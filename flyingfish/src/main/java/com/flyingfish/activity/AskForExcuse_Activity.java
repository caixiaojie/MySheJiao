package com.flyingfish.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flyingfish.R;
import com.flyingfish.bean.MyInfoBean;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class AskForExcuse_Activity extends Activity {
    @BindView(R.id.imageView10)
    CircleImageView imageView10;
    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.departmentText)
    TextView departmentText;
    @BindView(R.id.positionText)
    TextView positionText;
    private String friend_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_for_excuse_activity);
        ButterKnife.bind(this);
        Intent intent =getIntent();
        friend_id = intent.getStringExtra("friend_id");
        friendinfo();
    }

    private void friendinfo() {
        OkHttpUtils
                .post()
                .url(NetUrl.friendinfo)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())

                .addParams("friend_id",friend_id)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        MyInfoBean myInfoBean = new Gson().fromJson(response, MyInfoBean.class);
                        if (myInfoBean.getStatus() == 200) {
                            nameText.setText(myInfoBean.getData().getReal_name());
                            departmentText.setText(myInfoBean.getData().getDepartment_name());
                            positionText.setText(myInfoBean.getData().getMember_role());
                        } else {
                            SuperCustomToast.getInstance( getApplicationContext()).show(myInfoBean.getMsg());
                        }
                    }
                });
    }

    private void agreerelieve(String type) {
        OkHttpUtils
                .post()
                .url(NetUrl.agreerelieve)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("type",type)
                .addParams("friend_id",friend_id)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }

    @OnClick({R.id.imageView11, R.id.agree_Linear, R.id.refuse_Linear, R.id.refuse_again_Linear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView11:
                finish();
                break;
            case R.id.agree_Linear:
                agreerelieve("1");
                break;
            case R.id.refuse_Linear:
                agreerelieve("2");
            case R.id.refuse_again_Linear:
                agreerelieve("3");
                break;
        }
    }
}
