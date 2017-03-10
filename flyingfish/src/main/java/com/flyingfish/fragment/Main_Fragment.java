package com.flyingfish.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.flyingfish.R;
import com.flyingfish.activity.AskForExcuse_Activity;
import com.flyingfish.activity.Black_Activity;
import com.flyingfish.activity.Friend_Add_Activity;
import com.flyingfish.activity.Friends_Information_Activity;
import com.flyingfish.activity.GroupChant_Activity;
import com.flyingfish.activity.Service_Message_Activity;
import com.flyingfish.activity.UserInformation_Activity;
import com.flyingfish.adapter.ChooseAnimatorsAdapter;
import com.flyingfish.adapter.GradeRecyclerAdapter;
import com.flyingfish.adapter.MarshonRecyclerAdapter;
import com.flyingfish.adapter.MemberRecyclerAdapter;
import com.flyingfish.bean.GradeBean;
import com.flyingfish.bean.IndexFrilistBean;
import com.flyingfish.bean.IndexSendInfo;
import com.flyingfish.listener.SoftKeyBoardListener;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.flyingfish.util.DividerItemDecoration;
import com.google.gson.Gson;
import com.marshon.mrecyclerview.MRecyclerView;
import com.marshon.swipe.SwipeWraper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/10 0010.
 */
public class Main_Fragment extends Fragment {
    @BindView(R.id.chant_RadioBtn)
    RadioButton chantRadioBtn;
    @BindView(R.id.email_RadioBtn)
    RadioButton emailRadioBtn;
    @BindView(R.id.note_RadioBtn)
    RadioButton noteRadioBtn;
    @BindView(R.id.black_RadioBtn)
    RadioButton blackRadioBtn;
    @BindView(R.id.function_RadioGroup)
    RadioGroup function_RadioGroup;
    @BindView(R.id.search_Edit)
    EditText search_Edit;
    //搜索按钮id
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn_1)
    Button btn_1;
    @BindView(R.id.btn_2)
    Button btn_2;
    @BindView(R.id.btn_spc)
    Button btn_spc;

    private ChooseAnimatorsAdapter animAdapter;
    private List<IndexFrilistBean.DataBean.AllListBean> datas;
    private ArrayList<GradeBean> gradeDatas;
    private List<IndexSendInfo.DataBean> memberDatas;
    private MRecyclerView mMessageRecyclerView;
    private RecyclerView mGradeRecyclerView;
    private RecyclerView mMemberRecyclerView;
    private GradeRecyclerAdapter gradeRecycler;
    private MemberRecyclerAdapter mMemberAdapter;
    private View view;
    private SwipeWraper swipelayout;
    private ImageView head_Img_item;
    private LinearLayout userLinear;
    private MarshonRecyclerAdapter adapter_marshon;
    private ArrayList<IndexFrilistBean.DataBean.AllListBean> datas_search;
    private MarshonRecyclerAdapter adapter_search;

    //记录状态标记
    int f3 = 1;
    int f2 = 1;
    int f1 = 1;
    int f0 = 1;
    int f_1 = 1;
    int f_2 = 1;
    int f_spc = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_activity, null);
        ButterKnife.bind(this, view);
        initMessageRecycler();
 //       initGradeRecyclerview();
        initUserRecyclerview();
        initMessageData();
        //indexGrade();
        indexSendInfo();
        initRadioGroup();



        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                mMemberRecyclerView.setVisibility(View.GONE);
                initSearchRecyclear();
            }

            @Override
            public void keyBoardHide(int height) {
                mMemberRecyclerView.setVisibility(View.VISIBLE);
                mMessageRecyclerView.unregisterAdapterDataObserver(adapter_marshon);
                mMessageRecyclerView.setAdapter(adapter_marshon);
            }
        });

        return view;

    }

    /**
     * 搜索按钮的点击事件
     * @param view
     */
    @OnClick({R.id.btn3,R.id.btn2,R.id.btn1,R.id.btn0,R.id.btn_1,R.id.btn_2,R.id.btn_spc,})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn3:
                if (f3 % 2 != 0) {
                    btn3.setSelected(true);
                    //btn3.setTextColor(Color.WHITE);
                    f3++;
                }else {
                    btn3.setSelected(false);
                    //btn3.setTextColor(Color.BLACK);
                    f3++;
                }
                break;
            case R.id.btn2:
                if (f2 % 2 != 0) {
                    btn2.setSelected(true);
                    f2++;
                }else {
                    btn2.setSelected(false);
                    f2++;
                }
                break;
            case R.id.btn1:
                if (f1 % 2 != 0) {
                    btn1.setSelected(true);
                    f1++;
                }else {
                    btn1.setSelected(false);
                    f1++;
                }
                break;
            case R.id.btn0:
                if (f0 % 2 != 0) {
                    btn0.setSelected(true);
                    f0++;
                }else {
                    btn0.setSelected(false);
                    f0++;
                }
                break;
            case R.id.btn_1:
                if (f_1 % 2 != 0) {
                    btn_1.setSelected(true);
                    f_1++;
                }else {
                    btn_1.setSelected(false);
                    f_1++;
                }
                break;
            case R.id.btn_2:
                if (f_2 % 2 != 0) {
                    btn_2.setSelected(true);
                    f_2++;
                }else {
                    btn_2.setSelected(false);
                    f_2++;
                }
                break;
            case R.id.btn_spc:
                if (f_spc % 2 != 0) {
                    btn_spc.setSelected(true);
                    f_spc++;
                }else {
                    btn_spc.setSelected(false);
                    f_spc++;
                }
                break;
        }
    }

    /**
     * 初始化搜索功能
     */
    private void initSearchRecyclear() {

        mMessageRecyclerView = (MRecyclerView) view.findViewById(R.id.message_Horizontal_Recyclerview);
        datas_search = new ArrayList<IndexFrilistBean.DataBean.AllListBean>();
        adapter_search  = new MarshonRecyclerAdapter(getContext(), datas_search) {
            @Override
            public void convert(final RecyclerView.ViewHolder holder, final int position) {

            }

        };
        mMessageRecyclerView.setAdapter(adapter_search);
    }



    private void initRadioGroup() {
        function_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == chantRadioBtn.getId()) {

                } else if (checkedId == emailRadioBtn.getId()) {

                } else if (checkedId == noteRadioBtn.getId()) {

                } else if (checkedId == blackRadioBtn.getId()) {

                }
            }
        });
    }

    /**
     * 等级Recyclerview
     */
    private void initGradeRecyclerview() {
        mGradeRecyclerView = (RecyclerView) view.findViewById(R.id.grade_Horizontal_Recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向滚动的RecycleView
//        mGradeRecyclerView.setLayoutManager(linearLayoutManager);
        gradeDatas = new ArrayList<GradeBean>();
        gradeRecycler = new GradeRecyclerAdapter(getContext(), gradeDatas) {
            @Override
            public void convert(final RecyclerView.ViewHolder holder, final int position) {
                GradeMViewHolder viewHolder = (GradeMViewHolder) holder;

                viewHolder.gradeText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked) {
                            gradeDatas.get(position).setIscheck("0");
                        } else {
                            gradeDatas.get(position).setIscheck("1");
                            String grades = "";
                            for (int count = 0; count < gradeDatas.size(); count++) {
                                if (gradeDatas.get(count).getIscheck().equals("1")) {
                                    if (grades.equals("")) {
                                        grades = gradeDatas.get(count).getGrade();
                                    } else {
                                        grades = grades + "," + gradeDatas.get(count).getGrade();
                                    }
                                }
                            }
                            search("", grades, "", "", "2");
                        }
                    }
                });
            }
        };
        mGradeRecyclerView.setAdapter(gradeRecycler);
    }

    /**
     * 成员Recyclerview初始化
     */
    private void initUserRecyclerview() {
        mMemberRecyclerView = (RecyclerView) view.findViewById(R.id.member_Horizontal_Recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向滚动的RecycleView
        mMemberRecyclerView.setLayoutManager(linearLayoutManager);
        memberDatas = new ArrayList<IndexSendInfo.DataBean>();
        //1.make full data test
        mMemberAdapter = new MemberRecyclerAdapter(getContext(), memberDatas) {
            @Override
            public void convert(final RecyclerView.ViewHolder holder, final int position) {
                MViewHolder viewHolder = (MViewHolder) holder;
                LinearLayout linearLayout = (LinearLayout) viewHolder.itemView.findViewById(R.id.sevice_linea);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Service_Message_Activity.class);
                        intent.putExtra("friend_id", memberDatas.get(position).getMember_id());
                        startActivity(intent);
                    }
                });
            }
        };
        mMemberRecyclerView.setAdapter(mMemberAdapter);
    }


    /**
     * 首页消息RecyclerView初始化
     */
    private void initMessageRecycler() {

        mMessageRecyclerView = (MRecyclerView) view.findViewById(R.id.message_Horizontal_Recyclerview);
        mMessageRecyclerView.setPullRefreshEnabled(true);
        mMessageRecyclerView.setLoadingMoreEnabled(true);
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMessageRecyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
         datas = new ArrayList<IndexFrilistBean.DataBean.AllListBean>();

        //1.make full data test
        adapter_marshon = new MarshonRecyclerAdapter(getContext(), datas) {
            @Override
            public void convert(final RecyclerView.ViewHolder holder, final int position) {
                View deleteView = holder.itemView.findViewById(R.id.tv_delete);
                View appearView = holder.itemView.findViewById(R.id.tv_appear);
                swipelayout = (SwipeWraper) holder.itemView.findViewById(R.id.swipelayout);
                head_Img_item = (ImageView) holder.itemView.findViewById(R.id.head_Img_item);
                userLinear = (LinearLayout) holder.itemView.findViewById(R.id.userLinear);
                userLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0) {
                            Intent intent = new Intent(getActivity(), AskForExcuse_Activity.class);
                            intent.putExtra("friend_id", datas.get(position).getMember_id());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(), Friend_Add_Activity.class);
                            intent.putExtra("face", datas.get(position).getFace());
                            intent.putExtra("real_name", datas.get(position).getReal_name());
                            intent.putExtra("department_name", datas.get(position).getDepartment_name());
                            intent.putExtra("member_role", datas.get(position).getMember_role());
                            intent.putExtra("friend_id", datas.get(position).getMember_id());
                            startActivity(intent);
                        }
                    }
                });

                head_Img_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Friends_Information_Activity.class);
                        intent.putExtra("friend_id", datas.get(position).getMember_id());
                        startActivity(intent);
                    }
                });
                final String frinds_id = datas.get(position).getMember_id();
                appearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        report(frinds_id);
                    }
                });

                deleteView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delfriend(frinds_id, holder.getAdapterPosition());
                    }
                });
            }
        };

        //2.set up recyclerview
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        //3.给adapter装饰上animAdapter
        animAdapter = new ChooseAnimatorsAdapter(adapter_marshon);
        animAdapter.setAnimatorFlag(ChooseAnimatorsAdapter.ANIMATORFLAG_SCALE);
        mMessageRecyclerView.setAdapter(animAdapter);
        mMessageRecyclerView.setLayoutManager(manager);
        mMessageRecyclerView.setLoadingListener(new MRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mMessageRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animAdapter.notifyDataSetChanged();
                    }
                }, 3000);
            }
        });
    }


//    /**
//     * 首页广告信息数据接口
//     */
//    private void indexadvert(){
//        OkHttpUtils
//                .post()
//                .url(NetUrl.indexadvert)
//                .addParams("member_id",FlyingFishIntance.getInstance().getMember_Id())
//                .build()
//                .execute(new UserCallback(getApplicationContext()) {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                    }
//                    @Override
//                    public void onResponse(String response, int id) {
////                        AdvertBean advertBean = new Gson().fromJson(response,AdvertBean.class);
////                        if(advertBean.getStatus()==200){
////                            IndexFrilistBean indexFrilistBean = new IndexFrilistBean();
////                            indexFrilistBean.getData().getGroup_fri_info().get(0).setFace(advertBean.getData().getLogo());
//////                            indexFrilistBean.getData().getGroup_fri_info().get(1).
////                          }
//                    }
//                });
//    }


    /**
     * 等级接口
     */
    /*private void indexGrade() {
        OkHttpUtils
                .post()
                .url(NetUrl.indexGrade)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .build()
                .execute(new UserCallback(getContext().getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("200")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int count = 0; count < jsonArray.length(); count++) {

                                    gradeDatas.add(new GradeBean(jsonArray.getString(count), "0"));
                                }
                            } else {
                                SuperCustomToast.getInstance(getContext().getApplicationContext()).show(jsonObject.getString("msg"));
                            }
//                            gradeRecycler.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }*/

    /**
     * 空间消息接口
     */
    private void indexSendInfo() {
        OkHttpUtils
                .post()
                .url(NetUrl.indexsendinfo)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .build()
                .execute(new UserCallback(getContext().getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        IndexSendInfo indexSendInfo = new Gson().fromJson(response, IndexSendInfo.class);
                        if (indexSendInfo.getStatus() == 200) {
                            memberDatas.addAll(indexSendInfo.getData());
                        } else {
                            SuperCustomToast.getInstance(getContext().getApplicationContext()).show(indexSendInfo.getMsg());
                        }
                        mMemberAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 首页人员数据接口
     */
    private void initMessageData() {
        OkHttpUtils
                .post()
                .url(NetUrl.indexfrilist)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .build()
                .execute(new UserCallback(getContext().getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

//                        IndexFrilistBean indexFrilistBean = new Gson().fromJson(response, IndexFrilistBean.class);
//                        if (indexFrilistBean.getStatus() == 200) {
//                            FlyingFishIntance.getInstance().setSetGrade(indexFrilistBean.getData().getSup_phone_series());
//                            datas.addAll(indexFrilistBean.getData().getAll_list());
//                        } else {
//                            SuperCustomToast.getInstance(getContext().getApplicationContext()).show(indexFrilistBean.getMsg());
//                        }
                    }
                });
    }

    /**
     * 查找好友
     * @param search_value
     * @param grade
     * @param department
     * @param depart_role
     * @param type
     */
    private void search(String search_value, String grade, String department, String depart_role, String type) {

        FlyingFishIntance.search(search_value, grade, department, depart_role, type, new UserCallback(getContext().getApplicationContext()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }
        });
    }

    /**
     * 删除好友
     *
     * @param friend_id
     */
    private void delfriend(String friend_id, final int postion) {
        OkHttpUtils
                .post()
                .url(NetUrl.delfriend)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("Friend_id", friend_id)
                .build()
                .execute(new UserCallback(getContext().getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("200")) {
                                swipelayout.close(true);
//                                datas.remove(postion - 1);
                                animAdapter.notifyItemRemoved(postion);
                            }
                            SuperCustomToast.getInstance(getContext().getApplicationContext()).show(jsonObject.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 上报异常
     *
     * @param friend_id
     */
    private void report(String friend_id) {
        OkHttpUtils
                .post()
                .url(NetUrl.report)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("Friend_id", friend_id)
                .build()
                .execute(new UserCallback(getContext().getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("200")) {
                                swipelayout.close(true);
                            }
                            SuperCustomToast.getInstance(getContext().getApplicationContext()).show(jsonObject.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FlyingFishIntance.getInstance().EXIT_TEAM) {
            //indexGrade();
            indexSendInfo();
            initMessageData();
        }
    }

    Handler handelr = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1) {

            }
            return false;
        }
    });

    @OnClick({  R.id.chant_RadioBtn, R.id.email_RadioBtn, R.id.note_RadioBtn, R.id.black_RadioBtn, R.id.user_messageImg, R.id.searchImg})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.chant_RadioBtn:
                startActivity(new Intent(getContext(), GroupChant_Activity.class));
                break;
            case R.id.email_RadioBtn:
                break;
            case R.id.note_RadioBtn:
                /*Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("vnd.android-dir/mms-sms");*/
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:10086;1008611"));//群发用分号隔开
                intent.putExtra("sms_body","测试信息");
                startActivity(intent);
                break;
            case R.id.black_RadioBtn:
                startActivity(new Intent(getContext(), Black_Activity.class));
                break;
            case R.id.user_messageImg:
                startActivity(new Intent(getContext(), UserInformation_Activity.class));
                break;
            case R.id.searchImg:
                search(search_Edit.getText().toString(), "", "", "", "1");
                break;
        }
    }

}
