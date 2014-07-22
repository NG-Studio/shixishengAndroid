package com.NG.activity;

import android.support.v7.app.ActionBarActivity;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends ActionBarActivity{
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);       //统计时长
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
}
