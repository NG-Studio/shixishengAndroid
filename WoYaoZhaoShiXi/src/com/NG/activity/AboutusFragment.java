package com.NG.activity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ngstudio.zhaoshixi.R;

public class AboutusFragment extends Fragment{
	
	private Context mContext;
	
	public AboutusFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_aboutus, container,
				false);
		
		
		
		return rootView;
	}
	
}
