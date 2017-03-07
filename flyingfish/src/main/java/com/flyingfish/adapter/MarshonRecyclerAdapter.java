package com.flyingfish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyingfish.R;
import com.flyingfish.bean.IndexFrilistBean;
import com.flyingfish.tool.FlyingFishIntance;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marshon.Chen on 2016/7/26.
 * DESC:
 */
public abstract class MarshonRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<IndexFrilistBean.DataBean.AllListBean> mDatas;
    private Context mContext;

    public MarshonRecyclerAdapter(Context context, List<IndexFrilistBean.DataBean.AllListBean> mDatas){
        this.mContext=context;
        this.mDatas=mDatas;
     }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(mContext).inflate(R.layout.message_list_item,parent,false);
        return new MViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MViewHolder viewHolder = (MViewHolder)holder;
        if (!mDatas.get(position).getFace().equals("")){
            Picasso.with(mContext.getApplicationContext()).
                    load( mDatas.get(position).getFace().replaceAll("\\\\",""))
                    .placeholder(R.mipmap.user_icon)
                    .into(viewHolder.headImg);

        }
             viewHolder.mNameText.setText(mDatas.get(position).getReal_name());
            viewHolder.departmentText.setText(mDatas.get(position).getDepartment_name());
            viewHolder.emailEdit.setText(mDatas.get(position).getEmail());
            viewHolder.positionText.setText(mDatas.get(position).getMember_role());
            viewHolder.messageTypeText.setBackground(mContext.getResources().getDrawable(R.drawable.red_oval_shape));

            if (position!=0)
            if ((Integer.parseInt(mDatas.get(position).getGrade()) > Integer.parseInt(FlyingFishIntance.getInstance().getSetGrade()))){
                viewHolder.phoneEdit.setText(mDatas.get(position).getPhone());
            }else {
                viewHolder.phoneEdit.setText(mDatas.get(position).getMb_phone());
            }


        convert(holder,position);
    }

    public abstract void convert(RecyclerView.ViewHolder holder, final int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class  MViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView headImg ;
        private TextView mNameText;
        private TextView positionText;
        private TextView departmentText;
        private TextView phoneEdit;
        private TextView emailEdit;
        private TextView messageTypeText;

        public MViewHolder(View itemView) {
            super(itemView);
            headImg = (CircleImageView)itemView.findViewById(R.id.head_Img_item);
            mNameText = (TextView)itemView.findViewById(R.id.nameTextView);
            positionText = (TextView)itemView.findViewById(R.id.positionText);
            departmentText = (TextView)itemView.findViewById(R.id.departmentText);
            phoneEdit = (TextView)itemView.findViewById(R.id.phoneEdit);
            emailEdit = (TextView)itemView.findViewById(R.id.emailEdit);
            messageTypeText = (TextView)itemView.findViewById(R.id.messageTypeText);
        }
    }
}
