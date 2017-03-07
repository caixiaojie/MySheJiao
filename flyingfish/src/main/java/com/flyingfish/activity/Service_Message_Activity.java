package com.flyingfish.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flyingfish.R;
import com.flyingfish.adapter.CircleAdapter;
import com.flyingfish.bean.CommentConfig;
import com.flyingfish.bean.ServiceInfoBean;
import com.flyingfish.mvp.contract.ServiceMessageContract;
import com.flyingfish.mvp.presenter.ServiceMessagePresenter;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.flyingfish.util.CommonUtils;
import com.flyingfish.util.DividerItemDecoration;
import com.flyingfish.view.widgets.CommentListView;
import com.flyingfish.view.widgets.dialog.UpLoadDialog;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2017/2/10 0010.
 */
public class Service_Message_Activity extends Activity implements View.OnClickListener , ServiceMessageContract.View, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.recyclerView)
    SuperRecyclerView recyclerView;
    @BindView(R.id.title_relative)
    RelativeLayout title_relative;
    @BindView(R.id.go_backImg)
    ImageView goBackImg;
    @BindView(R.id.sendMessageEdit)
    ImageView sendMessageEdit;
    @BindView(R.id.sendIv)
    ImageView sendIv;
    @BindView(R.id.circleEt)
    EditText editText;
    @BindView(R.id.editTextBodyLl)
    LinearLayout edittextbody;

    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private LinearLayoutManager layoutManager;
    private UpLoadDialog uploadDialog;
     private CircleAdapter circleAdapter;
    private String friend_id = "";
    private int page = 0;
    private String time = "";
    private String count="";
    ServiceMessagePresenter presenter;
    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;
    private RelativeLayout bodyLayout;


    /**
     * 评论输入框高度
     */
    private int screenHeight;
    private int editTextBodyHeight;
    private int currentKeyboardH;
    private int selectCircleItemH;
    private int selectCommentItemOffset;
    private CommentConfig commentConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_message_activity);
        ButterKnife.bind(this);
        friend_id = getIntent().getStringExtra("friend_id");
        init();
    }

    /**
     * 数据初始化
     */
    private void getserviceinfo() {

        OkHttpUtils
                .post()
                .url(NetUrl.getserviceinfo)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("friend_id", friend_id)
                .addParams("time", time)
                .addParams("page", String.valueOf(page))
                .addParams("count", count)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ServiceInfoBean serviceInfoBean = new Gson().fromJson(response, ServiceInfoBean.class);

                        if (time.equals("")) {
                            time = serviceInfoBean.getPageinfo().getTime();
                            page = serviceInfoBean.getPageinfo().getPage();
                            count = serviceInfoBean.getPageinfo().getCount();
                            getserviceinfo();
                        }else {
                            presenter.loadData(TYPE_PULLREFRESH,serviceInfoBean.getData());
                        }

                        time = serviceInfoBean.getPageinfo().getTime();
                        page = serviceInfoBean.getPageinfo().getPage();
                        count = serviceInfoBean.getPageinfo().getCount();
                    }
                });
    }



    /**
     * 控件初始化
     */
    private void init() {
        presenter = new ServiceMessagePresenter(this);

        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    //发布评论
                    String content =  editText.getText().toString().trim();
                    if(TextUtils.isEmpty(content)){
                         SuperCustomToast.getInstance( getApplicationContext()).show("评论内容不能为空...");
                        return;
                    }
                    presenter.addComment(content, commentConfig);

                }
                updateEditTextBodyVisible(View.GONE, null);
            }
        });


        goBackImg.setOnClickListener(this);
        sendMessageEdit.setOnClickListener(this);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setLoadingMore(true);
         recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        circleAdapter = new CircleAdapter(getApplicationContext());
        circleAdapter.setServiceMessagePresenter(presenter);
        recyclerView.setAdapter(circleAdapter);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getserviceinfo();
                    }
                }, 1000);
            }
        };
        recyclerView.setRefreshListener(refreshListener);

        //实现自动下拉刷新功能
        recyclerView.getSwipeToRefresh().post(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshing(true);//执行下拉刷新的动画
                refreshListener.onRefresh();//执行数据加载操作
            }
        });


        initUploadDialog();
        setViewTreeObserver();
    }
    /**
     * 获取状态栏高度
     * @return
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setViewTreeObserver() {
        bodyLayout = (RelativeLayout) findViewById(R.id.bodyLayout);
        final ViewTreeObserver swipeRefreshLayoutVTO = bodyLayout.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                bodyLayout.getWindowVisibleDisplayFrame(r);
                int statusBarH =  getStatusBarHeight();//状态栏高度
                int screenH = bodyLayout.getRootView().getHeight();
                if(r.top != statusBarH ){
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);

                if(keyboardH == currentKeyboardH){//有变化时才处理，否则会陷入死循环
                    return;
                }

                currentKeyboardH = keyboardH;
                screenHeight = screenH;//应用屏幕的高度
                editTextBodyHeight = edittextbody.getHeight();

                if(keyboardH<150){//说明是隐藏键盘的情况
                    updateEditTextBodyVisible(View.GONE, null);
                    return;
                }
                //偏移listview
                if(layoutManager!=null && commentConfig != null){
                    layoutManager.scrollToPositionWithOffset(commentConfig.circlePosition  , getListviewOffset(commentConfig));
                }
            }
        });
    }

    /**
     * 测量偏移量
     * @param commentConfig
     * @return
     */
    private int getListviewOffset(CommentConfig commentConfig) {
        if(commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        //int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        int listviewOffset = screenHeight - selectCircleItemH - currentKeyboardH - editTextBodyHeight - title_relative.getHeight();
        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            listviewOffset = listviewOffset + selectCommentItemOffset;
        }
         return listviewOffset;
    }
    /**
     * 初始化
     */
    private void initUploadDialog() {
        uploadDialog = new UpLoadDialog(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_backImg:

                break;
            case R.id.sendMessageEdit:
//                Intent intent = new Intent( getApplicationContext(), SendServiceMessage_Fragment.class);
//                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void update2DeleteCircle(String relation_id,final int postion) {
        delsendinfo(relation_id,postion);
    }

    /**
     * 删除评论
     * @param relation_id
     */
    private void delsendinfo(String relation_id , final int postion) {
        OkHttpUtils
                .post()
                .url(NetUrl.delfriend)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                 .addParams("relation_id", relation_id)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            if (jsonObject.getString("status").equals("200")){
                                circleAdapter.getDatas().remove(postion);
                                 circleAdapter.notifyDataSetChanged();
                            }else {
                                SuperCustomToast.getInstance(getApplicationContext()).show(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    @Override
    public void update2AddComment(int circlePosition, ServiceInfoBean.DataBean.OpListBean addItem) {
        if(addItem != null){
            ServiceInfoBean.DataBean item = (ServiceInfoBean.DataBean) circleAdapter.getDatas().get(circlePosition);
            commentinfo(item,addItem);
            //circleAdapter.notifyItemChanged(circlePosition+1);
        }
        //清空评论文本
        editText.setText("");
    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentId) {

    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        this.commentConfig = commentConfig;
        edittextbody.setVisibility(visibility);
        measureCircleItemHighAndCommentItemOffset(commentConfig);
        if(View.VISIBLE==visibility){
            editText.requestFocus();
            //弹出键盘
            CommonUtils.showSoftInput( editText.getContext(),  editText);

        }else if(View.GONE==visibility){
            //隐藏键盘
            CommonUtils.hideSoftInput( editText.getContext(),  editText);
        }
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig){
        if(commentConfig == null)
            return;

        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition  );

        if(selectCircleItem != null){
            selectCircleItemH = selectCircleItem.getHeight();
        }

        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if(commentLv!=null){
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if(selectCommentItem != null){
                    //选择的commentItem距选择的CircleItem底部的距离
                    selectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if(parentView != null){
                            selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
    }

    @Override
    public void update2loadData(int loadType, List<ServiceInfoBean.DataBean> datas) {

        if (loadType == TYPE_PULLREFRESH){
            recyclerView.setRefreshing(false);
            circleAdapter.setDatas(datas);
        }else if(loadType == TYPE_UPLOADREFRESH){
            circleAdapter.getDatas().addAll(datas);
        }
        circleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(edittextbody != null && edittextbody.getVisibility() == View.VISIBLE){
                //edittextbody.setVisibility(View.GONE);
                updateEditTextBodyVisible(View.GONE, null);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 添加评论
     * @param item
     * @param addItem
     */
    private void commentinfo(final ServiceInfoBean.DataBean item, final ServiceInfoBean.DataBean.OpListBean addItem){
        OkHttpUtils
                .post()
                .url(NetUrl.commentinfo)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("content", addItem.getComment_content())
                .addParams("relation_id", item.getRelation_id())
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            if (jsonObject.getString("status").equals("200")){
                                item.getOp_list().add(addItem);
                                circleAdapter.notifyDataSetChanged();
                            }else {
                                SuperCustomToast.getInstance(getApplicationContext()).show(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}