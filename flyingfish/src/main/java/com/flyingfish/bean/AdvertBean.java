package com.flyingfish.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/2/3 0003.
 */
public class AdvertBean {

    /**
     * status : 200
     * msg : 获取数据成功
     * data : {"id":"1","member_id":"11","advert_name":"这是广告名称","advert_content":"这是广告内容","logo":"这是广告logo","url":"这是广告图片地址","type":"2","time":"1234314311","create_time":"2000-01-23 12:31:23","expiration_time":"2000-09-23 12:31:23"}
     */

    private int status;
    private String msg;
    /**
     * id : 1
     * member_id : 11
     * advert_name : 这是广告名称
     * advert_content : 这是广告内容
     * logo : 这是广告logo
     * url : 这是广告图片地址
     * type : 2
     * time : 1234314311
     * create_time : 2000-01-23 12:31:23
     * expiration_time : 2000-09-23 12:31:23
     */

    private DataBean data;

    public static AdvertBean objectFromData(String str) {
        return new Gson().fromJson(str, AdvertBean.class);
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
        private String member_id;
        private String advert_name;
        private String advert_content;
        private String logo;
        private String url;
        private String type;
        private String time;

        public static DataBean objectFromData(String str) {
            return new Gson().fromJson(str, DataBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getAdvert_name() {
            return advert_name;
        }

        public void setAdvert_name(String advert_name) {
            this.advert_name = advert_name;
        }

        public String getAdvert_content() {
            return advert_content;
        }

        public void setAdvert_content(String advert_content) {
            this.advert_content = advert_content;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }
}
