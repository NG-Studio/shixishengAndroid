package com.NG.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.NG.adapter.MessageAdapter;
import com.NG.db.ShixiDatabaseManager;
import com.NG.db.ShixiMessage;
import com.NG.loader.ShixiMessageLoader;
import com.NG.util.MyUtils;
import com.NG.util.TimeUtils;
import com.ngstudio.zhaoshixi.R;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class MessageFragment extends Fragment implements IXListViewListener {
	private static final String TAG = "MessageFragment";

	// endtime
	private static long time_now = 0;
	private static long start_time = 0;
	private static long update_time = 0;
	private static long loadmore_time = 0;

	private XListView mListView;
	private MessageAdapter mAdapter;
	// 用于显示的List
	private List<ShixiMessage> showList = new ArrayList<ShixiMessage>();
	// 用于装载下拉刷新的List
	private List<ShixiMessage> updateList = new ArrayList<ShixiMessage>();
	// 装载loadmore数据的List
	private List<ShixiMessage> loadmoreList = new ArrayList<ShixiMessage>();

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
		final Activity MainActivity = this.getActivity();
		
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

		// new Thread(new LoadData()).start();
		// proDialog.show();
		loadDataInSql();

		
		mListView.setDividerHeight(0);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				System.out.println("DIANJI");
				Intent intent = new Intent();
				intent.setClass(MainActivity, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("item_id", showList.get(arg2 - 1).getMessage_id());
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

		return rootView;
	}

	private void loadDataInSql() {
		// TODO Auto-generated method stub
		showList = MyUtils.ItemListInSql2MessageList(dbManager
				.queryMultipleItemsOnline());
		mAdapter = new MessageAdapter(mContext, showList);
		mListView.setAdapter(mAdapter);
		try {

			start_time = Long.parseLong(showList.get(0).getTime());
			mListView.setRefreshTime(TimeUtils.longToMinute(start_time));
			//start_time = 1400483739;

		} catch (Exception e) {
			start_time = 0;
			e.printStackTrace();
		}

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

				new Thread(new LoadUpdateData()).start();
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

	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {
				//将下拉刷新得到的list存入数据库
				dbManager.addMultipleItemsOnline(MyUtils
						.MessageList2ItemInSqlList(updateList));
				//从数据库中查找全部数据 得到应该显示的list
				showList = MyUtils.ItemListInSql2MessageList(dbManager
						.queryMultipleItemsOnline());

				mAdapter = new MessageAdapter(mContext, showList);
				mListView.setAdapter(mAdapter);
				
				start_time = Long.parseLong(showList.get(0).getTime());
				loadmore_time = Long.parseLong(showList
						.get(showList.size() - 1).getTime()) - 1;

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	};

	class LoadUpdateData implements Runnable {

		@Override
		public void run() {
			int choice = 0;
			Log.d(TAG, "run()");
			try {
				Date d = new Date();
				time_now = d.getTime() / 1000;
				String url = "http://211.155.86.159/online/info/get_message?startTime="
						+ start_time + "&endTime=" + time_now + "&count=20";
				// String url =
				// "http://211.155.86.159:8008/info/get_message?startTime=0&endTime="+time_now+"&count=50";

				updateList = mMessageLoader.parserMovieJson(url);
				updateHandler.sendEmptyMessage(choice);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private Handler loadmoreHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {
				showList.addAll(loadmoreList);
				mAdapter.notifyDataSetChanged();

				loadmore_time = Long.parseLong(showList
						.get(showList.size() - 1).getTime()) - 1;
				System.out.println("loadmore_time = " + loadmore_time);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	};

	class LoadMoreData implements Runnable {

		@Override
		public void run() {
			int choice = 0;
			Log.d(TAG, "run()");
			try {
				// Date d = new Date();
				// time_now = d.getTime()/1000;
				String url = "http://211.155.86.159/online/info/get_message?startTime=0&endTime="
						+ loadmore_time + "&count=5";
				// String url =
				// "http://211.155.86.159:8008/info/get_message?startTime=0&endTime="+loadmore_time+"&count=20";

				loadmoreList = mMessageLoader.parserMovieJson(url);
				loadmoreHandler.sendEmptyMessage(choice);

				dbManager.addMultipleItemsOnline(MyUtils
						.MessageList2ItemInSqlList(loadmoreList));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}