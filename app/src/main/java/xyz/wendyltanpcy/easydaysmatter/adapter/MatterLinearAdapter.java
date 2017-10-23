package xyz.wendyltanpcy.easydaysmatter.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.wendyltanpcy.easydaysmatter.MatterDetailActivity;
import xyz.wendyltanpcy.easydaysmatter.R;
import xyz.wendyltanpcy.easydaysmatter.helper.Utility;
import xyz.wendyltanpcy.easydaysmatter.model.Matter;

import static java.lang.Math.abs;

/**
 * Created by Wendy on 2017/10/23.
 */

public class MatterLinearAdapter extends RecyclerView.Adapter<MatterLinearAdapter.ViewHolder> {

    private List<Matter> mMatterList = new ArrayList<>();
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView matterContent,matterCount,matterBefore,matterAfter,matterDaytext;
        public ViewHolder(final View v){
            super(v);
            matterContent = v.findViewById(R.id.matter_content);
            matterCount = v.findViewById(R.id.matter_day_count);
            matterBefore = v.findViewById(R.id.matter_before_text);
            matterAfter = v.findViewById(R.id.matter_after_text);
            matterDaytext = v.findViewById(R.id.matter_day_text);

        }
    }

    public MatterLinearAdapter(List<Matter> list){
        mMatterList = list;
    }

    @Override
    public void onBindViewHolder(MatterLinearAdapter.ViewHolder holder, int position) {
        Matter matter = mMatterList.get(position);
        String matterContent = matter.getMatterContent();
        if (matterContent.length()>5){
            matterContent = matterContent.substring(0,4)+"...";
        }
        holder.matterContent.setText(matterContent);

        long duration = Utility.getDateInterval(matter.getTargetDate());

        //deal with style of different duration
        String beforetext = "";
        String aftertext = "";
        if(duration < 0) {
            aftertext = "已经";
            ColorStateList list = ContextCompat.getColorStateList(mContext, R.color.expired);
            holder.matterCount.setBackgroundTintList(list);
            list = ContextCompat.getColorStateList(mContext,R.color.expired_light);
            holder.matterDaytext.setBackgroundTintList(list);
        }
        else if(duration>=0){
            aftertext = "还有";
            beforetext = "距离";
            ColorStateList list = ContextCompat.getColorStateList(mContext, R.color.future);
            holder.matterCount.setBackgroundTintList(list);
            list = ContextCompat.getColorStateList(mContext,R.color.future_light);
            holder.matterDaytext.setBackgroundTintList(list);
        }
        holder.matterAfter.setText(aftertext);
        holder.matterBefore.setText(beforetext);
        holder.matterCount.setText(Long.toString(abs(duration)));



    }

    public List<Matter> getMatterList(){
        return mMatterList;
    }

    @Override
    public int getItemCount() {
        return mMatterList.size();
    }

    @Override
    public MatterLinearAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        final View view  = LayoutInflater.from(mContext).inflate(R.layout.matter_list_item_linear,parent,false);
        final MatterLinearAdapter.ViewHolder hd = new MatterLinearAdapter.ViewHolder(view);

        hd.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = hd.getAdapterPosition();
                Matter matter = mMatterList.get(position);
                MatterDetailActivity.actionStart(mContext,matter,mMatterList,position);
            }
        });
        return hd;

    }

}
