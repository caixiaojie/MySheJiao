package com.flyingfish.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyingfish.R;
import com.flyingfish.bean.VerifiTokenbean;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/13 0013.
 */
public class Find_Password_Activity extends Activity {
    @BindView(R.id.findpassword_TitleText)
    TextView findpasswordTitleText;
    @BindView(R.id.findpassword_EmailEdit)
    EditText findpasswordEmailEdit;
    @BindView(R.id.findpassword_CodeEidt)
    EditText findpasswordCodeEidt;
    @BindView(R.id.findpassword_GetCodeBtn)
    TextView findpassword_GetCodeBtn;
    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpassword_activity);
        FlyingFishIntance.getInstance().addActivity(this);
        ButterKnife.bind(this);
        time = new TimeCount(30000, 1000);
    }

    private void getcode() {
        String str = "";
        if (!FlyingFishIntance.getInstance().isEmail(findpasswordEmailEdit.getText().toString())) {
            str = new String(getString(R.string.email_err));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }
        time.start();
        OkHttpUtils
                .post()
                .url(NetUrl.getcode)
                .addParams("email", findpasswordEmailEdit.getText().toString())
                 .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            SuperCustomToast.getInstance(getApplication()).show(jsonObject.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void verifitoken() {
        String str = "";
        if (!FlyingFishIntance.getInstance().isEmail(findpasswordEmailEdit.getText().toString())) {
            str = new String(getString(R.string.email_err));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }
        if (findpasswordCodeEidt.getText().toString().length()!=6) {
            str = new String(getString(R.string.code_err));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }
        OkHttpUtils
                .post()
                .url(NetUrl.verifitoken)
                .addParams("email", findpasswordEmailEdit.getText().toString())
                .addParams("code", findpasswordCodeEidt.getText().toString())
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        VerifiTokenbean verifiTokenbean = new Gson().fromJson(response,VerifiTokenbean.class);
                        SuperCustomToast.getInstance(getApplicationContext()).show(verifiTokenbean.getMsg());
                        if (verifiTokenbean.getStatus()==200){
                            FlyingFishIntance.getInstance().setMember_Id(verifiTokenbean.getData().getId());
                            startActivity(new Intent(getApplicationContext(), NewPassword_Activity.class));
                        }
                    }
                });
    }

    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            findpassword_GetCodeBtn.setText(millisUntilFinished / 1000 + " 秒后可重新发送");
            findpassword_GetCodeBtn.setEnabled(false);
            findpassword_GetCodeBtn.setClickable(false);
        }
        @Override
        public void onFinish() {
            findpassword_GetCodeBtn.setText(getResources().getText(R.string.againcode));
            findpassword_GetCodeBtn.setEnabled(true);
            findpassword_GetCodeBtn.setClickable(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time.onFinish();
    }


    @OnClick({R.id.findpassword_GoBackBtn,R.id.findpassword_GetCodeBtn, R.id.findpassword_NextBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.findpassword_GoBackBtn:
                finish();
                break;
            case R.id.findpassword_GetCodeBtn:
                getcode();
                break;
            case R.id.findpassword_NextBtn:
                verifitoken();
                break;
        }
    }


}
