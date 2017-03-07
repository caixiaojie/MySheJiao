package com.flyingfish.activity;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.flyingfish.R;
import com.flyingfish.bean.ConuntryBean;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.MD5;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SpanUtils;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.flyingfish.util.FaceRect;
import com.flyingfish.util.ParseResult;
import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.FaceDetector;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
 * Created by Administrator on 2017/1/6 0006.
 */
public class Regist_Activity extends Activity implements View.OnClickListener {
    @BindView(R.id.head_Img)
    CircleImageView headImg;
    @BindView(R.id.realNameEdit)
    EditText realNameEdit;
    @BindView(R.id.teamNameEdit)
    EditText teamNameEdit;
    @BindView(R.id.teamDuty)
    EditText teamDuty;
    @BindView(R.id.phoneEdit)
    EditText phoneEdit;
    @BindView(R.id.emailEdit)
    EditText emailEdit;
    @BindView(R.id.setup_passwordEdit)
    EditText setupPasswordEdit;
    @BindView(R.id.affirm_passwordEdit)
    EditText affirmPasswordEdit;
    @BindView(R.id.countryCheckBox)
    CheckBox countryCheckBox;

    private FaceDetector mFaceDetector;
    // 人脸识别结果
    private FaceRect[] mFaces;

    private ArrayList<HashMap<String, String>> listems;
    private PopupWindow popupWindow;
     private String code = "";
    private File cameraFile;
    private String Img;
    private String upImg="";
    private Bitmap mImage;
    public final static int REQUEST_PICTURE_CHOOSE = 1;
    public final static int  REQUEST_CAMERA_IMAGE = 2;
    public final static int REQUEST_CROP_IMAGE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_activity);
        FlyingFishIntance.getInstance().addActivity(this);
        ButterKnife.bind(this);
        country();
        initPopWindow();
        mFaceDetector = FaceDetector.createDetector(this, null);
    }

    /**
     * 初始化popwindow
     */
    private void initPopWindow() {
        while (listems == null) ;
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
     * 监听popupWindow消失
     */
    private class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            countryCheckBox.setChecked(false);
            backgroundAlpha(1);
        }
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
                    }
                });
    }

    private void register() {
        String str = "";
        if (realNameEdit.getText().toString().equals("")) {
            str = new String(getString(R.string.please_input_name));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }

        if (teamNameEdit.getText().toString().equals("")) {
            str = new String(getString(R.string.please_input_team));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }

        if (teamDuty.getText().toString().equals("")) {
            str = new String(getString(R.string.please_input_team_duty));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }

        if (!SpanUtils.isPhoneNumberValid(phoneEdit.getText().toString())) {
            str = new String(getString(R.string.please_input_rightphone));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }

        if (FlyingFishIntance.getInstance().isEmail(emailEdit.getText().toString())) {
            str = new String(getString(R.string.please_input_email));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }

        if (setupPasswordEdit.getText().toString().length() < 6) {
            str = new String(getString(R.string.please_input_set_up_password));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }

        if (affirmPasswordEdit.equals(setupPasswordEdit)) {
            str = new String(getString(R.string.password_different));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }
        if (upImg.equals("")) {
            str = new String(getString(R.string.please_up_headimg));
            SuperCustomToast.getInstance(getApplicationContext()).show(str);
            return;
        }
        String passwordMD5 = null;
        try {
            passwordMD5 = MD5.eccrypt(setupPasswordEdit.getText().toString());
            passwordMD5 = MD5.eccrypt(passwordMD5);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        OkHttpUtils
                .post()
                .url(NetUrl.register)
                .addParams("real_name", realNameEdit.getText().toString())
                .addParams("department_name", teamNameEdit.getText().toString())
                .addParams("member_role", teamDuty.getText().toString())
                .addParams("mb_phone", phoneEdit.getText().toString())
                .addParams("email", emailEdit.getText().toString())
                .addParams("code", code)
                .addParams("url",upImg)
                .addParams("password", passwordMD5)
                .addParams("again_password", affirmPasswordEdit.getText().toString())
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            SuperCustomToast.getInstance(getApplication()).show(jsonObject.getString("msg"));
                            if (jsonObject.getString("status").equals("200")) {
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
                    ;
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
                    startActivityForResult(intent,  REQUEST_PICTURE_CHOOSE);
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

    private void faceRecognition(){
        if(mImage!=null) {
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
                    if (face.bound.left > 40){
                        face.bound.left = face.bound.left - 40;
                    }else if (face.bound.left > 20){
                        face.bound.left = face.bound.left - 20;
                    }

                    if (face.bound.top > 80){
                        face.bound.top = face.bound.top - 80;
                    }else if (face.bound.top > 60){
                        face.bound.top = face.bound.top - 60;
                    }else if (face.bound.top > 40){
                        face.bound.top = face.bound.top - 40;
                    }

                    if (face.bound.right + 50 < mImage.getWidth()){
                        face.bound.right = face.bound.right + 50;
                    }else if (face.bound.right + 30 < mImage.getWidth()){
                        face.bound.top = face.bound.top - 30;
                    }

                    if (face.bound.bottom + 30 < mImage.getHeight()){
                        face.bound.bottom = face.bound.bottom + 30;
                    }else if (face.bound.bottom + 15< mImage.getHeight()){
                        face.bound.top = face.bound.top - 15;
                    }

                    int width =  face.bound.right - face.bound.left;
                    int height =  face.bound.bottom - face.bound.top;

                    Bitmap bmp = Bitmap.createBitmap(mImage, face.bound.left, face.bound.top, width, height, null,
                            false);
                    headImg.setImageBitmap(bmp);
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
                headImg.setImageBitmap(mImage);
            }
        }else {
            SuperCustomToast.getInstance(getApplicationContext()).show("请选择图片后再检测" );
        }

    }

    /**
     * Bitmap对象保存味图片文件
     * @param bitmap
     */
    public File saveBitmapFile(Bitmap bitmap){
        File file=new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/face.png");//将要保存图片的路径
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
     * @param file
     */
    private void up_Img( File file) {
        headImg.setEnabled(false);
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
                         headImg.setEnabled(true);
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

        if (requestCode == REQUEST_PICTURE_CHOOSE&&resultCode == Activity.RESULT_OK) {
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

        } else if (requestCode ==  REQUEST_CAMERA_IMAGE&&resultCode == Activity.RESULT_OK) {
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




    @OnClick({R.id.cancel_actionBtn, R.id.countryCheckBox, R.id.save_DataBtn,R.id.head_Img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_Img:
                bottomwindow(view);
                break;
            case R.id.cancel_actionBtn:
                finish();
                break;
            case R.id.save_DataBtn:
                register();
                break;
            case R.id.countryCheckBox:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.setWidth(countryCheckBox.getWidth() + 40);
                    popupWindow.showAsDropDown(countryCheckBox);
                }
                break;
        }
    }
}
