package com.flyingfish.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyingfish.R;
import com.flyingfish.view.ExpandTextView;
import com.flyingfish.view.SnsPopupWindow;
import com.flyingfish.view.widgets.CommentListView;


/**
 * Created by yiw on 2016/8/16.
 */
public abstract class ServiceMessageHolder extends RecyclerView.ViewHolder  {

    public final static int TYPE_URL = 1;
    public final static int TYPE_IMAGE = 2;
    public final static int TYPE_VIDEO = 3;

    public int viewType;

    public ImageView headIv;
    public TextView nameTv;

    /** 动态的内容 */
    public ExpandTextView contentTv;
    public TextView timeTv;
    public TextView deleteBtn;
    public ImageView snsBtn;

    public LinearLayout digCommentBody;
     /** 评论列表 */
    public CommentListView commentList;
    public SnsPopupWindow snsPopupWindow;


    public ServiceMessageHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;

        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);

        initSubView(viewType, viewStub);

        headIv = (ImageView) itemView.findViewById(R.id.headIv);
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);

        contentTv = (ExpandTextView) itemView.findViewById(R.id.contentTv);
         timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        deleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
        snsBtn = (ImageView) itemView.findViewById(R.id.snsBtn);
        commentList = (CommentListView)itemView.findViewById(R.id.commentList);
        digCommentBody = (LinearLayout) itemView.findViewById(R.id.digCommentBody);
        snsPopupWindow = new SnsPopupWindow(itemView.getContext());

    }

    public abstract void initSubView(int viewType, ViewStub viewStub);


}
