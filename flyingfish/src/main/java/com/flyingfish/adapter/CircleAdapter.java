package com.flyingfish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyingfish.R;
import com.flyingfish.activity.ImagePagerActivity;
import com.flyingfish.adapter.viewholder.ImageViewHolder;
import com.flyingfish.adapter.viewholder.ServiceMessageHolder;
import com.flyingfish.bean.ActionItem;
import com.flyingfish.bean.CommentConfig;
import com.flyingfish.bean.ServiceInfoBean;
import com.flyingfish.mvp.presenter.ServiceMessagePresenter;
import com.flyingfish.util.GlideCircleTransform;
import com.flyingfish.util.UrlUtils;
import com.flyingfish.view.ExpandTextView;
import com.flyingfish.view.MultiImageView;
import com.flyingfish.view.SnsPopupWindow;
import com.flyingfish.view.widgets.CommentListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/18 0018.
 */
public class CircleAdapter extends BaseRecycleViewAdapter {
    public Context context;

    public ServiceMessagePresenter presenter;


    public CircleAdapter(Context context) {
        this.context = context;
    }

    public void setServiceMessagePresenter(ServiceMessagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = 0;
        ServiceInfoBean.DataBean item = (ServiceInfoBean.DataBean) datas.get(position);
        if (ServiceInfoBean.TYPE_URL.equals(item.getType())) {
            itemType = Integer.parseInt(ServiceInfoBean.TYPE_URL);
        } else if (ServiceInfoBean.TYPE_IMG.equals(item.getType())) {
            itemType = Integer.parseInt(ServiceInfoBean.TYPE_IMG);
        } else if (ServiceInfoBean.TYPE_VIDEO.equals(item.getType())) {
            itemType = Integer.parseInt(ServiceInfoBean.TYPE_VIDEO);
        }
        return itemType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ServiceMessageHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_servicemessage_item, parent, false);

        viewHolder = new ImageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final ServiceMessageHolder holder = (ServiceMessageHolder) viewHolder;
        final ServiceInfoBean.DataBean dataBean = (ServiceInfoBean.DataBean) datas.get(position);
        Glide.with(context).load(dataBean.getSend_mb_face()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.headIv);
        holder.nameTv.setText(dataBean.getSend_mb_real_name());
        holder.contentTv.setText(dataBean.getContent());
        holder.timeTv.setText(times(dataBean.getCreate_time()));

        final List<ServiceInfoBean.DataBean.OpListBean> commentsDatas = dataBean.getOp_list();
        final SnsPopupWindow snsPopupWindow = holder.snsPopupWindow;
        boolean hasComment = dataBean.hasComment();
        if (!TextUtils.isEmpty(dataBean.getContent())) {
            holder.contentTv.setExpand(dataBean.isExpand());
            holder.contentTv.setExpandStatusListener(new ExpandTextView.ExpandStatusListener() {
                @Override
                public void statusChange(boolean isExpand) {
                    dataBean.setExpand(isExpand);
                }
            });
            holder.contentTv.setText(UrlUtils.formatUrlString(dataBean.getContent()));
        }
        holder.contentTv.setVisibility(TextUtils.isEmpty(dataBean.getContent()) ? View.GONE : View.VISIBLE);
        if (hasComment) {//处理评论列表
            holder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                @Override
                public void onItemClick(int commentPosition) {
                    ServiceInfoBean.DataBean.OpListBean commentItem = commentsDatas.get(commentPosition);
                }
            });
            holder.commentList.setVisibility(View.VISIBLE);
            holder.commentList.setDatas(commentsDatas);
            holder.digCommentBody.setVisibility(View.VISIBLE);
        } else {
            holder.commentList.setVisibility(View.GONE);
            holder.digCommentBody.setVisibility(View.GONE);
        }
        snsPopupWindow.update();
        snsPopupWindow.setmItemClickListener(new PopupItemClickListener(position, dataBean));
        holder.snsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出popupwindow
                snsPopupWindow.showPopupWindow(view);
            }
        });

        if (holder instanceof ImageViewHolder) {
            final List<String> photos = dataBean.getPicture();
            if (photos != null && photos.size() > 0) {
                ((ImageViewHolder) holder).multiImageView.setVisibility(View.VISIBLE);
                ((ImageViewHolder) holder).multiImageView.setList(photos);
                ((ImageViewHolder) holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //imagesize是作为loading时的图片size
                        ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                        ImagePagerActivity.startImagePagerActivity(context  , photos, position, imageSize);
                    }
                });
            } else {
                ((ImageViewHolder) holder).multiImageView.setVisibility(View.GONE);
            }
        }
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                if (presenter != null) {
                    presenter.deleteCircle(dataBean.getRelation_id(),position);
                }
            }
        });
    }

    private class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener {
        //动态在列表中的位置
        private int mCirclePosition;
        private long mLasttime = 0;
        private ServiceInfoBean.DataBean dataBean;

        public PopupItemClickListener(int circlePosition, ServiceInfoBean.DataBean circleItem) {
            this.mCirclePosition = circlePosition;
            this.dataBean = circleItem;
        }

        @Override
        public void onItemClick(ActionItem actionitem, int position) {
            if (presenter != null) {
                CommentConfig config = new CommentConfig();
                config.circlePosition = mCirclePosition;
                config.commentType = CommentConfig.Type.PUBLIC;
                presenter.showEditTextBody(config);
            }
        }
    }
    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
