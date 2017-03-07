package com.flyingfish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyingfish.R;
import com.flyingfish.bean.IndexSendInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marshon.Chen on 2016/7/26.
 * DESC:
 */
public abstract class MemberRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<IndexSendInfo.DataBean> mDatas;
    private Context mContext;

    public MemberRecyclerAdapter(Context context, List<IndexSendInfo.DataBean> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.member_recycler_item,parent, false);
        MViewHolder viewHolder = new MViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MViewHolder viewHolder = (MViewHolder) holder;
         if (!mDatas.get(position).getFace().equals("")){
            Picasso.with(mContext.getApplicationContext()).
                    load( mDatas.get(position).getFace().replaceAll("\\\\",""))
                    .placeholder(R.mipmap.user_icon)
                    .into(viewHolder.userHeadImg);
        }
         viewHolder.userNameText.setText(mDatas.get(position).getReal_name());
        convert(holder, position);
    }

    public abstract void convert(RecyclerView.ViewHolder holder, final int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class MViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameText;
        private CircleImageView userHeadImg;
        private LinearLayout sevice_linea;
        public MViewHolder(View itemView) {
            super(itemView);
            userNameText = (TextView) itemView.findViewById(R.id.userNameText);
            userHeadImg = (CircleImageView) itemView.findViewById(R.id.userHeadImg);
            sevice_linea = (LinearLayout) itemView.findViewById(R.id.sevice_linea);
        }
    }
}
