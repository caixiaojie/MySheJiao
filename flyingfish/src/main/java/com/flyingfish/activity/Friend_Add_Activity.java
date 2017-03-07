package com.flyingfish.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyingfish.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class Friend_Add_Activity extends Activity {
    @BindView(R.id.headImg)
    ImageView headImg;
    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.departmentText)
    TextView departmentText;
    @BindView(R.id.positionText)
    TextView positionText;
    private String user_id;
    private String faceImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agree_add_activity);
        ButterKnife.bind(this);
        Intent intent =getIntent();

        nameText.setText(intent.getStringExtra("real_name"));
        departmentText.setText(intent.getStringExtra("department_name"));
        positionText.setText(intent.getStringExtra("member_role"));
        user_id = intent.getStringExtra("friend_id");
        faceImg = intent.getStringExtra("face");
      }

    @OnClick({R.id.agree_Linear, R.id.refuse_Linear, R.id.refuse_again_Linear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.agree_Linear:
                break;
            case R.id.refuse_Linear:
                break;
            case R.id.refuse_again_Linear:
                break;
        }
    }
}
