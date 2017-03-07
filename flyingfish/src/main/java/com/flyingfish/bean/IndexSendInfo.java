package com.flyingfish.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/1/22 0022.
 */
public class IndexSendInfo {

    /**
     * status : 200
     * data : [{"member_id":"13","real_name":"xiaowu","face":""},{"member_id":"14","real_name":"真实","face":""}]
     */

    private int status;



    private String msg;
    /**
     * member_id : 13
     * real_name : xiaowu
     * face :
     */

    private List<DataBean> data;

    public static IndexSendInfo objectFromData(String str) {

        return new Gson().fromJson(str, IndexSendInfo.class);
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String member_id;
        private String real_name;
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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
