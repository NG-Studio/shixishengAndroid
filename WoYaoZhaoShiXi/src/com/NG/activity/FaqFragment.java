package com.NG.activity;

import com.ngstudio.zhaoshixi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FaqFragment extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	

		View rootView = inflater.inflate(R.layout.fragment_faq, container,
				false);
		return rootView;
	}
}
