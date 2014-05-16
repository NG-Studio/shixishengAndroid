package com.NG.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.NG.adapter.MessageAdapter;
import com.NG.db.ShixiDatabaseManager;
import com.NG.db.ShixiItem;
import com.NG.db.ShixiMessage;
import com.NG.loader.ShixiMessageLoader;
import com.ngstudio.zhaoshixi.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class CollectFragment extends Fragment {

	private ListView listView;
	private MessageAdapter mAdapter;
	private List<ShixiMessage> mdList = new ArrayList<ShixiMessage>();
	List<ShixiItem> items = new ArrayList<ShixiItem>();

	private Context mContext;
	private ShixiDatabaseManager dbManager;

	public CollectFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_collect, container,
				false);
		mContext = this.getActivity().getApplicationContext();
		dbManager = new ShixiDatabaseManager(mContext);

		listView = (ListView) rootView.findViewById(R.id.collect_list);
		
		final Activity MainActivity = this.getActivity();
		listView.setDividerHeight(0);
		

		items = dbManager.queryMultipleItems();

		for (int i = 0; i < items.size(); i++) {
			ShixiItem it = items.get(i);
			ShixiMessage sm = new ShixiMessage();
			sm.setMessage_id(it.getItem_id());
			sm.setTitle(it.getTitle());
			sm.setSource(it.getSource());
			sm.setTime(it.getTime());

			mdList.add(sm);
		}
		
		mAdapter = new MessageAdapter(mContext, mdList);
		listView.setAdapter(mAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				System.out.println("DIANJI");
				Intent intent = new Intent();
				intent.setClass(MainActivity, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("item_id", items.get(arg2).getItem_id());
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		
		//setListView(items);

		return rootView;
	}
	
	public void setListView(List<ShixiItem> messages){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(ShixiItem m:messages){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message_id", m.getItem_id());
			map.put("title", m.getTitle());
			map.put("source", m.getSource());
			list.add(map);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(mContext, list, R.layout.test_sqlite_cell, new String[]{"message_id","title","source"}, new int[]{R.id.test_message_id,R.id.test_message_title,R.id.test_message_source});
	    listView.setAdapter(adapter);
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		dbManager.closeDB();
		super.onDestroy();

	}

}
