package com.flyingfish.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/1/20 0020.
 */
public class LoginBean {

    /**
     * status : 200
     * msg : 登录成功
     * data : {"member_id":"15","real_name":"二师兄","mb_depart_id":"0","mb_phone":"18322365488","work_phone":"","phone":"","email":"1066394039@qq.com","face":"","qr_code":"","token":"bf3478f4738036e5b7f281e663fa3962","token_exptime":"1484019874","registered_time":"2017-01-09 04:37:05","status":"1","start_disable_time":"","end_disable_time":"","authkey":"dczj5wmxpcye0rc94qepofjgut2wqzhz","last_login_ip":"127.0.0.1","last_login_time":"2017-01-09 04:44:49","info_is_complete":"0","remark":""}
     */

    private int status;
    private String msg;
    /**
     * member_id : 15
     * real_name : 二师兄
     * mb_depart_id : 0
     * mb_phone : 18322365488
     * work_phone :
     * phone :
     * email : 1066394039@qq.com
     * face :
     * qr_code :
     * token : bf3478f4738036e5b7f281e663fa3962
     * token_exptime : 1484019874
     * registered_time : 2017-01-09 04:37:05
     * status : 1
     * start_disable_time :
     * end_disable_time :
     * authkey : dczj5wmxpcye0rc94qepofjgut2wqzhz
     * last_login_ip : 127.0.0.1
     * last_login_time : 2017-01-09 04:44:49
     * info_is_complete : 0
     * remark :
     */

    private DataBean data;

    public static LoginBean objectFromData(String str) {

        return new Gson().fromJson(str, LoginBean.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
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

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getMb_depart_id() {
            return mb_depart_id;
        }

        public void setMb_depart_id(String mb_depart_id) {
            this.mb_depart_id = mb_depart_id;
        }

        public String getMb_phone() {
            return mb_phone;
        }

        public void setMb_phone(String mb_phone) {
            this.mb_phone = mb_phone;
        }

        public String getWork_phone() {
            return work_phone;
        }

        public void setWork_phone(String work_phone) {
            this.work_phone = work_phone;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getQr_code() {
            return qr_code;
        }

        public void setQr_code(String qr_code) {
            this.qr_code = qr_code;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken_exptime() {
            return token_exptime;
        }

        public void setToken_exptime(String token_exptime) {
            this.token_exptime = token_exptime;
        }

        public String getRegistered_time() {
            return registered_time;
        }

        public void setRegistered_time(String registered_time) {
            this.registered_time = registered_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStart_disable_time() {
            return start_disable_time;
        }

        public void setStart_disable_time(String start_disable_time) {
            this.start_disable_time = start_disable_time;
        }

        public String getEnd_disable_time() {
            return end_disable_time;
        }

        public void setEnd_disable_time(String end_disable_time) {
            this.end_disable_time = end_disable_time;
        }

        public String getAuthkey() {
            return authkey;
        }

        public void setAuthkey(String authkey) {
            this.authkey = authkey;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getInfo_is_complete() {
            return info_is_complete;
        }

        public void setInfo_is_complete(String info_is_complete) {
            this.info_is_complete = info_is_complete;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
