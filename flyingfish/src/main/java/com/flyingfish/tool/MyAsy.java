package com.flyingfish.tool;

import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public class MyAsy extends AsyncTask<String,Void,List<String>> {
    private String falg;
    @Override
    protected List<String> doInBackground(String... params) {
        NetUrl.blackboardindex = params[0];
        return null;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
    }
}
