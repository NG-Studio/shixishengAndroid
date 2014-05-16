package com.NG.activity;

import java.util.ArrayList;
import java.util.List;

import com.NG.adapter.MessageAdapter;
import com.NG.db.ShixiMessage;
import com.NG.loader.ShixiMessageLoader;
import com.ngstudio.zhaoshixi.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CollectFragment extends Fragment {
	
	private ListView mListView;
	private MessageAdapter mAdapter;
	private List<ShixiMessage> mdList = new ArrayList<ShixiMessage>();
	
	private Context mContext;
	
	
	public CollectFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_message, container, false);
		
		
		
		
		
		
		
		
		
		return rootView;
	}
	
	
}
