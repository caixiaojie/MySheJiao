package com.flyingfish.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/2/15 0015.
 */
public class FriendBean {

    /**
     * status : 200
     * msg : 查询成功
     * data : {"member_id":"21","real_name":"张飞","department_name":"测试组","member_role":"测试人员"}
     */

    private int status;
    private String msg;
    /**
     * member_id : 21
     * real_name : 张飞
     * department_name : 测试组
     * member_role : 测试人员
     */

    private DataBean data;

    public static FriendBean objectFromData(String str) {

        return new Gson().fromJson(str, FriendBean.class);
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
        private String department_name;
        private String member_role;



        private String face;

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

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getMember_role() {
            return member_role;
        }

        public void setMember_role(String member_role) {
            this.member_role = member_role;
        }
        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
