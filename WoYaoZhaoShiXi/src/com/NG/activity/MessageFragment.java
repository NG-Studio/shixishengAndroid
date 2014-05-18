package com.NG.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.NG.activity.DetailActivity.LoadData;
import com.NG.adapter.MessageAdapter;
import com.NG.db.ShixiDatabaseManager;
import com.NG.db.ShixiItemOnline;
import com.NG.db.ShixiMessage;
import com.NG.loader.ShixiMessageLoader;
import com.ngstudio.zhaoshixi.R;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class MessageFragment extends Fragment implements IXListViewListener {
	public static final String ARG_PLANET_NUMBER = "planet_number";
	private static final String TAG = "MessageFragment";

	private static long time_now = 0;
	private static long update_time = 0;
	private static long loadmore_time = 0;

	private XListView mListView;
	private MessageAdapter mAdapter;
	private List<ShixiMessage> mdList = new ArrayList<ShixiMessage>();
	List<ShixiMessage> newmdList = new ArrayList<ShixiMessage>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;

	private ShixiMessageLoader mMessageLoader;

	private ProgressDialog proDialog;
	private Context mContext;
	
	private static ShixiDatabaseManager dbManager;

	public MessageFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_message, container,
				false);

		getActivity().setTitle("全部实习");
		mContext = this.getActivity().getApplicationContext();
		
		dbManager = new ShixiDatabaseManager(mContext);

		// geneItems();
		mListView = (XListView) rootView.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);

		// mListView.setPullLoadEnable(false);
		// mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();

		// ProgressDialog
		proDialog = new ProgressDialog(this.getActivity());
		proDialog.setTitle(R.string.loading);
		proDialog.setMessage("请耐心等待...");

		mMessageLoader = new ShixiMessageLoader();

		new Thread(new LoadData()).start();
		proDialog.show();

		final Activity MainActivity = this.getActivity();
		mListView.setDividerHeight(0);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				System.out.println("DIANJI");
				Intent intent = new Intent();
				intent.setClass(MainActivity, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("item_id", mdList.get(arg2 - 1).getMessage_id());
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

		return rootView;
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {

				new Thread(new LoadData()).start();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				new Thread(new LoadMoreData()).start();
				onLoad();
			}
		}, 2000);

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {

				mAdapter = new MessageAdapter(mContext, mdList);
				mListView.setAdapter(mAdapter);
				proDialog.dismiss();
				
				ArrayList<ShixiItemOnline> onlineList = new ArrayList<ShixiItemOnline>();
				/*
				for(ShixiMessage m:mdList){
					ShixiItemOnline item = new ShixiItemOnline();
					item.setItem_id(m.getMessage_id());
					item.setTitle(m.getTitle());
					item.setTime(m.getTime());
					item.setSource(m.getSource());
					item.setSource_url(m.getSource_url());
					item.setIs_clicked(0);
					item.setIs_collected(0);
					onlineList.add(item);
				}		
				dbManager.addMultipleItemsOnline(onlineList);*/
				
				loadmore_time = Long.parseLong(mdList.get(mdList.size() - 1)
						.getTime()) - 1;
				System.out.println("loadmore_time = " + loadmore_time);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	};

	class LoadData implements Runnable {

		@Override
		public void run() {
			int choice = 0;
			Log.d(TAG, "run()");
			try {
				Date d = new Date();
				time_now = d.getTime() / 1000;
				String url = "http://211.155.86.159/online/info/get_message?startTime=0&endTime="
						+ time_now + "&count=50";
				// String url =
				// "http://211.155.86.159:8008/info/get_message?startTime=0&endTime="+time_now+"&count=50";

				mdList = mMessageLoader.parserMovieJson(url);
				handler.sendEmptyMessage(choice);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	class LoadMoreData implements Runnable {

		@Override
		public void run() {
			int choice = 0;
			Log.d(TAG, "run()");
			try {
				// Date d = new Date();
				// time_now = d.getTime()/1000;
				String url = "http://211.155.86.159/online/info/get_message?startTime=0&endTime="
						+ loadmore_time + "&count=20";
				// String url =
				// "http://211.155.86.159:8008/info/get_message?startTime=0&endTime="+loadmore_time+"&count=20";

				newmdList = mMessageLoader.parserMovieJson(url);

				handler1.sendEmptyMessage(choice);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private Handler handler1 = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {
				mdList.addAll(newmdList);
				mAdapter.notifyDataSetChanged();

				loadmore_time = Long.parseLong(mdList.get(mdList.size() - 1)
						.getTime()) - 1;
				System.out.println("loadmore_time = " + loadmore_time);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	};
}