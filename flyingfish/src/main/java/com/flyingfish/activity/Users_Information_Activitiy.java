package com.flyingfish.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flyingfish.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class Users_Information_Activitiy extends Activity {
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.email_addressText)
    TextView emailAddressText;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.textView9)
    TextView textView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_information_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView4, R.id.downloadText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView4:
                finish();
                break;
            case R.id.downloadText:

                break;
        }
    }
}
