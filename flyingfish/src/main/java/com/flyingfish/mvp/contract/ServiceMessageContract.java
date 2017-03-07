package com.flyingfish.mvp.contract;


import com.flyingfish.bean.CommentConfig;
import com.flyingfish.bean.ServiceInfoBean;

import java.util.List;

/**
 * Created by suneee on 2016/7/15.
 */
public interface ServiceMessageContract {

    interface View extends BaseView{
        void update2DeleteCircle(String relation_id,int postion);
        void update2AddComment(int circlePosition, ServiceInfoBean.DataBean.OpListBean addItem);
        void update2DeleteComment(int circlePosition, String commentId);
        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
        void update2loadData(int loadType, List<ServiceInfoBean.DataBean> datas);
    }

    interface Presenter extends BasePresenter{
        void loadData(int loadType,List<ServiceInfoBean.DataBean> datas);
        void deleteCircle(final String circleId,int postion);

    }
}
