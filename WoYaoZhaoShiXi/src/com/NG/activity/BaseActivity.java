package com.NG.activity;

import com.umeng.analytics.MobclickAgent;

import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);       //统计时长
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
}
