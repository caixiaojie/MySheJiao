package com.flyingfish.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.flyingfish.R;
import com.flyingfish.bean.ConuntryBean;
import com.flyingfish.bean.MyInfoBean;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.flyingfish.util.FaceRect;
import com.flyingfish.util.ParseResult;
import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.FaceDetector;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/17 0017.
 */
public class UserInformation_Activity extends Activity {

    @BindView(R.id.real_nameEdit)
    EditText realNameEdit;
    @BindView(R.id.department_nameEdit)
    EditText departmentNameEdit;
    @BindView(R.id.member_roleEdit)
    EditText memberRoleEdit;
    @BindView(R.id.countryCheckBox)
    CheckBox countryCheckBox;
    @BindView(R.id.phoneEdit)
    EditText phoneEdit;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.workPhone)
    EditText workPhone;
    @BindView(R.id.extensionTelephone)
    EditText extensionTelephone;
    @BindView(R.id.oldPasswordEdit)
    EditText oldPasswordEdit;
    @BindView(R.id.newPasswrodEdit)
    EditText newPasswrodEdit;
    @BindView(R.id.newPasswordAffirmEdit)
    EditText newPasswordAffirmEdit;
    @BindView(R.id.userEmailText)
    TextView userEmailText;
    @BindView(R.id.head_faceImage)
    CircleImageView head_faceImage;
    @BindView(R.id.qr_codeImage)
    ImageView qr_codeImage;
    @BindView(R.id.take_overWorkText)
    TextView take_overWorkText;
    private ArrayList<HashMap<String, String>> listems;
    private PopupWindow popupWindow;
    private String code = "";
    private MyInfoBean myInfoBean;
    private PopupWindow popupsureWindow;
    private Bitmap mImage;
    private FaceDetector mFaceDetector;
    private FaceRect[] mFaces;
    private String upImg="";
    private File cameraFile;
    private String Img;
    public final static int REQUEST_PICTURE_CHOOSE = 1;
    public final static int REQUEST_CAMERA_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_activity);
        FlyingFishIntance.getInstance().addActivity(this);
        ButterKnife.bind(this);
        getmyinfo();
        country();
        mFaceDetector = FaceDetector.createDetector(this, null);
    }

    /**
     * 初始化PopWindow
     */
    private void initPopWindow() {

        View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.country_pop, null);
        contentView.setFocusable(true); // 这个很重要
        contentView.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        final ListView listView = (ListView) contentView.findViewById(R.id.contrayList);

        SimpleAdapter simplead = new SimpleAdapter(this, listems,
                R.layout.country_item, new String[]{"country_name", "id", "code", "status"},
                new int[]{R.id.countryText});
        listView.setAdapter(simplead);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                code = listems.get(position).get("code");
                countryCheckBox.setText(listems.get(position).get("country_name"));
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setOnDismissListener(new poponDismissListener());

    }

    /**
     * 可以选国家电话号码类型数据获取
     */
    private void country() {
        listems = new ArrayList<HashMap<String, String>>();
        OkHttpUtils
                .post()
                .url(NetUrl.country)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ConuntryBean conuntryBean = new Gson().fromJson(response, ConuntryBean.class);
                        for (int count = 0; conuntryBean.getData().size() > count; count++) {
                            ConuntryBean.DataBean dataBean = conuntryBean.getData().get(count);
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("id", dataBean.getId());
                            hashMap.put("country_name", dataBean.getCountry_name());
                            hashMap.put("code", dataBean.getCode());
                            hashMap.put("status", dataBean.getStatus());
                            listems.add(hashMap);
                        }
                        code = listems.get(0).get("code");
                        countryCheckBox.setText(listems.get(0).get("country_name"));
                        initPopWindow();
                    }
                });
    }

    /**
     * 用户信息填写
     */
    private void getmyinfo() {
        OkHttpUtils
                .post()
                .url(NetUrl.getmyinfo)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        myInfoBean = new Gson().fromJson(response, MyInfoBean.class);
                        if (myInfoBean.getStatus() == 200) {
                            if (!myInfoBean.getData().getFace().equals("")) {
                                Picasso.with(getApplication()).load(myInfoBean.
                                        getData().getFace().replaceAll("\\\\", "")).into(head_faceImage);
                            }

                            if (!myInfoBean.getData().getQr_code().equals("")) {
                                Picasso.with(getApplication()).load(myInfoBean.getData().getQr_code().replaceAll("\\\\", "")).into(qr_codeImage);
                            }

                            realNameEdit.setText(myInfoBean.getData().getReal_name());
                            departmentNameEdit.setText(myInfoBean.getData().getDepartment_name());
                            memberRoleEdit.setText(myInfoBean.getData().getMember_role());
                            phoneEdit.setText(myInfoBean.getData().getMb_phone());
                            if (!myInfoBean.getData().getWork_phone().equals("")) {
                                workPhone.setText(myInfoBean.getData().getWork_phone());
                            }
                            if (!myInfoBean.getData().getPhone().equals("")) {
                                extensionTelephone.setText(myInfoBean.getData().getPhone());
                            }
                            userEmailText.setText(myInfoBean.getData().getEmail());
                        } else {
                            SuperCustomToast.getInstance(getApplicationContext()).show(myInfoBean.getMsg());
                        }

                    }
                });
    }


    @OnClick({R.id.join_teamBtn, R.id.staff_analysis_RaidoBtn, R.id.exit_team_RadioBtn, R.id.exit_RadioBtn, R.id.take_overWorkText,
            R.id.connect_workText, R.id.software_suggtionText, R.id.countryCheckBox, R.id.head_faceImage, R.id.qr_codeImage,
            R.id.cancel_actionBtn, R.id.saveDataBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.join_teamBtn://加入团队
                startActivity(new Intent(this, Scann_Activity.class));

                break;

            case R.id.staff_analysis_RaidoBtn://人员分析
                startActivity(new Intent(this, Users_Information_Activitiy.class));
                break;
            case R.id.exit_team_RadioBtn://退出团队
                initsurePop();
                break;
            case R.id.exit_RadioBtn://退出登录
                FlyingFishIntance.getInstance().clear();
                startActivity(new Intent(UserInformation_Activity.this, LoginActivity.class));
                finish();
                break;
            case R.id.take_overWorkText://接手工作
                break;
            case R.id.connect_workText://交接工作

                break;
            case R.id.software_suggtionText://软件建议
                startActivity(new Intent(UserInformation_Activity.this, SuggestionSoftWare_Activity.class));
                break;
            case R.id.cancel_actionBtn://放弃
                finish();
                break;
            case R.id.countryCheckBox://国家选择
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.setWidth(countryCheckBox.getWidth() + 40);
                    popupWindow.showAsDropDown(countryCheckBox);
                }
                break;
            case R.id.head_faceImage:
                bottomwindow(view);
                break;
            case R.id.qr_codeImage:
                Intent intent = new Intent(UserInformation_Activity.this, SpaceImageDetailActivity.class);
                int[] location = new int[2];
                qr_codeImage.getLocationOnScreen(location);
                intent.putExtra("img", myInfoBean.getData().getQr_code());//必须
                intent.putExtra("locationX", location[0]);//必须
                intent.putExtra("locationY", location[1]);//必须
                intent.putExtra("width", qr_codeImage.getWidth());//必须
                intent.putExtra("height", qr_codeImage.getHeight());//必须
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.saveDataBtn:
                editmyinfo();
                break;
        }
    }

    private void editmyinfo() {
        if (newPasswrodEdit.toString().equals(newPasswordAffirmEdit.getText().toString())) {
            String passwordDifferen = getResources().getString(R.string.password_different);
            SuperCustomToast.getInstance(getApplicationContext()).show(passwordDifferen);
            return;
        }
        if (upImg.equals("")) {
            String str = new String(getString(R.string.please_up_headimg));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }
        OkHttpUtils
                .post()
                .url(NetUrl.editmyinfo)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("real_name", realNameEdit.getText().toString())
                .addParams("department_name", departmentNameEdit.getText().toString())
                .addParams("member_role", memberRoleEdit.getText().toString())
                .addParams("mb_phone", phoneEdit.getText().toString())
                .addParams("work_phone", workPhone.getText().toString())
                .addParams("phone", phoneEdit.getText().toString())
                .addParams("url",upImg)
                .addParams("old_password", oldPasswordEdit.getText().toString())
                .addParams("new_password", newPasswrodEdit.toString().toString())
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            SuperCustomToast.getInstance( getApplicationContext()).show(jsonObject.getString("msg"));
                            if (jsonObject.getInt("status") == 200) {
                                setResult(FlyingFishIntance.getInstance().EXIT_TEAM);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    void bottomwindow(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.window_popup, null);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
        setButtonListeners(layout);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new poponDismissListener());
        backgroundAlpha(0.5f);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void setButtonListeners(LinearLayout layout) {

        Button camera = (Button) layout.findViewById(R.id.camera);
        Button gallery = (Button) layout.findViewById(R.id.gallery);
        Button cancel = (Button) layout.findViewById(R.id.cancel);

        camera.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    //在此处添加你的按键处理 xxx
                    Img = new DateFormat().format("yyyyMMdd", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    cameraFile = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + Img);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
                    startActivityForResult(intent, REQUEST_CAMERA_IMAGE);
                    popupWindow.dismiss();
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
//                    //在此处添加你的按键处理 xxx
//                    // TODO Auto-generated method stub
//                    //使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片
//                    Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
//                    getAlbum.setType(IMAGE_TYPE);
//                    startActivityForResult(getAlbum, 10);
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(intent, REQUEST_PICTURE_CHOOSE);
                    popupWindow.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    backgroundAlpha(1f);
                }
            }
        });
    }

    /**
     * 人脸识别
     */
    private void faceRecognition() {
        if (mImage != null) {
            if (mFaceDetector == null) {
                /**
                 * 离线人脸检测功能需要单独下载支持离线人脸的SDK
                 * 请开发者前往语音云官网下载对应SDK
                 */
                SuperCustomToast.getInstance(getApplicationContext()).show("本SDK不支持离线人脸检测");
                return;
            }
            // 启动图片人脸检测
            String result = mFaceDetector.detectARGB(mImage);
            // 解析人脸结果
            mFaces = ParseResult.parseResult(result);
            if (null != mFaces && mFaces.length > 0) {
                for (FaceRect face : mFaces) {
                    if (face.bound.left > 40) {
                        face.bound.left = face.bound.left - 40;
                    } else if (face.bound.left > 20) {
                        face.bound.left = face.bound.left - 20;
                    }

                    if (face.bound.top > 80) {
                        face.bound.top = face.bound.top - 80;
                    } else if (face.bound.top > 60) {
                        face.bound.top = face.bound.top - 60;
                    } else if (face.bound.top > 40) {
                        face.bound.top = face.bound.top - 40;
                    }


                    if (face.bound.right + 50 < mImage.getWidth()) {
                        face.bound.right = face.bound.right + 50;
                    } else if (face.bound.right + 30 < mImage.getWidth()) {
                        face.bound.top = face.bound.top - 30;
                    }

                    if (face.bound.bottom + 30 < mImage.getHeight()) {
                        face.bound.bottom = face.bound.bottom + 30;
                    } else if (face.bound.bottom + 15 < mImage.getHeight()) {
                        face.bound.top = face.bound.top - 15;
                    }

                    int width = face.bound.right - face.bound.left;
                    int height = face.bound.bottom - face.bound.top;

                    Bitmap bmp = Bitmap.createBitmap(mImage, face.bound.left, face.bound.top, width, height, null,
                            false);
                    head_faceImage.setImageBitmap(bmp);
                    up_Img(saveBitmapFile(bmp));
                }

            } else {
                // 在无人脸的情况下，判断结果信息
                int errorCode = 0;
                JSONObject object;
                try {
                    object = new JSONObject(result);
                    errorCode = object.getInt("ret");
                } catch (JSONException e) {
                }
                // errorCode!=0，表示人脸发生错误，请根据错误处理
                if (ErrorCode.SUCCESS == errorCode) {
                    SuperCustomToast.getInstance(getApplicationContext()).show("没有检测到人脸");
                } else {
                    SuperCustomToast.getInstance(getApplicationContext()).show("错误码" + errorCode);
                }
                head_faceImage.setImageBitmap(mImage);
                up_Img(saveBitmapFile(mImage));
            }
        } else {
            SuperCustomToast.getInstance(getApplicationContext()).show("请选择图片后再检测");
        }
    }

    /**
     * Bitmap对象保存味图片文件
     *
     * @param bitmap
     */
    public File saveBitmapFile(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/face.png");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 图片上传
     *
     * @param file
     */
    private void up_Img(File file) {
        head_faceImage.setEnabled(false);
        FlyingFishIntance.getInstance().uploadFileInThreadByOkHttp(file, new UserCallback(getApplicationContext()) {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("result").equals("1")) {
                        upImg = jsonObject.getString("data").substring(2, jsonObject.getString("data").length() - 2);
                        upImg = upImg.replaceAll("\\\\", "");
                        head_faceImage.setEnabled(true);
                    }
                    SuperCustomToast.getInstance(getApplicationContext()).show(jsonObject.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICTURE_CHOOSE && resultCode == Activity.RESULT_OK) {
            if ("file".equals(data.getData().getScheme())) {
                // 有些低版本机型返回的Uri模式为file
                upImg = data.getData().getPath();
            } else {
                // Uri模型为content
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(data.getData(), proj,
                        null, null, null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                upImg = cursor.getString(idx);
                cursor.close();
            }
            try {
                FileInputStream fis = new FileInputStream(upImg);
                mImage = BitmapFactory.decodeStream(fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            faceRecognition();

        } else if (requestCode == REQUEST_CAMERA_IMAGE && resultCode == Activity.RESULT_OK) {
            if (cameraFile.exists()) {
                try {
                    FileInputStream fis = new FileInputStream(cameraFile);
                    mImage = BitmapFactory.decodeStream(fis);
                    fis.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                faceRecognition();
            }
        }

    }


    private void initsurePop() {
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pomenu_item, null);
        contentView.setFocusable(true); // 这个很重要
        contentView.setFocusableInTouchMode(true);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        popupsureWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupsureWindow.setContentView(contentView);


        popupsureWindow.setFocusable(true);
        popupsureWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupsureWindow.setOnDismissListener(new poponDismissListener());
        TextView messageText = (TextView) contentView.findViewById(R.id.popText);
        TextView popSureBtn = (TextView) contentView.findViewById(R.id.popSureBtn);
        TextView popCancelBtn = (TextView) contentView.findViewById(R.id.popCancelBtn);

        messageText.setText("您确定退出团队");
        popupsureWindow.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);
        popSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outgroup();
            }
        });
        popCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupsureWindow.dismiss();
            }
        });
    }

    /**
     * 退出团队
     */
    private void outgroup() {

        OkHttpUtils
                .post()
                .url(NetUrl.outgroup)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("status") == 200) {
                                popupsureWindow.dismiss();
                                setResult(FlyingFishIntance.getInstance().EXIT_TEAM);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 监听popupWindow消失
     */
    private class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            countryCheckBox.setChecked(false);
            backgroundAlpha(1f);
        }
    }

}
