package com.flyingfish.activity;

import android.app.Activity;
import android.os.Bundle;

import com.flyingfish.R;
import com.flyingfish.tool.FlyingFishIntance;

/**
 * Created by Administrator on 2017/2/6 0006.
 */
public class Chant_Activity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_chant_activity);
        FlyingFishIntance.getInstance().addActivity(this);
    }
}
