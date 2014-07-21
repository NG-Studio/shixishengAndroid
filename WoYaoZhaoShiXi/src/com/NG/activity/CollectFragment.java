package com.NG.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.NG.adapter.MessageAdapter;
import com.NG.db.ShixiDatabaseManager;
import com.NG.db.ShixiItem;
import com.NG.db.ShixiItemInSqlite;
import com.NG.db.ShixiMessage;
import com.NG.util.MyUtils;
import com.ngstudio.zhaoshixi.R;

public class CollectFragment extends Fragment {

	private ListView listView;
	private MessageAdapter mAdapter;
	private List<ShixiMessage> smList = new ArrayList<ShixiMessage>();
	List<ShixiItemInSqlite> items = new ArrayList<ShixiItemInSqlite>();

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
		MainActivity.setTitle("我的收藏");
		listView.setDividerHeight(0);
		

		items = dbManager.queryMultipleItemsOnline();		
		List<ShixiItemInSqlite> collectedList = new ArrayList<ShixiItemInSqlite>();
		for(ShixiItemInSqlite it:items){
			if(it.getIs_collected()==1)
				collectedList.add(it);
		}
		smList = MyUtils.ItemListInSql2MessageList(collectedList);
		

		mAdapter = new MessageAdapter(mContext, smList);
		listView.setAdapter(mAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				System.out.println("DIANJI");
				Intent intent = new Intent();
				intent.setClass(MainActivity, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("item_id", smList.get(arg2).getMessage_id());
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
