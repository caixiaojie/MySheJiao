package com.junyao.cxj.zhangdan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<BillBean> data = new ArrayList<>();
    private BillAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewId();
        initData();
        initadapter();
    }

    /**
     * 找id
     */
    private void findViewId() {
        listView = (ListView) findViewById(R.id.lv);
    }
    /**
     * 初始化适配器
     */
    private void initadapter() {
        adapter = new BillAdapter(this);
        //加载空数据
        listView.setAdapter(adapter);
        //加载有数据
        adapter.setData(data);
        //更新适配器
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化数据源
     */
    private void initData() {
        for (int i = 1; i < 5; i++) {
            BillBean billBean = new BillBean();
            billBean.setPropertyfee("¥ 405");
            billBean.setWaterfee("3.00元");
            billBean.setOtherrfee("135平米"+i);
            billBean.setPeriod("2016-01-01～2016-01-31");
            data.add(billBean);
        }
    }
}
