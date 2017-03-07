package com.flyingfish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.flyingfish.R;
import com.flyingfish.bean.GradeBean;

import java.util.ArrayList;

/**
 * Created by Marshon.Chen on 2016/7/26.
 * DESC:
 */
public abstract class GradeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<GradeBean> mDatas;
    private Context mContext;

    public GradeRecyclerAdapter(Context context, ArrayList<GradeBean> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         GradeMViewHolder v = new GradeMViewHolder(LayoutInflater.from(mContext).inflate(R.layout.grade_item_layout,parent, false) );

        return v;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
          GradeMViewHolder viewHolder = (GradeMViewHolder) holder;
//        viewHolder.headImg.setImageResource();
        viewHolder.gradeText.setText(mDatas.get(position).getGrade());

        convert(holder,position);
    }

    public abstract void convert(RecyclerView.ViewHolder holder, final int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class GradeMViewHolder extends RecyclerView.ViewHolder {
        public CheckBox gradeText;

        public GradeMViewHolder(View itemView) {
            super(itemView);
            gradeText = (CheckBox) itemView.findViewById(R.id.gradeText);

        }
    }
}
