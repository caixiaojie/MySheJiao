package com.flyingfish.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingfish.R;
import com.flyingfish.adapter.BlabordAdapter;
import com.flyingfish.bean.AddBlaboard;
import com.flyingfish.bean.Blackboardindex;
import com.flyingfish.bean.EnterBlaboard;
import com.flyingfish.tool.FlyingFishIntance;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.SuperCustomToast;
import com.flyingfish.tool.UserCallback;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.flyingfish.tool.NetUrl.blackboardindex;

/**
 * Created by Administrator on 2017/2/10 0010.
 */
public class Black_Activity extends Activity {
    @BindView(R.id.img_delet)
    ImageView img_delet;
    @BindView(R.id.editText2)
    EditText et_name;
    @BindView(R.id.editText4)
    EditText et_phone;
    @BindView(R.id.editText7)
    EditText et_dept;
    @BindView(R.id.editText8)
    EditText et_email;
    @BindView(R.id.textView6)
    TextView tv_send;
    @BindView(R.id.listView4)
    PullToRefreshListView lv;
    @BindView(R.id.searchView)
    SearchView search;
    private List<Blackboardindex> data = new ArrayList<>();
    private BlabordAdapter adapter;
    private String name;
    private String dept;
    private String email;
    private String phone;
    private List<String> idData = new ArrayList<>();//存放所有id的集合
    private List<String> idDelete = new ArrayList<>();//存放需要删除的id集合
    private List<Blackboardindex> searchData = new ArrayList<>();
    private int page = 1;//默认为第一页
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_activity);
        ButterKnife.bind(this);
        FlyingFishIntance.getInstance().addActivity(this);
        lv.setMode(PullToRefreshBase.Mode.BOTH);//设置可以下拉刷新和上拉加载
        //lv.setRefreshing();//进入黑板，自动执行下拉刷新请求数据
        blackboardindex(1);//进入黑板,请求第一页数据
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //请求最新数据
                new MyAsyncTask().execute(NetUrl.blackboardindex,"1");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //请求下一页数据
                new MyAsyncTask().execute(NetUrl.blackboardindex,"2");
            }
        });
        //适配数据
        initEnterBlaboard();
        //搜索查询
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchData.clear();
                adapter = new BlabordAdapter(Black_Activity.this);
                lv.setAdapter(adapter);
                adapter.setData(searchData);
                adapter.notifyDataSetChanged();
                blackboardsearch(newText);
                return true;
            }
        });
    }

    //黑板的信息适配器
    private void initEnterBlaboard() {
        //设置适配器
        adapter = new BlabordAdapter(this);
        //加载空数据
        lv.setAdapter(adapter);
        //加载有数据
        adapter.setData(data);
        //更新适配器
        adapter.notifyDataSetChanged();
    }

    //初始化添加黑板信息的数据
    private void initDatas() {
        name = et_name.getText().toString();
        dept = et_dept.getText().toString();
        email = et_email.getText().toString();
        phone = et_phone.getText().toString();
        Blackboardindex blackboardBean = new Blackboardindex();
        blackboardBean.setPhone(phone);
        blackboardBean.setName(name);
        blackboardBean.setEmail(email);
        blackboardBean.setDept(dept);
        data.add(blackboardBean);
    }

    /**
     * 把添加数据上传到服务器
     */
    private void addSuccess() {
        OkHttpUtils.post()
                .url(NetUrl.addblaboard)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("name", name)
                .addParams("dept", dept)
                .addParams("phone", phone)
                .addParams("email", email)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SuperCustomToast.getInstance(getApplicationContext()).show("添加失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        AddBlaboard addBlaboard = new Gson().fromJson(response, AddBlaboard.class);
                        SuperCustomToast.getInstance(getApplicationContext()).show(addBlaboard.getMsg());
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    //进入黑板（分页，默认第一页是从1开始）
    private void blackboardindex(int page) {
        OkHttpUtils.post()
                .url(NetUrl.blackboardindex)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("page",page+"")
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        EnterBlaboard blaboard = new Gson().fromJson(response, EnterBlaboard.class);
                        if (blaboard.getStatus() == 200) {
                            SuperCustomToast.getInstance(getApplicationContext()).show(blaboard.getMsg());
                            List<EnterBlaboard.DataBean> dataBeanList = blaboard.getData();
                            if (dataBeanList != null) {
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    EnterBlaboard.DataBean dataBean = dataBeanList.get(i);
                                    String id1 = dataBean.getId();
                                    String id_item = id1;
                                    idData.add(id_item);
                                    String dept = dataBean.getDept();
                                    String email = dataBean.getEmail();
                                    String phone = dataBean.getPhone();
                                    String name = dataBean.getName();
                                    Blackboardindex blackboardBean = new Blackboardindex();
                                    blackboardBean.setDept(dept);
                                    blackboardBean.setEmail(email);
                                    blackboardBean.setName(name);
                                    blackboardBean.setPhone(phone);
                                    data.add(blackboardBean);
                                }
                                Log.e("info", idData.toString());
                            }
                        }
                    }
                });
    }

    /**
     * 点击发送
     */
    @OnClick({R.id.textView6, R.id.img_delet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView6:
                //初始化数据
                initDatas();
                initEnterBlaboard();
                setHintData();
                addSuccess();
                break;
            case R.id.img_delet:
                delete();
        }


    }

    /**
     * 点击发送后，将各值设置为hint
     */
    private void setHintData() {
        et_phone.setText("");
        et_email.setText("");
        et_dept.setText("");
        et_name.setText("");
    }

    /**
     * 删除
     */
    private void delete() {
        //拿到所有数据
        Map<Integer, Boolean> isCheck_delete = adapter.getMap();
        //获取到条目数量
        int count = adapter.getCount();
        //遍历删除
        for (int i = 0; i < count; i++) {
            int position = i - (count - adapter.getCount());
            //判断状态为true删除数据
            if (isCheck_delete.get(i) != null && isCheck_delete.get(i)) {
                //删除数据
                isCheck_delete.remove(i);
                String delete_id = idData.get(i);
                idDelete.add(delete_id);//将需要删除人员的id添加到集合中
                adapter.removeData(position);
            }
        }
        Log.e("info", idDelete.toString());
        adapter.notifyDataSetChanged();
        deleteSuccess();//判断是否删除成功
    }

    /**
     * 判断是否删除成功
     */
    private void deleteSuccess() {
        String del_str = idDelete.toString();
        String last_delStr = del_str.substring(1, del_str.length() - 1);//截取字符串
        String str = last_delStr.replaceAll(" ", "");//去除空格后的最终字符串
        Log.e("info","last_delStr="+last_delStr);
        Log.e("info","str="+str);
        OkHttpUtils.post()
                .url(NetUrl.delblkboard)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("ids", last_delStr)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response != null) {
                            Log.e("info","删除返回的信息:"+response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String msg = object.getString("msg");
                                SuperCustomToast.getInstance(getApplicationContext()).show(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    /**
     * 黑板信息查询
     */
    private void blackboardsearch(String searchStr) {
        OkHttpUtils.post()
                .url(NetUrl.blackboardsearch)
                .addParams("member_id", FlyingFishIntance.getInstance().getMember_Id())
                .addParams("value", searchStr)
                .build()
                .execute(new UserCallback(getApplicationContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("info", "搜索返回信息:" + response);
                        EnterBlaboard json = new Gson().fromJson(response, EnterBlaboard.class);
                        if (json.getStatus() == 200) {
                            List<EnterBlaboard.DataBean> beanList = json.getData();
                            for (int i = 0; i < beanList.size(); i++) {
                                EnterBlaboard.DataBean dataBean = beanList.get(i);
                                Blackboardindex bean = new Blackboardindex();
                                bean.setDept(dataBean.getDept());
                                bean.setEmail(dataBean.getEmail());
                                bean.setName(dataBean.getName());
                                bean.setPhone(dataBean.getPhone());
                                searchData.add(bean);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    class MyAsyncTask extends AsyncTask<String,Void,List<String>>{
        private String flag;
        @Override
        protected List<String> doInBackground(String... params) {
            NetUrl.blackboardindex = params[0];//请求的地址
            flag = params[1];//区分是上拉加载还是下拉刷新
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            lv.onRefreshComplete();//通知刷新控件异步请求事件完成
            if ("1".equals(flag)) {//下拉刷新
                //获取到数据
                data.clear();
                page = 1;
                blackboardindex(page);
            }else if ("2".equals(flag)) {//上拉加载
                page++;
                blackboardindex(page);
            }
        }
    }
}
