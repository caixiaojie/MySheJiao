package com.flyingfish.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.flyingfish.R;
import com.flyingfish.bean.Blackboardindex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class BlabordAdapter extends BaseAdapter {
    private Context context;
    private List<Blackboardindex> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Map<Integer,Boolean> isCheck = new HashMap<>();//保存选中状态
    public BlabordAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        initCheck(false);//默认不勾选
    }

    /**
     * 初始化map集合
     */
    private void initCheck(boolean flag) {
        //map集合的数量和list的数量是一样的
        for (int i = 0; i < data.size(); i++) {
            //默认状态
            isCheck.put(i,flag);
        }
    }
    //设置数据
    public void setData(List<Blackboardindex> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data != null? data.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.blabord_item,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_dept = (TextView) view.findViewById(R.id.tv_dept);
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            viewHolder.tv_email = (TextView) view.findViewById(R.id.tv_email);
            viewHolder.ck_select = (CheckBox) view.findViewById(R.id.btn_select);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_dept.setText(data.get(i).getDept());
        viewHolder.tv_email.setText(data.get(i).getEmail());
        viewHolder.tv_name.setText(data.get(i).getName());
        viewHolder.tv_phone.setText(data.get(i).getPhone());
        viewHolder.ck_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //用map集合保存
                isCheck.put(i,b);
            }
        });
        //设置状态
        if (isCheck.get(i) == null) {
            isCheck.put(i,false);
        }
        viewHolder.ck_select.setChecked(isCheck.get(i));
        return view;
    }
    static class ViewHolder {
        TextView tv_name,tv_dept,tv_phone,tv_email;
        CheckBox ck_select;
    }
    //获取状态
    public Map<Integer,Boolean> getMap() {
        //返回状态
        return isCheck;
    }
    //删除一条数据
    public void removeData(int position) {
        data.remove(position);
    }
}
