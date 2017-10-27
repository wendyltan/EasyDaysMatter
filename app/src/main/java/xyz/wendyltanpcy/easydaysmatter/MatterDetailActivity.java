package xyz.wendyltanpcy.easydaysmatter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wooplr.spotlight.SpotlightView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import xyz.wendyltanpcy.easydaysmatter.helper.Utility;
import xyz.wendyltanpcy.easydaysmatter.model.Matter;

import static java.lang.Math.abs;

/**
 * Created by user on 17-8-29.
 */

public class MatterDetailActivity extends BaseActivity implements View.OnClickListener {


    private TextView detailDate,detailAfter,detailBefore,detailContent,detailDays;
    private static Matter mMatter;
    private static int matpos;
    private static Context mContext;
    private static List<Matter> sMatterList;
    private LinearLayout detailHeader;
    private SpotlightView guideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.layoutId = R.layout.activity_matter_detail;
        super.title = "MatterDetail";
        super.onCreate(savedInstanceState);

        setAllText();




    }


    private void showGuide(View view,String uid,String title,String content){
        guideView = new SpotlightView.Builder(this)
                .introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(Color.parseColor("#eb273f"))
                .headingTvSize(32)
                .headingTvText(title)
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(16)
                .subHeadingTvText(content)
                .maskColor(Color.parseColor("#dc000000"))
                .target(view)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#eb273f"))
                .dismissOnTouch(true)
                .dismissOnBackPress(true)
                .enableDismissAfterShown(true)
                .usageId(uid) //UNIQUE ID
                .show();
    }

    private void setAllText(){


        String matterContent = mMatter.getMatterContent();
        long matterDateMs = mMatter.getTargetDate().getTime();
        long daysCount = Utility.getDateInterval(mMatter.getTargetDate());


        detailContent = (TextView) findViewById(R.id.matter_detail_content);
        detailContent.setOnClickListener(this);
        detailDays = (TextView) findViewById(R.id.matter_detail_days);
        detailDays.setOnClickListener(this);
        detailDate = (TextView) findViewById(R.id.matter_target_date);
        detailAfter = (TextView) findViewById(R.id.detail_after_text);
        detailBefore = (TextView) findViewById(R.id.detail_before_text);
        detailHeader = (LinearLayout) findViewById(R.id.detail_header);


        long duration = Utility.getDateInterval(mMatter.getTargetDate());
        String beforetext = "";
        String aftertext = "";
        if(duration < 0) {
            aftertext = "已经";
            ColorStateList list = ContextCompat.getColorStateList(mContext, R.color.expired);
            detailHeader.setBackgroundTintList(list);
        } else if(duration >=0) {
            aftertext = "还有";
            beforetext = "距离";
            ColorStateList list = ContextCompat.getColorStateList(mContext, R.color.future);
            detailHeader.setBackgroundTintList(list);
        }
        detailAfter.setText(aftertext);
        detailBefore.setText(beforetext);

        Calendar c = Calendar.getInstance();
        new SimpleDateFormat("yyyy年MM月dd日").format(c.getTime());

        detailDate.setText(new SimpleDateFormat("yyyy年MM月dd日").
                format(new Date(matterDateMs)));

        detailContent.setText(matterContent);

        detailDays.setText(Long.toString(abs(daysCount)));
    }

    public static void actionStart(Context context, Matter matter, List<Matter> matterList,int position){
        Intent i = new Intent(context,MatterDetailActivity.class);
        mContext = context;
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
                 MatterEditActivity.actionStart(this,mMatter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setAllText();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.matter_detail_content:
                showGuide(detailContent,"detailContent","This is detailContent","Check here to see matter detail!");
                break;
            case R.id.matter_detail_days:
                showGuide(detailDays,"detailDays","Here is the count down day","Yep this simple");
                break;
            default:
                break;

        }
    }
}
