package xyz.wendyltanpcy.easydaysmatter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import xyz.wendyltanpcy.easydaysmatter.adapter.MatterAdapter;
import xyz.wendyltanpcy.easydaysmatter.model.Matter;


public class MainActivity extends AppCompatActivity {


    private List<Matter> mMatterList = new ArrayList<>();
    private MatterAdapter MyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //deal with database
        LitePal.getDatabase();



        //get list and setDatabase
        mMatterList = DataSupport.findAll(Matter.class);



        //set recyclerview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter = new MatterAdapter(mMatterList);
        recyclerView.setAdapter(MyAdapter);

        FloatingActionButton addMatter = (FloatingActionButton) findViewById(R.id.add_matter);
        addMatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MatterAddActivity.actionStart(getApplicationContext(),mMatterList);
            }
        });



    }

    private void doRefresh(){
        mMatterList = MyAdapter.getMatterList();
        MyAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        doRefresh();
    }


}
