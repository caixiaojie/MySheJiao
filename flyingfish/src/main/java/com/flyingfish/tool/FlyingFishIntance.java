package com.flyingfish.tool;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class FlyingFishIntance {

    public int EXIT_TEAM =0x11;
    private static   FlyingFishIntance instance = null;
    private HashSet<Activity> activityList =new HashSet<Activity>();
     private String member_id;
    private String real_name;
    private String mb_depart_id;
    private String mb_phone;
    private String work_phone;
    private String phone;
    private String email;
    private String face;
    private String qr_code;
    private String token;
    private String token_exptime;
    private String registered_time;
    private String status;
    private String start_disable_time;
    private String end_disable_time;
    private String authkey;
    private String last_login_ip;
    private String last_login_time;
    private String info_is_complete;
    private String remark;
    private String setGrade;







    public String getReal_name() {
        return getInstance().real_name;
    }

    public void setReal_name(String real_name) {
        getInstance().real_name = real_name;
    }

    public String getMb_depart_id() {
        return getInstance().mb_depart_id;
    }

    public void setMb_depart_id(String mb_depart_id) {
        getInstance().mb_depart_id = mb_depart_id;
    }

    public String getMb_phone() {
        return getInstance().mb_phone;
    }

    public void setMb_phone(String mb_phone) {
        getInstance().mb_phone = mb_phone;
    }

    public String getWork_phone() {
        return getInstance().work_phone;
    }

    public void setWork_phone(String work_phone) {
        getInstance().work_phone = work_phone;
    }

    public String getPhone() {
        return getInstance().phone;
    }

    public void setPhone(String phone) {
        getInstance().phone = phone;
    }

    public String getEmail() {
        return getInstance().email;
    }

    public void setEmail(String email) {
        getInstance().email = email;
    }

    public String getFace() {
        return getInstance().face;
    }

    public void setFace(String face) {
        getInstance().face = face;
    }

    public String getQr_code() {
        return getInstance().qr_code;
    }

    public void setQr_code(String qr_code) {
        getInstance().qr_code = qr_code;
    }

    public String getToken() {
        return getInstance().token;
    }

    public void setToken(String token) {
        getInstance().token = token;
    }

    public String getToken_exptime() {
        return getInstance().token_exptime;
    }

    public void setToken_exptime(String token_exptime) {
        getInstance().token_exptime = token_exptime;
    }

    public String getRegistered_time() {
        return getInstance().registered_time;
    }

    public void setRegistered_time(String registered_time) {
        getInstance().registered_time = registered_time;
    }

    public String getStatus() {
        return getInstance().status;
    }

    public void setStatus(String status) {
        getInstance().status = status;
    }

    public String getStart_disable_time() {
        return getInstance().start_disable_time;
    }

    public void setStart_disable_time(String start_disable_time) {
        this.start_disable_time = start_disable_time;
    }

    public String getEnd_disable_time() {
        return getInstance().end_disable_time;
    }

    public void setEnd_disable_time(String end_disable_time) {
        getInstance().end_disable_time = end_disable_time;
    }

    public String getAuthkey() {
        return getInstance().authkey;
    }

    public void setAuthkey(String authkey) {
        getInstance().authkey = authkey;
    }

    public String getLast_login_ip() {
        return getInstance().last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        getInstance().last_login_ip = last_login_ip;
    }

    public String getLast_login_time() {
        return getInstance().last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getInfo_is_complete() {
        return getInstance().info_is_complete;
    }

    public void setInfo_is_complete(String info_is_complete) {
        getInstance().info_is_complete = info_is_complete;
    }

    public String getRemark() {
        return getInstance().remark;
    }

    public void setRemark(String remark) {
        getInstance().remark = remark;
    }

    public String getSetGrade() {
        return getInstance().setGrade;
    }

    public void setSetGrade(String setGrade) {
        getInstance().setGrade = setGrade;
    }


    public static void search(String search_value, String grade, String department, String depart_role, String type, UserCallback userCallback){
        OkHttpUtils
                .post()
                .url(NetUrl.searchfriend)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("type",  type)
                .addParams("search_value",search_value)
                .addParams("min_grade", grade)
                .addParams("name", department)
                .addParams("depart_role",depart_role)
                .build()
                .execute(userCallback);
    }




    public static FlyingFishIntance getInstance() {

        if (instance == null) {
            synchronized (FlyingFishIntance.class) {
                if (instance == null) {
                    instance = new FlyingFishIntance();
                }
            }
        }
        return instance;
    }


    public void setMember_Id(String userId) {
        getInstance().member_id = userId;
    }

    public String getMember_Id() {
        return  getInstance().member_id;
    }



    public  String compressImage(String filePath, String targetPath, int quality)  {
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if(degree!=0){//旋转照片角度，防止头像横着显示
            bm=rotateBitmap(bm,degree);
        }
        File outputFile=new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            }else{
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        }catch (Exception e){}
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public  Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * 获取照片角度
     * @param path
     * @return
     */
    public  int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     * @param bitmap
     * @param degress
     * @return
     */
    public  Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    // 压缩图片并上传
    public  void uploadFileInThreadByOkHttp(final File tempPic, UserCallback userCallback) {
        final String pic_path = tempPic.getPath();
        String targetPath = tempPic +tempPic.getName();
        //调用压缩图片的方法，返回压缩后的图片path
        final String compressImage = compressImage(pic_path, targetPath, 30);
        final File compressedPic = new File(compressImage);
        if (compressedPic.exists()) {
            uploadFileByOkHTTP( compressedPic,userCallback);
        } else {//直接上传
            uploadFileByOkHTTP( tempPic,userCallback);
        }
    }
    private void uploadFileByOkHTTP(File upHeadImg, UserCallback userCallback) {
        OkHttpUtils
                .post()
                .url(NetUrl.upimg_url)
                .addParams("ftype", "file")
                .addFile("upfile", upHeadImg.getName(), upHeadImg)
                 .build()
                .execute(userCallback);
    }


    //添加Activity到容器中
    public void addActivity(Activity activity)
    {
        getInstance().activityList.add(activity);
    }
    //遍历所有Activity并finish
    public void exit()
    {
        for(Activity activity:getInstance().activityList)
        {
            activity.finish();
        }
        System.exit(0);
    }

    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public  boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public void clear() {

    }
}
