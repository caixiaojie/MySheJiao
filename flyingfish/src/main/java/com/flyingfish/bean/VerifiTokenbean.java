package com.flyingfish.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/1/20 0020.
 */
public class VerifiTokenbean {

    /**
     * status : 200
     * msg : 验证成功
     * data : {"id":"17"}
     */

    private int status;
    private String msg;
    /**
     * id : 17
     */

    private DataBean data;

    public static VerifiTokenbean objectFromData(String str) {

        return new Gson().fromJson(str, VerifiTokenbean.class);
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
        private String id;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
