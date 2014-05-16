package com.NG.activity;


import com.NG.db.ShixiDatabaseManager;

import com.ngstudio.zhaoshixi.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SettingFragment extends Fragment {
	
	private Context mContext;
	private ShixiDatabaseManager dbManager;
	
	private Button btn_reset;
	
	public SettingFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_setting, container,
				false);
		mContext = this.getActivity().getApplicationContext();
		dbManager = new ShixiDatabaseManager(mContext);
		
		btn_reset = (Button)rootView.findViewById(R.id.button_reset);
		
		btn_reset.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				reset();
			}
			
		});
		
		
		return rootView;
	}
	
	
	public void reset(){
		dbManager.resetItems();
		CharSequence text ;
		text = "清除全部数据";
		Toast toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
		toast.show();
	}

}
