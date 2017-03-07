package com.flyingfish.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/1/22 0022.
 */
public class IndexFrilistBean {


    /**
     * status : 200
     * msg : 获取数据成功
     * data : {"sup_phone_series":"3","friends_number":"6","all_list":[{"chat_type":3,"info_type":1,"grade":"","not_read_count":"","member_id":"14","real_name":"真实","mb_phone":"18326548655","work_phone":"","phone":"","email":"1066394059@qq.com","department_name":"","member_role":"","face":"http://qysj.softyao.com/upload/face/face_1.png","mb_status":"1","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"28","not_read_count":"","real_name":"钱","mb_phone":"1568254","work_phone":"123123","phone":"15646889554","email":"06632qq.com","department_name":"生产部","member_role":"部门管理","face":"","mb_status":"2","grade":"0","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"29","not_read_count":"","real_name":"孙","mb_phone":"1688254","work_phone":"123123","phone":"1646889554","email":"106325@qom","department_name":"生产部","member_role":"部门管理","face":"","mb_status":"2","grade":"0","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"30","not_read_count":"","real_name":"李","mb_phone":"158254","work_phone":"123123","phone":"156489554","email":"1062.com","department_name":"生产部","member_role":"部门管理","face":"","mb_status":"2","grade":"0","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"24","not_read_count":"","real_name":"王","mb_phone":"15648254","work_phone":"123123","phone":"156489554","email":"10625@qq.com","department_name":"生产部","member_role":"部门管理","face":"","mb_status":"2","grade":"0","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"","not_read_count":"","real_name":"","mb_phone":"","work_phone":"","phone":"","email":"","department_name":"","member_role":"","face":"","mb_status":"","grade":"0","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"12","not_read_count":"","real_name":"xiaowu","mb_phone":"18356845146","work_phone":"","phone":"","email":"374145071@qq.com","department_name":"生产部","member_role":"","face":"http://qysj.softyao.com/upload/face/face_1.png","mb_status":"1","grade":"-1","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""}]}
     */

    private int status;
    private String msg;
    /**
     * sup_phone_series : 3
     * friends_number : 6
     * all_list : [{"chat_type":3,"info_type":1,"grade":"","not_read_count":"","member_id":"14","real_name":"真实","mb_phone":"18326548655","work_phone":"","phone":"","email":"1066394059@qq.com","department_name":"","member_role":"","face":"http://qysj.softyao.com/upload/face/face_1.png","mb_status":"1","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"28","not_read_count":"","real_name":"钱","mb_phone":"1568254","work_phone":"123123","phone":"15646889554","email":"06632qq.com","department_name":"生产部","member_role":"部门管理","face":"","mb_status":"2","grade":"0","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"29","not_read_count":"","real_name":"孙","mb_phone":"1688254","work_phone":"123123","phone":"1646889554","email":"106325@qom","department_name":"生产部","member_role":"部门管理","face":"","mb_status":"2","grade":"0","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"30","not_read_count":"","real_name":"李","mb_phone":"158254","work_phone":"123123","phone":"156489554","email":"1062.com","department_name":"生产部","member_role":"部门管理","face":"","mb_status":"2","grade":"0","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"24","not_read_count":"","real_name":"王","mb_phone":"15648254","work_phone":"123123","phone":"156489554","email":"10625@qq.com","department_name":"生产部","member_role":"部门管理","face":"","mb_status":"2","grade":"0","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"","not_read_count":"","real_name":"","mb_phone":"","work_phone":"","phone":"","email":"","department_name":"","member_role":"","face":"","mb_status":"","grade":"0","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""},{"info_type":1,"chat_type":"","member_id":"12","not_read_count":"","real_name":"xiaowu","mb_phone":"18356845146","work_phone":"","phone":"","email":"374145071@qq.com","department_name":"生产部","member_role":"","face":"http://qysj.softyao.com/upload/face/face_1.png","mb_status":"1","grade":"-1","isset_disable":0,"advert_name":"","advert_content":"","logo":"","url":"","group_id":""}]
     */

    private DataBean data;

    public static IndexFrilistBean objectFromData(String str) {

        return new Gson().fromJson(str, IndexFrilistBean.class);
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
        private String sup_phone_series;
        private String friends_number;
        /**
         * chat_type : 3
         * info_type : 1
         * grade :
         * not_read_count :
         * member_id : 14
         * real_name : 真实
         * mb_phone : 18326548655
         * work_phone :
         * phone :
         * email : 1066394059@qq.com
         * department_name :
         * member_role :
         * face : http://qysj.softyao.com/upload/face/face_1.png
         * mb_status : 1
         * isset_disable : 0
         * advert_name :
         * advert_content :
         * logo :
         * url :
         * group_id :
         */

        private List<AllListBean> all_list;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getSup_phone_series() {
            return sup_phone_series;
        }

        public void setSup_phone_series(String sup_phone_series) {
            this.sup_phone_series = sup_phone_series;
        }

        public String getFriends_number() {
            return friends_number;
        }

        public void setFriends_number(String friends_number) {
            this.friends_number = friends_number;
        }

        public List<AllListBean> getAll_list() {
            return all_list;
        }

        public void setAll_list(List<AllListBean> all_list) {
            this.all_list = all_list;
        }

        public static class AllListBean {
            private String chat_type;
            private String info_type;
            private String grade;
            private String not_read_count;
            private String member_id;
            private String real_name;
            private String mb_phone;
            private String work_phone;
            private String phone;
            private String email;
            private String department_name;
            private String member_role;
            private String face;
            private String mb_status;
            private int isset_disable;
            private String advert_name;
            private String advert_content;
            private String logo;
            private String url;
            private String group_id;

            public static AllListBean objectFromData(String str) {

                return new Gson().fromJson(str, AllListBean.class);
            }

            public String getChat_type() {
                return chat_type;
            }

            public void setChat_type(String chat_type) {
                this.chat_type = chat_type;
            }

            public String getInfo_type() {
                return info_type;
            }

            public void setInfo_type(String info_type) {
                this.info_type = info_type;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public String getNot_read_count() {
                return not_read_count;
            }

            public void setNot_read_count(String not_read_count) {
                this.not_read_count = not_read_count;
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

            public String getMb_status() {
                return mb_status;
            }

            public void setMb_status(String mb_status) {
                this.mb_status = mb_status;
            }

            public int getIsset_disable() {
                return isset_disable;
            }

            public void setIsset_disable(int isset_disable) {
                this.isset_disable = isset_disable;
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

            public String getGroup_id() {
                return group_id;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }
        }
    }
}
