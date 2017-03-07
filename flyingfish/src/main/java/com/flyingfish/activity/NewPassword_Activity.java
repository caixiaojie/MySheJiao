package com.flyingfish.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.flyingfish.R;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.MD5;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/20 0020.
 */
public class NewPassword_Activity extends Activity {
    @BindView(R.id.newPasswrodEdit)
    EditText newPasswrodEdit;
    private String passwordMD5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password_activity);
        FlyingFishIntance.getInstance().addActivity(this);
        ButterKnife.bind(this);
    }

    private void editpwd(){
        if ( newPasswrodEdit.getText().toString().length() < 6) {
            String str = new String(getString(R.string.please_input_set_up_password));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }
        try {
            passwordMD5 = MD5.eccrypt(newPasswrodEdit.getText().toString());
            passwordMD5 = MD5.eccrypt(passwordMD5);
         } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .post()
                .url(NetUrl.editpwd)
                .addParams("id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("password", passwordMD5)
                .addParams("again_password", passwordMD5)
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


    @OnClick(R.id.textView5)
    public void onClick() {
        editpwd();
    }

}
