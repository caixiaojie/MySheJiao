package com.flyingfish.activity;

import android.app.Activity;

public class MainActivity extends Activity {
//    @BindView(R.id.chant_RadioBtn)
//    RadioButton chantRadioBtn;
//    @BindView(R.id.email_RadioBtn)
//    RadioButton emailRadioBtn;
//    @BindView(R.id.note_RadioBtn)
//    RadioButton noteRadioBtn;
//    @BindView(R.id.black_RadioBtn)
//    RadioButton blackRadioBtn;
//    @BindView(R.id.function_RadioGroup)
//    RadioGroup function_RadioGroup;
//    @BindView(R.id.search_Edit)
//    EditText search_Edit;
//    private ChooseAnimatorsAdapter animAdapter;
//    private List<IndexFrilistBean.DataBean.GroupFriInfoBean> datas;
//    private ArrayList<GradeBean> gradeDatas;
//    private List<IndexSendInfo.DataBean> memberDatas;
//    private MRecyclerView mMessageRecyclerView;
//    private RecyclerView mGradeRecyclerView;
//    private RecyclerView mMemberRecyclerView;
//    private GradeRecyclerAdapter gradeRecycler;
//    private MemberRecyclerAdapter mMemberAdapter;
//    private GestureDetector mGestureDetector;
//    private SwipeWraper swipelayout;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_activity);
//        FlyingFishIntance.getInstance().addActivity(this);
//        ButterKnife.bind(this);
//        initMessageRecycler();
//        initGradeRecyclerview();
//        initUserRecyclerview();
//        initMessageData();
//        indexGrade();
//        indexSendInfo();
//        initRadioGroup();
//
//
//    }
//
//    private void initRadioGroup() {
//        function_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == chantRadioBtn.getId()) {
//
//                } else if (checkedId == emailRadioBtn.getId()) {
//
//                } else if (checkedId == noteRadioBtn.getId()) {
//
//                } else if (checkedId == blackRadioBtn.getId()) {
//
//                }
//            }
//        });
//    }
//
//    /**
//     * 等级Recyclerview
//     */
//    private void initGradeRecyclerview() {
//        mGradeRecyclerView = (RecyclerView) findViewById(R.id.grade_Horizontal_Recyclerview);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向滚动的RecycleView
//        mGradeRecyclerView.setLayoutManager(linearLayoutManager);
//        gradeDatas = new ArrayList<GradeBean>();
//        gradeRecycler = new GradeRecyclerAdapter(this, gradeDatas) {
//            @Override
//            public void convert(final RecyclerView.ViewHolder holder, final int position) {
//                GradeMViewHolder viewHolder = (GradeMViewHolder) holder;
//
//                viewHolder.gradeText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (!isChecked) {
//                            gradeDatas.get(position).setIscheck("0");
//                        } else {
//                            gradeDatas.get(position).setIscheck("1");
//                            String grades = "";
//                            for (int count = 0; count < gradeDatas.size(); count++) {
//                                if (gradeDatas.get(count).getIscheck().equals("1")) {
//                                    if (grades.equals("")) {
//                                        grades = gradeDatas.get(count).getGrade();
//                                    } else {
//                                        grades = grades + "," + gradeDatas.get(count).getGrade();
//                                    }
//                                }
//                            }
//                            search("", grades, "", "", "2");
//                        }
//                    }
//                });
//            }
//        };
//        mGradeRecyclerView.setAdapter(gradeRecycler);
//    }
//
//    /**
//     * 成员Recyclerview初始化
//     */
//    private void initUserRecyclerview() {
//        mMemberRecyclerView = (RecyclerView) findViewById(R.id.member_Horizontal_Recyclerview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向滚动的RecycleView
//        mMemberRecyclerView.setLayoutManager(linearLayoutManager);
//        memberDatas = new ArrayList<IndexSendInfo.DataBean>();
//        //1.make full data test
//        mMemberAdapter = new MemberRecyclerAdapter(this, memberDatas) {
//            @Override
//            public void convert(final RecyclerView.ViewHolder holder, final int position) {
//
//            }
//        };
//        mMemberRecyclerView.setAdapter(mMemberAdapter);
//    }
//
//
//    /**
//     * 首页消息RecyclerView初始化
//     */
//    private void initMessageRecycler() {
//
//        mMessageRecyclerView = (MRecyclerView) findViewById(R.id.message_Horizontal_Recyclerview);
//        mMessageRecyclerView.setPullRefreshEnabled(true);
//        mMessageRecyclerView.setLoadingMoreEnabled(true);
//        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        datas = new ArrayList<IndexFrilistBean.DataBean.GroupFriInfoBean>();
//
//        //1.make full data test
//        MarshonRecyclerAdapter adapter = new MarshonRecyclerAdapter(this, datas) {
//            @Override
//            public void convert(final RecyclerView.ViewHolder holder, final int position) {
//                View deleteView = holder.itemView.findViewById(R.id.tv_delete);
//                View appearView = holder.itemView.findViewById(R.id.tv_appear);
//                swipelayout = (SwipeWraper) holder.itemView.findViewById(R.id.swipelayout);
//
//                appearView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//
//                deleteView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String frinds_id= datas.get(position).getMember_id();
//                        sendfriend(frinds_id,holder.getAdapterPosition());
//                    }
//                });
//            }
//        };
//
//        //2.set up recyclerview
//        final LinearLayoutManager manager = new LinearLayoutManager(this);
//        //3.给adapter装饰上animAdapter
//        animAdapter = new ChooseAnimatorsAdapter(adapter);
//        animAdapter.setAnimatorFlag(ChooseAnimatorsAdapter.ANIMATORFLAG_SCALE);
//        mMessageRecyclerView.setAdapter(animAdapter);
//        mMessageRecyclerView.setLayoutManager(manager);
//        mMessageRecyclerView.setLoadingListener(new MRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//
//            @Override
//            public void onLoadMore() {
//                mMessageRecyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        animAdapter.notifyDataSetChanged();
//                    }
//                }, 3000);
//            }
//        });
//    }
//
//    /**
//     *上报异常
//     * @param friend_id
//     */
//    private void sendfriend(String friend_id, final int postion){
//        OkHttpUtils
//                .post()
//                .url(NetUrl.delfriend)
//                .addParams("member_id",FlyingFishIntance.getInstance().getMember_Id())
//                .addParams("Friend_id",friend_id)
//                .build()
//                .execute(new UserCallback(getApplicationContext()) {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                    }
//                    @Override
//                    public void onResponse(String response, int id) {
//                        JSONObject jsonObject = null;
//                        try {
//                            jsonObject = new JSONObject(response);
//                            if (jsonObject.getString("status").equals("200")) {
//                                swipelayout.close(true);
//                                datas.remove(postion  - 1);
//                                animAdapter.notifyItemRemoved(postion);
//                            }
//
//                            SuperCustomToast.getInstance(getApplicationContext()).show(jsonObject.getString("msg"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                     }
//                });
//    }
//
////    /**
////     * 首页广告信息数据接口
////     */
////    private void indexadvert(){
////        OkHttpUtils
////                .post()
////                .url(NetUrl.indexadvert)
////                .addParams("member_id",FlyingFishIntance.getInstance().getMember_Id())
////                .build()
////                .execute(new UserCallback(getApplicationContext()) {
////                    @Override
////                    public void onError(Call call, Exception e, int id) {
////                    }
////                    @Override
////                    public void onResponse(String response, int id) {
//////                        AdvertBean advertBean = new Gson().fromJson(response,AdvertBean.class);
//////                        if(advertBean.getStatus()==200){
//////                            IndexFrilistBean indexFrilistBean = new IndexFrilistBean();
//////                            indexFrilistBean.getData().getGroup_fri_info().get(0).setFace(advertBean.getData().getLogo());
////////                            indexFrilistBean.getData().getGroup_fri_info().get(1).
//////                          }
////                    }
////                });
////    }
//
//
//    /**
//     * 等级接口
//     */
//    private void indexGrade() {
//        OkHttpUtils
//                .post()
//                .url(NetUrl.indexGrade)
//                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
//                .build()
//                .execute(new UserCallback(getApplicationContext()) {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.getString("status").equals("200")) {
//                                JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                for (int count = 0; count < jsonArray.length(); count++) {
//                                    gradeDatas.add(new GradeBean(jsonArray.getString(count), "0"));
//                                }
//                            } else {
//                                SuperCustomToast.getInstance(getApplicationContext()).show(jsonObject.getString("msg"));
//                            }
//                            gradeRecycler.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//    }
//
//    /**
//     * 空间消息接口
//     */
//    private void indexSendInfo() {
//        OkHttpUtils
//                .post()
//                .url(NetUrl.indexsendinfo)
//                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
//                .build()
//                .execute(new UserCallback(getApplicationContext()) {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        IndexSendInfo indexSendInfo = new Gson().fromJson(response, IndexSendInfo.class);
//                        if (indexSendInfo.getStatus() == 200) {
//                            memberDatas.addAll(indexSendInfo.getData());
//                        } else {
//                            SuperCustomToast.getInstance(getApplicationContext()).show(indexSendInfo.getMsg());
//                        }
//                        mMemberAdapter.notifyDataSetChanged();
//                    }
//                });
//    }
//
//    /**
//     * 首页消息数据接口
//     */
//    private void initMessageData() {
//        OkHttpUtils
//                .post()
//                .url(NetUrl.indexfrilist)
//                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
//                .build()
//                .execute(new UserCallback(getApplicationContext()) {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        IndexFrilistBean indexFrilistBean = new Gson().fromJson(response, IndexFrilistBean.class);
//                        if (indexFrilistBean.getStatus() == 200) {
//                            FlyingFishIntance.getInstance().setSetGrade(indexFrilistBean.getData().getSet_grade());
//
//                            datas.addAll(indexFrilistBean.getData().getGroup_fri_info());
//                        } else {
//                            SuperCustomToast.getInstance(getApplicationContext()).show(indexFrilistBean.getMsg());
//                        }
//                    }
//                });
//    }
//
//    private void search(String search_value, String grade, String department, String depart_role, String type) {
//
//        FlyingFishIntance.search(search_value, grade, department, depart_role, type, new UserCallback(getApplicationContext()) {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == FlyingFishIntance.getInstance().EXIT_TEAM) {
//            indexGrade();
//            indexSendInfo();
//            initMessageData();
//        }
//    }
//
//    Handler handelr = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.arg1) {
//
//            }
//            return false;
//        }
//    });
//
//    @OnClick({R.id.member_Horizontal_Recyclerview, R.id.chant_RadioBtn, R.id.email_RadioBtn, R.id.note_RadioBtn, R.id.black_RadioBtn, R.id.user_messageImg, R.id.searchImg})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.member_Horizontal_Recyclerview:
//                startActivity(new Intent(this, LookServiceMessage_Activity.class));
//                break;
//            case R.id.chant_RadioBtn:
//                startActivity(new Intent(this, GroupChant_Activity.class));
//                break;
//            case R.id.email_RadioBtn:
//                 break;
//            case R.id.note_RadioBtn:
//                 break;
//            case R.id.black_RadioBtn:
//                startActivity(new Intent(this, Black_Activity.class));
//                break;
//            case R.id.user_messageImg:
//                startActivity(new Intent(this, UserInformation_Activity.class));
//                break;
//            case R.id.searchImg:
//                search(search_Edit.getText().toString(), "", "", "", "1");
//                break;
//        }
//    }
//


}
