package com.junyao.cxj.zhangdan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/9.
 */

public class BillAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<BillBean> data = new ArrayList<>();

    public BillAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<BillBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.bill_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_propertyfee = (TextView) convertView.findViewById(R.id.tv_propertyfee);
            viewHolder.tv_waterfee = (TextView) convertView.findViewById(R.id.tv_waterfee);
            viewHolder.tv_otherfee = (TextView) convertView.findViewById(R.id.tv_area);
            viewHolder.tv_period = (TextView) convertView.findViewById(R.id.tv_period);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.detaillayout = (LinearLayout) convertView.findViewById(R.id.detaillayout);
            viewHolder.relativeLayout1 = (RelativeLayout) convertView.findViewById(R.id.relativelayout1);
            viewHolder.relativeLayout2 = (RelativeLayout) convertView.findViewById(R.id.relativelayout2);
            viewHolder.relativeLayout3 = (RelativeLayout) convertView.findViewById(R.id.relativelayout3);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            BillBean billBean = data.get(position);
            viewHolder.tv_propertyfee.setText(billBean.getPropertyfee());
            viewHolder.tv_waterfee.setText(billBean.getWaterfee());
            viewHolder.tv_otherfee.setText(billBean.getOtherrfee());
            viewHolder.tv_period.setText(billBean.getPeriod());
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        finalViewHolder.detaillayout.setVisibility(View.GONE);
                        finalViewHolder.relativeLayout1.setVisibility(View.GONE);
                        finalViewHolder.relativeLayout2.setVisibility(View.GONE);
                        finalViewHolder.relativeLayout3.setVisibility(View.GONE);

                        /*finalViewHolder.tv_waterfee.setVisibility(View.VISIBLE);
                        finalViewHolder.tv_otherfee.setVisibility(View.VISIBLE);
                        finalViewHolder.tv_period.setVisibility(View.VISIBLE);*/
                    }else {

                        /*finalViewHolder.tv_waterfee.setVisibility(View.GONE);
                        finalViewHolder.tv_otherfee.setVisibility(View.GONE);
                        finalViewHolder.tv_period.setVisibility(View.GONE);*/
                        finalViewHolder.detaillayout.setVisibility(View.VISIBLE);
                        finalViewHolder.relativeLayout1.setVisibility(View.VISIBLE);
                        finalViewHolder.relativeLayout2.setVisibility(View.VISIBLE);
                        finalViewHolder.relativeLayout3.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        return convertView;
    }
    static class ViewHolder{
        TextView tv_propertyfee;//物业费
        TextView tv_waterfee;
        TextView tv_otherfee;
        TextView tv_period;//周期
        CheckBox checkBox;//切换
        LinearLayout detaillayout;
        RelativeLayout relativeLayout1,relativeLayout2,relativeLayout3;
    }
}
