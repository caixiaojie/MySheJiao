package com.flyingfish.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/1/20 0020.
 */
public class ConuntryBean {

    /**
     * id : 1
     * country_name : 中国
     * code :
     * status : 1
     */

    private List<DataBean> data;

    public static ConuntryBean objectFromData(String str) {

        return new Gson().fromJson(str, ConuntryBean.class);
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String country_name;
        private String code;
        private String status;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
