package com.flyingfish.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/14 0014.
 */
public abstract class UserCallback extends Callback<String>
{
    private Context context;
    public UserCallback(Context context) {
        this.context =context;
    }

    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.e("UserCallback", "parseNetworkResponse: "+string);
        if ( string.substring(0, 3).equals("s-[")&&string.substring(string.length()-3,string.length() ).equals("]-e")){
            String result = string.substring(3, string.length() - 3);
              return result;
        }else {
            return null;
        }
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
         if (!isNetworkAvailable(context)){
            SuperCustomToast.getInstance(context.getApplicationContext()).show("网络不可用");
            return;
        }
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}

