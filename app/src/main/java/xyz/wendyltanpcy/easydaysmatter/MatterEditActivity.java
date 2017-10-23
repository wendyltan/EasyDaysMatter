package xyz.wendyltanpcy.easydaysmatter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import xyz.wendyltanpcy.easydaysmatter.model.Matter;

/**
 * Created by Wendy on 2017/10/23.
 */

public class MatterEditActivity extends BaseActivity implements View.OnClickListener{


    private Button saveMatter;

    private EditText matterContent;

    public static TextView datetime;


    public static Matter sMatter;


    protected void onCreate(Bundle savedInstanceState) {
        super.layoutId = R.layout.activity_add_matter;
        super.title = "EditMatter";
        super.onCreate(savedInstanceState);

        // Find button in add event layout and set it the click listener
        saveMatter = (Button) findViewById(R.id.save_event);
        saveMatter.setOnClickListener(this);

        matterContent = (EditText) findViewById(R.id.matter_content_input);
        matterContent.setText(sMatter.getMatterContent());


        // find the datetime text view element in add event layout
        datetime = (TextView) findViewById(R.id.datetime);

        // Getting current date time and set for text view component
        Date date = sMatter.getTargetDate();
        String dateStr = new SimpleDateFormat("yyy-MM-dd").format(date);
        String weekdayStr = new SimpleDateFormat("EEEE").format(date);
        datetime.setText(dateStr + " " + weekdayStr);

        // Datetime text view component click listener that to show the date time picker
        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new MatterEditActivity.DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });


    }

    public static void actionStart(Context context, Matter matter) {
        Intent i = new Intent(context, MatterEditActivity.class);
        sMatter = matter;
        context.startActivity(i);

    }


    @Override
    public void onClick(View view) {
        sMatter.setMatterContent(matterContent.getText().toString());
        String dateStr = datetime.getText().toString().split(" ")[0];
        try {
            sMatter.setTargetDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sMatter.save();
        finish();
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String[] dateStrArr = datetime.getText().toString().split(" ")[0].split("-");
            int year = Integer.parseInt(dateStrArr[0]);
            int month = Integer.parseInt(dateStrArr[1]) - 1;
            int day = Integer.parseInt(dateStrArr[2]);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String dateStr = "";
            String weekdayStr = "";
            try {
                dateStr = i + "-" + (i1 + 1) + "-" + i2;
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                weekdayStr = new SimpleDateFormat("EEEE").format(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            datetime.setText(dateStr + " " + weekdayStr);
        }
    }
}
