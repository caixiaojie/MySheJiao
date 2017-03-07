package com.flyingfish.bean;

import java.util.List;

/**
 * Created by fyl on 2017/3/4.
 */

public class EnterBlaboard {

    /**
     * status : 200
     * msg : 获取数据成功
     * data : [{"id":"2","re_id":"11","share_id":"11","name":"名字","dept":"团队","phone":"电话","email":"邮箱","create_time":"1484719410"},{"id":"3","re_id":"11","share_id":"11","name":"名字","dept":"团队","phone":"电话","email":"邮箱","create_time":"1484719410"},{"id":"41","re_id":"11","share_id":"11","name":"test","dept":"test","phone":"test","email":"test","create_time":"1488591322"},{"id":"50","re_id":"11","share_id":"11","name":"test","dept":"test","phone":"test","email":"test","create_time":"1488591781"},{"id":"56","re_id":"11","share_id":"11","name":"xiao","dept":"研发部","phone":"13547408958","email":"649669918@qq.com","create_time":"1488591887"},{"id":"62","re_id":"11","share_id":"11","name":"xiao","dept":"研发部","phone":"13547408958","email":"649669918@qq.com","create_time":"1488592013"},{"id":"68","re_id":"11","share_id":"11","name":"xiao","dept":"研发部","phone":"13547408958","email":"649669918@qq.com","create_time":"1488595274"},{"id":"74","re_id":"11","share_id":"11","name":"xiao","dept":"研发部","phone":"13547408958","email":"649669918@qq.com","create_time":"1488596140"},{"id":"80","re_id":"11","share_id":"11","name":"xiao","dept":"研发部","phone":"13547408958","email":"649669918@qq.com","create_time":"1488596187"},{"id":"86","re_id":"11","share_id":"11","name":"xiao","dept":"研发部","phone":"13547408958","email":"649669918@qq.com","create_time":"1488596214"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * re_id : 11
         * share_id : 11
         * name : 名字
         * dept : 团队
         * phone : 电话
         * email : 邮箱
         * create_time : 1484719410
         */

        private String id;
        private String re_id;
        private String share_id;
        private String name;
        private String dept;
        private String phone;
        private String email;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRe_id() {
            return re_id;
        }

        public void setRe_id(String re_id) {
            this.re_id = re_id;
        }

        public String getShare_id() {
            return share_id;
        }

        public void setShare_id(String share_id) {
            this.share_id = share_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
