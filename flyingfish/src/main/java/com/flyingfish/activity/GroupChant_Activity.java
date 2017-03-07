package com.flyingfish.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.flyingfish.R;
import com.flyingfish.tool.FlyingFishIntance;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class GroupChant_Activity extends Activity {


    private PopupWindow usersPopWindow;
    private ListView usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_chant_activity);
        ButterKnife.bind(this);
        FlyingFishIntance.getInstance().addActivity(this);

    }

//    void usersPop( ) {
//        if (usersPopWindow != null && usersPopWindow.isShowing()) {
//            return;
//        }
//        RelativeLayout layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.users_phone_pop, null);
//        usersPopWindow = new PopupWindow(layout,
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//        //点击空白处时，隐藏掉pop窗口
//        usersPopWindow.setFocusable(true);
//        usersPopWindow.setBackgroundDrawable(new BitmapDrawable());
//        //添加弹出、弹入的动画
//        usersPopWindow.setAnimationStyle(R.style.Popupwindow);
//        int[] location = new int[2];
//        usersPopWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, -location[1]);
//        //添加按键事件监听
//        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
//        usersList = (ListView)layout.findViewById(R.id.userPhoneList);
//    }


    @OnClick({R.id.go_backRadioBtn, R.id.emailRadioBtn, R.id.usersRadioBtn, R.id.phoneRadioBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_backRadioBtn:
                finish();
                break;
            case R.id.emailRadioBtn:

                break;
            case R.id.usersRadioBtn:
                Intent intent = new Intent(GroupChant_Activity.this,Users_Activity.class);
                startActivity(intent);
                break;
            case R.id.complieRadioBtn:
                break;
            case R.id.phoneRadioBtn:

                break;

        }
    }
}
