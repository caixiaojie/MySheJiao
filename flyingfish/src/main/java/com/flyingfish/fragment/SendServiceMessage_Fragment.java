package com.flyingfish.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.flyingfish.R;
import com.flyingfish.adapter.GradeRecyclerAdapter;
import com.flyingfish.bean.GradeBean;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class SendServiceMessage_Fragment extends Fragment {
    @BindView(R.id.messageEdit)
    EditText messageEdit;
    private RecyclerView mGradeRecyclerView;
    private ArrayList<GradeBean> gradeDatas;
    private GradeRecyclerAdapter gradeRecycler;
    private String grades = "";
    private String alone_friend="";
    private String img_path="";
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.send_service_message_avtivity, null);
        initGradeRecyclerview();
        indexGrade();
        return view;
    }

    /**
     * 等级Recyclerview
     */
    private void initGradeRecyclerview() {
        mGradeRecyclerView = (RecyclerView)view.findViewById(R.id.messageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplication());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//横向滚动的RecycleView
        mGradeRecyclerView.setLayoutManager(linearLayoutManager);
        gradeDatas = new ArrayList<GradeBean>();
        gradeRecycler = new GradeRecyclerAdapter(getActivity().getApplication(), gradeDatas) {
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

                            for (int count = 0; count < gradeDatas.size(); count++) {
                                if (gradeDatas.get(count).getIscheck().equals("1")) {
                                    if (grades.equals("")) {
                                        grades = gradeDatas.get(count).getGrade();
                                    } else {
                                        grades = grades + "," + gradeDatas.get(count).getGrade();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        };
        mGradeRecyclerView.setAdapter(gradeRecycler);
    }


    /**
     * 等级接口
     */
    private void indexGrade() {
        OkHttpUtils
                .post()
                .url(NetUrl.indexGrade)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .build()
                .execute(new UserCallback(getActivity().getApplication()) {
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
                                    Log.e("TAG", "onResponse: "+ jsonArray.getString(count));
                                    gradeDatas.add(new GradeBean(jsonArray.getString(count), "0"));
                                }
                            } else {
                                SuperCustomToast.getInstance(getActivity().getApplication()).show(jsonObject.getString("msg"));
                            }
                            gradeRecycler.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 发送服务接口
     */
    public void sendinfo(){

        OkHttpUtils
                .post()
                .url(NetUrl.sendinfo)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("grade", grades)
                .addParams("content", messageEdit.getText().toString())
                .addParams("ima_path",  img_path)
                .addParams("alone_friend", alone_friend)

                .build()
                .execute(new UserCallback(getActivity().getApplication()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            SuperCustomToast.getInstance(getActivity().getApplication()).show(jsonObject.getString("msg"));
                            if (jsonObject.getString("status").equals("200")) {

                            }
                         } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @OnClick(R.id.go_backImg)
    public void onClick() {

    }

    @OnClick({R.id.voice_Img, R.id.sdpicture_Img, R.id.vedio_Img, R.id.photoing_Img, R.id.folder_Img,R.id.sendinfoText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.voice_Img:
                break;
            case R.id.sdpicture_Img:
                break;
            case R.id.vedio_Img:
                break;
            case R.id.photoing_Img:
                break;
            case R.id.folder_Img:
                break;
            case R.id.sendinfoText:
                sendinfo();
                break;
        }
    }
}
