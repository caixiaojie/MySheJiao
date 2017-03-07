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
public class Friends_Information_Activity extends Activity {
    @BindView(R.id.friendHeadImg)
    CircleImageView friendHeadImg;
    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.departmentText)
    TextView departmentText;
    @BindView(R.id.member_roleText)
    TextView memberRoleText;
    @BindView(R.id.user_phoneText)
    TextView userPhoneText;
    @BindView(R.id.emailText)
    TextView emailText;
    @BindView(R.id.agreePersonText)
    TextView agreePersonText;
    private String friend_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_information_layout);
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
                            memberRoleText.setText(myInfoBean.getData().getMember_role());
                            userPhoneText.setText(myInfoBean.getData().getMb_phone());
                            emailText.setText(myInfoBean.getData().getEmail());
                            agreePersonText.setText(myInfoBean.getData().getReal_name());
                         } else {
                            SuperCustomToast.getInstance( getApplicationContext()).show(myInfoBean.getMsg());
                        }
                     }
                });
    }

    private void changefri() {
        OkHttpUtils
                .post()
                .url(NetUrl.changefri)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
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

    @OnClick({R.id.imageView9, R.id.changeRelationText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView9:
                finish();
                break;
            case R.id.changeRelationText:
                changefri();
                break;
        }
    }
}
