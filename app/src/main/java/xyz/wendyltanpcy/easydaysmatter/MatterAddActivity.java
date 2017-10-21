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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import xyz.wendyltanpcy.easydaysmatter.model.Matter;

/**
 * Created by user on 17-8-29.
 */

public class MatterAddActivity extends BaseActivity implements View.OnClickListener {


    private Button saveMatter;

    private EditText matterContent;

    private static List<Matter> sMatterList = new ArrayList<>();


    public static TextView datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.layoutId = R.layout.activity_add_matter;
        super.title = "Add Matter";
        super.onCreate(savedInstanceState);


        datetime = (TextView) findViewById(R.id.datetime);


        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dateStr = new SimpleDateFormat("yyy-MM-dd").format(date);
        String weekdayStr = new SimpleDateFormat("EEEE").format(date);
        datetime.setText(dateStr +  " " + weekdayStr);


        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });


        saveMatter = (Button) findViewById(R.id.save_event);
        saveMatter.setOnClickListener(this);


        matterContent = (EditText) findViewById(R.id.matter_content_input);

    }

    public static void actionStart(Context context,List<Matter> matterList){
        Intent i = new Intent(context,MatterAddActivity.class);
        sMatterList = matterList;
        context.startActivity(i);

    }

    @Override
    public void onClick(View view) {
        Matter matter = new Matter();
        if (matterContent.getText().length()>8){
            matterContent.getText().clear();
            Toast.makeText(MatterAddActivity.this,"请重新输入！",Toast.LENGTH_SHORT).show();
        }else{
            matter.setMatterContent(matterContent.getText().toString());

            String dateStr = datetime.getText().toString().split(" ")[0];
            try {
                matter.setTargetDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            matter.setCreateDate(c.getTime());
            matter.save();
            sMatterList.add(matter);

            finish();
        }

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
