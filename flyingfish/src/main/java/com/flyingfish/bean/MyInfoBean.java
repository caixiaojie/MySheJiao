package com.flyingfish.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/2/8 0008.
 */
public class MyInfoBean {

    /**
     * status : 200
     * msg : 获取数据成功
     * data : {"member_id":"11","real_name":"jess.z","mb_phone":"15998964153","work_phone":"","phone":"","email":"649669918@qq.com","department_name":"移动部","is_join_group":"0","agree_member_id":"","member_role":"互联网研发工程师","join_time":"","face":"http://qysj.softyao.com/upload/face/face_2.png","qr_code":"http://qysj.softyao.com/upload/face/face_3.png","status":"1","start_disable_time":"","end_disable_time":""}
     */

    private int status;
    private String msg;
    /**
     * member_id : 11
     * real_name : jess.z
     * mb_phone : 15998964153
     * work_phone :
     * phone :
     * email : 649669918@qq.com
     * department_name : 移动部
     * is_join_group : 0
     * agree_member_id :
     * member_role : 互联网研发工程师
     * join_time :
     * face : http://qysj.softyao.com/upload/face/face_2.png
     * qr_code : http://qysj.softyao.com/upload/face/face_3.png
     * status : 1
     * start_disable_time :
     * end_disable_time :
     */

    private DataBean data;

    public static MyInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, MyInfoBean.class);
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
        private String mb_phone;
        private String work_phone;
        private String phone;
        private String email;
        private String department_name;
        private String is_join_group;
        private String agree_member_id;
        private String member_role;
        private String join_time;
        private String face;
        private String qr_code;
        private String status;
        private String start_disable_time;
        private String end_disable_time;

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

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getIs_join_group() {
            return is_join_group;
        }

        public void setIs_join_group(String is_join_group) {
            this.is_join_group = is_join_group;
        }

        public String getAgree_member_id() {
            return agree_member_id;
        }

        public void setAgree_member_id(String agree_member_id) {
            this.agree_member_id = agree_member_id;
        }

        public String getMember_role() {
            return member_role;
        }

        public void setMember_role(String member_role) {
            this.member_role = member_role;
        }

        public String getJoin_time() {
            return join_time;
        }

        public void setJoin_time(String join_time) {
            this.join_time = join_time;
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
    }
}
