package com.flyingfish.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyingfish.R;
import com.flyingfish.bean.LoginBean;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.MD5;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SpanUtils;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class LoginActivity extends Activity {

    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_Btn)
    TextView login_Btn;
    private String passwordMD5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        FlyingFishIntance.getInstance().addActivity(this);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white_color), true);
        FlyingFishIntance.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.login_Btn, R.id.fpw_txt, R.id.new_userText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_Btn:
                 account();
                break;
            case R.id.fpw_txt:
                startActivity(new Intent(this, Find_Password_Activity.class));
                break;
            case R.id.new_userText:
                startActivity(new Intent(this, Regist_Activity.class));
                break;

        }
    }


    private void account() {
        String username = loginUsername.getText().toString();
        String userpassword = loginPassword.getText().toString();
        String str  ;
        if ((!SpanUtils.isPhoneNumberValid(username))&&(!FlyingFishIntance.getInstance().isEmail( username))) {
            str = new String(getString(R.string.please_input_rightacount));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }

        if (userpassword.length() < 6) {
            str = new String(getString(R.string.please_input_set_up_password));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }
        try {
            passwordMD5 = MD5.eccrypt(userpassword);
            passwordMD5 = MD5.eccrypt(passwordMD5);
         } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String phoneInfo = "Product: " + Build.PRODUCT;
        phoneInfo += ", MODEL: " + Build.MODEL;
        phoneInfo += ", SDK: " + Build.VERSION.SDK;
        phoneInfo += ", VERSION.RELEASE: " + Build.VERSION.RELEASE;
        phoneInfo += ", DEVICE: " + Build.DEVICE;
        String uniqueId = UUID.randomUUID().toString();


        login_Btn.setEnabled(false);
        OkHttpUtils
                .post()
                .url(NetUrl.loginurl)
                .addParams("login_name",username)
                .addParams("password", passwordMD5)
                .addParams("udid",uniqueId)
                .addParams("devicetype","0")
                .addParams("name",phoneInfo)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        login_Btn.setEnabled(true);
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        login_Btn.setEnabled(true);
                        LoginBean loginBean = new Gson().fromJson(response,LoginBean.class);
                        SuperCustomToast.getInstance(getApplicationContext()).show(loginBean.getMsg());
                        if (loginBean.getStatus()==200){
                            LoginBean.DataBean dataBean = loginBean.getData();
                            FlyingFishIntance.getInstance().setMember_Id(dataBean.getMember_id());
                            FlyingFishIntance.getInstance().setAuthkey(dataBean.getAuthkey());
                            FlyingFishIntance.getInstance().setEmail(dataBean.getEmail());
                            FlyingFishIntance.getInstance().setEnd_disable_time(dataBean.getEnd_disable_time());
                            FlyingFishIntance.getInstance().setFace(dataBean.getFace());
                            FlyingFishIntance.getInstance().setInfo_is_complete(dataBean.getInfo_is_complete());
                            FlyingFishIntance.getInstance().setLast_login_time(dataBean.getLast_login_time());
                            FlyingFishIntance.getInstance().setLast_login_ip(dataBean.getLast_login_ip());
                            FlyingFishIntance.getInstance().setMb_depart_id(dataBean.getMb_depart_id());
                            FlyingFishIntance.getInstance().setQr_code(dataBean.getQr_code());
                            FlyingFishIntance.getInstance().setReal_name(dataBean.getReal_name());
                            FlyingFishIntance.getInstance().setRemark(dataBean.getRemark());
                            FlyingFishIntance.getInstance().setToken(dataBean.getToken());
                            FlyingFishIntance.getInstance().setWork_phone(dataBean.getWork_phone());
                            FlyingFishIntance.getInstance().setToken_exptime(dataBean.getToken_exptime());
                            FlyingFishIntance.getInstance().setRegistered_time(dataBean.getRegistered_time());
                            FlyingFishIntance.getInstance().setMb_phone(dataBean.getMb_phone());
                            FlyingFishIntance.getInstance().setPhone(dataBean.getPhone());
                            FlyingFishIntance.getInstance().setStart_disable_time(dataBean.getStart_disable_time());
                            FlyingFishIntance.getInstance().setStatus(dataBean.getStatus());
                            startActivity(new Intent(getApplicationContext(), Page_Activity.class));
                            finish();
                        }
                     }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            FlyingFishIntance.getInstance().exit();
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
