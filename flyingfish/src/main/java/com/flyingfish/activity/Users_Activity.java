package com.flyingfish.activity;

import android.app.Activity;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;

import com.flyingfish.R;
import com.flyingfish.tool.NetUrl;
import com.flyingfish.tool.UserCallback;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class Users_Activity extends Activity {
    @BindView(R.id.usersGradView)
    GridView usersGradView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_activity);
        ButterKnife.bind(this);
        getgroupinfo();
    }

   private void getgroupinfo(){
       OkHttpUtils
               .post()
               .url(NetUrl.getgroupinfo)
               .addParams("group_id", "7")
                .build()
               .execute(new UserCallback( getApplicationContext()) {
                   @Override
                   public void onError(Call call, Exception e, int id) {
                   }
                   @Override
                   public void onResponse(String response, int id) {
                       GroupChantUsersBean groupChantUsersBean = new Gson().fromJson(response,GroupChantUsersBean.class);

                     }
               });
    }

    @OnClick(R.id.go_backImg)
    public void onClick() {
        finish();
    }

    //自定义适配器
    class PictureAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<Picture> pictures;

        public PictureAdapter() {

        }

        @Override
        public int getCount() {
            return pictures.size();
        }

        @Override
        public Object getItem(int position) {
            return pictures.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.users_adapter, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.headImgItem)
            CircleImageView headImgItem;
            @BindView(R.id.nameCheckBoxItem)
            CheckBox nameCheckBoxItem;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
