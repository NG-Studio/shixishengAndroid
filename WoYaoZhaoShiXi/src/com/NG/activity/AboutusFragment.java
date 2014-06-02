package com.NG.activity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ngstudio.zhaoshixi.R;

public class AboutusFragment extends Fragment{
	
	private Context mContext;
	private Button checkVersionBth;
	public AboutusFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = this.getActivity().getApplicationContext();
		
		View rootView = inflater.inflate(R.layout.fragment_aboutus, container,
				false);
		checkVersionBth = (Button) rootView.findViewById(R.id.btn_check_version);
		checkVersionBth.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "check version",Toast.LENGTH_SHORT).show();
			}
			
		});
		
		
		return rootView;
	}
	
	
}
