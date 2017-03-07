package com.flyingfish;

import android.app.Application;
import android.view.View;

import com.iflytek.cloud.SpeechUtility;


public class FlyfishApplication extends Application {

	public static View getContext() {
		return getContext();
	}

	@Override
	public void onCreate() {

		super.onCreate();
		SpeechUtility.createUtility(FlyfishApplication.this, "appid=" + getString(R.string.app_id));

	}

}
