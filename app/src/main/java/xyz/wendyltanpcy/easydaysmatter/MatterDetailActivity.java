package xyz.wendyltanpcy.easydaysmatter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import xyz.wendyltanpcy.easydaysmatter.helper.Utility;
import xyz.wendyltanpcy.easydaysmatter.model.Matter;

/**
 * Created by user on 17-8-29.
 */

public class MatterDetailActivity extends BaseActivity {


    private TextView detailDate;
    private TextView detailContent;
    private TextView detailDays;
    private static Matter mMatter;
    private static int matpos;
    private static List<Matter> sMatterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.layoutId = R.layout.activity_matter_detail;
        super.title = "MatterDetail";
        super.onCreate(savedInstanceState);



        String matterContent = mMatter.getMatterContent();
        long matterDateMs = mMatter.getTargetDate().getTime();
        long daysCount = Utility.getDateInterval(mMatter.getTargetDate());


        detailContent = (TextView) findViewById(R.id.matter_detail_content);
        detailDays = (TextView) findViewById(R.id.matter_detail_days);
        detailDate = (TextView) findViewById(R.id.matter_target_date);
        Calendar c = Calendar.getInstance();
        new SimpleDateFormat("yyyy年MM月dd日").format(c.getTime());


        detailDate.setText(new SimpleDateFormat("yyyy年MM月dd日").
                    format(new Date(matterDateMs)));

        detailContent.setText(matterContent);

        detailDays.setText(Long.toString(daysCount));


    }

    public static void actionStart(Context context, Matter matter, List<Matter> matterList,int position){
        Intent i = new Intent(context,MatterDetailActivity.class);
        mMatter = matter;
        sMatterList = matterList;
        matpos = position;
        context.startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_matter:
                AlertDialog.Builder builder = new AlertDialog.Builder(MatterDetailActivity.this);
                builder.setMessage("确定要删除吗？");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mMatter.delete();
                        sMatterList.remove(matpos);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return true;
            case R.id.edit_matter:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
