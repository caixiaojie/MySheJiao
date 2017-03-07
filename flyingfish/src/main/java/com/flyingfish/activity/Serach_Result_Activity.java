package com.flyingfish.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyingfish.R;
import com.flyingfish.bean.FriendBean;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.flyingfish.util.Constant;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/17 0017.
 */
public class Serach_Result_Activity extends Activity {
    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.departmentText)
    TextView departmentText;
    @BindView(R.id.member_roleText)
    TextView memberRoleText;
    @BindView(R.id.gradeLinear)
    LinearLayout gradeLinear;
    @BindView(R.id.friendHeadImg)
    CircleImageView friendHeadImg;
    private String name;
    private String phone;
    private String user_id;
    private FriendBean friendBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chose_friend_relation_layout);
        ButterKnife.bind(this);
        FlyingFishIntance.getInstance().addActivity(this);
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        user_id = getIntent().getStringExtra("user_id");
        gradeLinear.setVisibility(View.GONE);
        if (user_id.equals("")) {
            searchfriend_p();
        } else {
            searchfriend_i();
        }

    }

    /**
     * 查找好友
     */
    private void searchfriend_p() {
        OkHttpUtils
                .post()
                .url(NetUrl.searchfriend)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("real_name", name)
                .addParams("mb_phone", phone)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        friendBean = new Gson().fromJson(response, FriendBean.class);
                        if (friendBean.getStatus() == 200) {
                            nameText.setText(friendBean.getData().getReal_name());
                            departmentText.setText(friendBean.getData().getDepartment_name());
                            memberRoleText.setText(friendBean.getData().getMember_role());
                            user_id = friendBean.getData().getMember_id();
                            if (!friendBean.getData().getFace().equals("")) {
                                Picasso.with(getApplication()).load(friendBean.
                                        getData().getFace()).into(friendHeadImg);
                            }
                            gradeLinear.setVisibility(View.VISIBLE);

                        }
                    }
                });
    }

    /**
     * 查找好友
     */
    private void searchfriend_i() {
        OkHttpUtils
                .post()
                .url(NetUrl.getmyinfo)
                .addParams("member_id", user_id)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        friendBean = new Gson().fromJson(response, FriendBean.class);
                        if (friendBean.getStatus() == 200) {

                            nameText.setText(friendBean.getData().getReal_name());
                            departmentText.setText(friendBean.getData().getDepartment_name());
                            memberRoleText.setText(friendBean.getData().getMember_role());
                            if (!friendBean.getData().getFace().equals("")) {
                                Picasso.with(getApplication()).load(friendBean.
                                        getData().getFace().replaceAll("\\\\","")).into(friendHeadImg);
                            }
                            gradeLinear.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 添加好友
     */
    private void addfriend(String grade) {
        OkHttpUtils
                .post()
                .url(NetUrl.addfriend)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("friend_id", user_id)
                .addParams("grade", grade)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            SuperCustomToast.getInstance(getApplicationContext()).show(jsonObject.getString("msg"));
                            if (jsonObject.getString("data").equals("200")){
                                setResult(Constant.ADD_FRIEND_OK);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    @OnClick({R.id.bossText, R.id.colleagueText, R.id.subordinateText, R.id.friendText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bossText:
                 addfriend("1");
                break;
            case R.id.colleagueText:
                 addfriend("0");
                break;
            case R.id.subordinateText:
                 addfriend("-1");
                break;
            case R.id.friendText:
                 addfriend("");
                break;
        }
    }
}
