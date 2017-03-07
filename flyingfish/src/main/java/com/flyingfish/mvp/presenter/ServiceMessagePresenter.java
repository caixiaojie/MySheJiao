package com.flyingfish.mvp.presenter;

import android.view.View;

import com.flyingfish.bean.CommentConfig;
import com.flyingfish.bean.ServiceInfoBean;
import com.flyingfish.listener.IDataRequestListener;
import com.flyingfish.mvp.contract.ServiceMessageContract;
import com.flyingfish.mvp.modle.ServiceModel;
import com.flyingfish.tool.FlyingFishIntance;

import java.util.List;

/**
 * @author yiw
 * @ClassName: CirclePresenter
 * @Description: 通知model请求服务器和通知view更新
 * @date 2015-12-28 下午4:06:03
 */
public class ServiceMessagePresenter implements ServiceMessageContract.Presenter {
    private ServiceModel serviceModel;
    private ServiceMessageContract.View view;

    public ServiceMessagePresenter(ServiceMessageContract.View view) {
        serviceModel = new ServiceModel();
        this.view = view;
    }

    @Override
    public void loadData(int loadType, List<ServiceInfoBean.DataBean> datas) {
        if (view != null) {
            view.update2loadData(loadType, datas);
        }
    }





    /**
     * @param relation_id
     * @param postion
     * @return void    返回类型
     * @throws
     * @Title: deleteCircle
     * @Description: 删除动态
     */
    @Override
    public void deleteCircle(final String relation_id, final int postion) {
        serviceModel.deleteCircle(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                if (view != null) {
                    view.update2DeleteCircle(relation_id, postion);
                }
            }
        });
    }


    /**
     * @param content
     * @param config  CommentConfig
     * @return void    返回类型
     * @throws
     * @Title: addComment
     * @Description: 增加评论
     */
     public void addComment(final String content, final CommentConfig config) {
        if (config == null) {
            return;
        }
        serviceModel.addComment(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                final ServiceInfoBean.DataBean.OpListBean newItem = new ServiceInfoBean.DataBean.OpListBean();
                newItem.setComment_content(content);
                newItem.setId(FlyingFishIntance.getInstance().getMember_Id());
                newItem.setReal_name(FlyingFishIntance.getInstance().getReal_name());
                if (view != null) {
                    view.update2AddComment(config.circlePosition, newItem);
                }
            }

        });
    }

    /**
     * @param @param circlePosition
     * @param @param commentId
     * @return void    返回类型
     * @throws
     * @Title: deleteComment
     * @Description: 删除评论
     */
    public void deleteComment(final int circlePosition, final String commentId) {
        serviceModel.deleteComment(new IDataRequestListener() {

            @Override
            public void loadSuccess(Object object) {
                if (view != null) {
                    view.update2DeleteComment(circlePosition, commentId);
                }
            }

        });
    }

    /**
     * @param commentConfig
     */
    public void showEditTextBody(CommentConfig commentConfig) {
        if (view != null) {
            view.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
        }
    }


    /**
     * 清除对外部对象的引用，反正内存泄露。
     */
    public void recycle() {
        this.view = null;
    }
}
