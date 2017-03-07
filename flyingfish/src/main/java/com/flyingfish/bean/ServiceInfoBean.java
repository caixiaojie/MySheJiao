package com.flyingfish.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
public class ServiceInfoBean {
    public final static String TYPE_URL = "1";
    public final static String TYPE_IMG = "2";
    public final static String TYPE_VIDEO = "3";
    /**
     * status : 200
     * msg : 获取数据成功
     * data : [{"id":"126","receive_member_id":"11","send_member_id":"13","relation_id":"89","content":"这是14发的内容","picture":[""],"type":"1","create_time":"1287233177","ex_time":"1497283177","time_slot":"5000000","status":"1","send_mb_face":"http://qysj.softyao.com/upload/face/face_1.png","send_mb_real_name":"xiaowu","send_mb_id":"13","op_list":[{"id":"1","relation_id":"89","member_id":"11","comment_content":"这个可以有","comment_time":"1487645143","real_name":"jess.z"},{"id":"2","relation_id":"89","member_id":"11","comment_content":"这个可以有","comment_time":"1487645418","real_name":"jess.z"}]},{"id":"127","receive_member_id":"11","send_member_id":"13","relation_id":"89","content":"这是14发的内容","picture":[""],"type":"1","create_time":"1287233177","ex_time":"1497283177","time_slot":"5000000","status":"1","send_mb_face":"http://qysj.softyao.com/upload/face/face_1.png","send_mb_real_name":"xiaowu","send_mb_id":"13","op_list":[{"id":"1","relation_id":"89","member_id":"11","comment_content":"这个可以有","comment_time":"1487645143","real_name":"jess.z"},{"id":"2","relation_id":"89","member_id":"11","comment_content":"这个可以有","comment_time":"1487645418","real_name":"jess.z"}]},{"id":"128","receive_member_id":"11","send_member_id":"13","relation_id":"89","content":"这是14发的内容","picture":[""],"type":"1","create_time":"1287233177","ex_time":"1497283177","time_slot":"5000000","status":"1","send_mb_face":"http://qysj.softyao.com/upload/face/face_1.png","send_mb_real_name":"xiaowu","send_mb_id":"13","op_list":[{"id":"1","relation_id":"89","member_id":"11","comment_content":"这个可以有","comment_time":"1487645143","real_name":"jess.z"},{"id":"2","relation_id":"89","member_id":"11","comment_content":"这个可以有","comment_time":"1487645418","real_name":"jess.z"}]},{"id":"123","receive_member_id":"11","send_member_id":"13","relation_id":"88","content":"这是11发的内容","picture":[""],"type":"1","create_time":"1287231961","ex_time":"1497281961","time_slot":"5000000","status":"1","send_mb_face":"http://qysj.softyao.com/upload/face/face_1.png","send_mb_real_name":"xiaowu","send_mb_id":"13","op_list":[]},{"id":"124","receive_member_id":"11","send_member_id":"13","relation_id":"88","content":"这是11发的内容","picture":[""],"type":"1","create_time":"1287231961","ex_time":"1497281961","time_slot":"5000000","status":"1","send_mb_face":"http://qysj.softyao.com/upload/face/face_1.png","send_mb_real_name":"xiaowu","send_mb_id":"13","op_list":[]},{"id":"122","receive_member_id":"11","send_member_id":"12","relation_id":"88","content":"这是11发的内容","picture":["http://qysj.softyao.com/upload/face/face_5.png","http://qysj.softyao.com/upload/face/face_5.png","http://qysj.softyao.com/upload/face/face_5.png"],"type":"1","create_time":"1287231961","ex_time":"1497281961","time_slot":"5000000","status":"1","send_mb_face":"http://qysj.softyao.com/upload/face/face_1.png","send_mb_real_name":"xiaowu","send_mb_id":"12","op_list":[]},{"id":"118","receive_member_id":"11","send_member_id":"12","relation_id":"87","content":"这是11发的内容","picture":["http://qysj.softyao.com/upload/face/face_5.png","http://qysj.softyao.com/upload/face/face_5.png","http://qysj.softyao.com/upload/face/face_5.png"],"type":"1","create_time":"1287231959","ex_time":"1492645418","time_slot":"5000000","status":"2","send_mb_face":"http://qysj.softyao.com/upload/face/face_1.png","send_mb_real_name":"xiaowu","send_mb_id":"12","op_list":[]}]
     * pageinfo : {"friend_id":"13","page":0,"count":"10","time":"1487756197"}
     */

    private int status;
    private String msg;
    /**
     * friend_id : 13
     * page : 0
     * count : 10
     * time : 1487756197
     */

    private PageinfoBean pageinfo;
    /**
     * id : 126
     * receive_member_id : 11
     * send_member_id : 13
     * relation_id : 89
     * content : 这是14发的内容
     * picture : [""]
     * type : 1
     * create_time : 1287233177
     * ex_time : 1497283177
     * time_slot : 5000000
     * status : 1
     * send_mb_face : http://qysj.softyao.com/upload/face/face_1.png
     * send_mb_real_name : xiaowu
     * send_mb_id : 13
     * op_list : [{"id":"1","relation_id":"89","member_id":"11","comment_content":"这个可以有","comment_time":"1487645143","real_name":"jess.z"},{"id":"2","relation_id":"89","member_id":"11","comment_content":"这个可以有","comment_time":"1487645418","real_name":"jess.z"}]
     */

    private List<DataBean> data;


    public static ServiceInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, ServiceInfoBean.class);
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

    public PageinfoBean getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(PageinfoBean pageinfo) {
        this.pageinfo = pageinfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageinfoBean {
        private String friend_id;
        private int page;
        private String count;
        private String time;

        public static PageinfoBean objectFromData(String str) {

            return new Gson().fromJson(str, PageinfoBean.class);
        }

        public String getFriend_id() {
            return friend_id;
        }

        public void setFriend_id(String friend_id) {
            this.friend_id = friend_id;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class DataBean {
        private String id;
        private String receive_member_id;
        private String send_member_id;
        private String relation_id;
        private String content;
        private String type;
        private String create_time;
        private String ex_time;
        private String time_slot;
        private String status;
        private String send_mb_face;
        private String send_mb_real_name;
        private String send_mb_id;
        private List<String> picture;

        /**
         * op_info : {"id":"1","relation_id":"89","member_id":"11","comment_content":"这个可以有","comment_time":"1487645143","real_name":"jess.z"}
         */
        public boolean hasComment(){
            if(op_list!=null && op_list.size()>0){
                return true;
            }
            return false;
        }

        public boolean isExpand() {
            return expand;
        }

        public void setExpand(boolean expand) {
            this.expand = expand;
        }

        private boolean expand;

        /**
         * id : 1
         * relation_id : 89
         * member_id : 11
         * comment_content : 这个可以有
         * comment_time : 1487645143
         * real_name : jess.z
         */

        private List<OpListBean> op_list;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReceive_member_id() {
            return receive_member_id;
        }

        public void setReceive_member_id(String receive_member_id) {
            this.receive_member_id = receive_member_id;
        }

        public String getSend_member_id() {
            return send_member_id;
        }

        public void setSend_member_id(String send_member_id) {
            this.send_member_id = send_member_id;
        }

        public String getRelation_id() {
            return relation_id;
        }

        public void setRelation_id(String relation_id) {
            this.relation_id = relation_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getEx_time() {
            return ex_time;
        }

        public void setEx_time(String ex_time) {
            this.ex_time = ex_time;
        }

        public String getTime_slot() {
            return time_slot;
        }

        public void setTime_slot(String time_slot) {
            this.time_slot = time_slot;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSend_mb_face() {
            return send_mb_face;
        }

        public void setSend_mb_face(String send_mb_face) {
            this.send_mb_face = send_mb_face;
        }

        public String getSend_mb_real_name() {
            return send_mb_real_name;
        }

        public void setSend_mb_real_name(String send_mb_real_name) {
            this.send_mb_real_name = send_mb_real_name;
        }

        public String getSend_mb_id() {
            return send_mb_id;
        }

        public void setSend_mb_id(String send_mb_id) {
            this.send_mb_id = send_mb_id;
        }

        public List<String> getPicture() {
            return picture;
        }

        public void setPicture(List<String> picture) {
            this.picture = picture;
        }

        public List<OpListBean> getOp_list() {
            return op_list;
        }

        public void setOp_list(List<OpListBean> op_list) {
            this.op_list = op_list;
        }

        public static class OpListBean {
            private String id;
            private String relation_id;
            private String member_id;
            private String comment_content;
            private String comment_time;
            private String real_name;

            public static OpListBean objectFromData(String str) {

                return new Gson().fromJson(str, OpListBean.class);
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRelation_id() {
                return relation_id;
            }

            public void setRelation_id(String relation_id) {
                this.relation_id = relation_id;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getComment_content() {
                return comment_content;
            }

            public void setComment_content(String comment_content) {
                this.comment_content = comment_content;
            }

            public String getComment_time() {
                return comment_time;
            }

            public void setComment_time(String comment_time) {
                this.comment_time = comment_time;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }


        }
    }
}
