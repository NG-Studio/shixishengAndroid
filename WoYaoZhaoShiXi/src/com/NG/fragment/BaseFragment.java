package com.NG.fragment;

import com.umeng.analytics.MobclickAgent;

import android.support.v7.app.ActionBarActivity;

public class BaseFragment extends ActionBarActivity {
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

}
