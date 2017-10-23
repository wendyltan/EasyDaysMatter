package xyz.wendyltanpcy.easydaysmatter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import xyz.wendyltanpcy.easydaysmatter.adapter.MatterGridAdapter;
import xyz.wendyltanpcy.easydaysmatter.adapter.MatterLinearAdapter;
import xyz.wendyltanpcy.easydaysmatter.helper.Utility;
import xyz.wendyltanpcy.easydaysmatter.model.Matter;

import static java.lang.Math.abs;


public class MainActivity extends AppCompatActivity {


    private List<Matter> mMatterList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MatterGridAdapter MyAdapterGrid;
    private MatterLinearAdapter MyAdapterLinear;
    private View headerView;

    //set default to gridview
    private boolean viewStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        baseInit(viewStatus);
        switchView(viewStatus);



    }

    private void baseInit(boolean isSwitch){
        if (isSwitch){
            setContentView(R.layout.activity_main_linear);
            headerView = findViewById(R.id.header);
            fillInHeader(headerView);

        }else{
            setContentView(R.layout.activity_main);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //deal with database
        LitePal.getDatabase();



        //get list and setDatabase
        mMatterList = DataSupport.findAll(Matter.class);

        //set recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        FloatingActionButton addMatter = (FloatingActionButton) findViewById(R.id.add_matter);
        addMatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MatterAddActivity.actionStart(getApplicationContext(),mMatterList);
            }
        });


    }

    public void switchView(boolean isSwitch){
        if (isSwitch){
            //set to linear recyclerview
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            MyAdapterLinear = new MatterLinearAdapter(mMatterList);
            mRecyclerView.setAdapter(MyAdapterLinear);

        }else{
            //set to grid recyclerview
            GridLayoutManager layoutManager = new GridLayoutManager(this,2);
            mRecyclerView.setLayoutManager(layoutManager);
            MyAdapterGrid = new MatterGridAdapter(mMatterList);
            mRecyclerView.setAdapter(MyAdapterGrid);


        }
    }

    private void fillInHeader(View v){
        TextView headContent,headDate,headCount;
        headContent = v.findViewById(R.id.head_event_content);
        headDate = v.findViewById(R.id.head_event_date);
        headCount = v.findViewById(R.id.head_days_count);
        Matter matter = mMatterList.get(0);
        headContent.setText(matter.getMatterContent());
        headDate.setText(new SimpleDateFormat("yyy年MM月dd日").
                format(matter.getTargetDate()));
        headCount.setText(Long.toString(abs(Utility.getDateInterval(matter.getTargetDate()))));


    }

    private void doRefreshForGrid(MatterGridAdapter adapter){
        mMatterList = adapter.getMatterList();
        adapter.notifyDataSetChanged();

    }


    private void doRefreshForLinear(MatterLinearAdapter adapter){
        mMatterList = adapter.getMatterList();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(viewStatus){
            //if true
            doRefreshForLinear(MyAdapterLinear);
        }else{
            //false
            doRefreshForGrid(MyAdapterGrid);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.switch_view:
                //switch view
                if (viewStatus == true){
                    viewStatus = false;
                    baseInit(viewStatus);
                    switchView(viewStatus);
                }else {
                    viewStatus = true;
                    baseInit(viewStatus);
                    switchView(viewStatus);
                }
                return true;
            default:
                break;
        }
        return false;
    }
}
