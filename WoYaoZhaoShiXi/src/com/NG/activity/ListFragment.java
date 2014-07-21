package com.NG.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.NG.adapter.MessageAdapter;
import com.NG.db.ShixiDatabaseManager;
import com.NG.db.ShixiMessage;
import com.NG.loader.ShixiMessageLoader;
import com.NG.util.MyUtils;
import com.NG.util.TimeUtils;
import com.ngstudio.zhaoshixi.R;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListFragment extends Fragment implements IXListViewListener{

	private static final String TAG = "MessageFragment";

	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	// endtime
	private  long current_time = 0;	
	private  long local_sql_last_time = 0;	
	private  long loadmore_end_time = 0;	
	
	//新的数据插入数据库中的条数
	private int insert_count = 0;
	
	//一次刷新的条目数
	private static int UPDATE_NUM = 20;
	private static int LOADMORE_NUM = 10;
	
	private int loadmore_state = 1;
	
	private static int LOADMORE_FROM_LOCAL  = 0;
	private static int LOADMORE_FROM_INTERNET = 1;
	
	private static String user_mac = "";

	private XListView mListView;
	
	private FrameLayout guide_frame;
	
	// 用于显示的List
	private List<ShixiMessage> showList = new ArrayList<ShixiMessage>();
	// 用于装载下拉刷新的List
	private List<ShixiMessage> updateList = new ArrayList<ShixiMessage>();
	// 装载loadmore数据的List
	private List<ShixiMessage> loadmoreList = new ArrayList<ShixiMessage>();

	//IXList需要的Handler
	private Handler mHandler;
	
	private MessageAdapter mAdapter;
	private ShixiMessageLoader mMessageLoader;
	
	private static ShixiDatabaseManager dbManager;
	private ProgressDialog proDialog;
	private Context mContext;
	

	public ListFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_message, container,
				false);

		getActivity().setTitle("全部实习");
		mContext = this.getActivity().getApplicationContext();
		user_mac = MyUtils.getLocalMacAddress(mContext);
		dbManager = new ShixiDatabaseManager(mContext);
		
		final Activity MainActivity = this.getActivity();	
		
		guide_frame = (FrameLayout)rootView.findViewById(R.id.frame_guide);
		guide_frame.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG,"点击 了！");
				guide_frame.setVisibility(View.GONE);
			}
			
		});
		
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

		// 若为第一次使用，则自动执行一次刷新操作
		settings = mContext.getSharedPreferences("setting",
				Activity.MODE_PRIVATE);
		editor = settings.edit();
		Boolean isFisrtUpdate = settings.getBoolean("isFisrtUpdate", true);
		if (isFisrtUpdate) {
			
			System.out.println("首次使用");
			editor.putBoolean("isFisrtUpdate", false);
			editor.commit();
			new Thread(new LoadUpdateData()).start();
			onLoad();
			proDialog.show();
			
			guide_frame.setVisibility(View.VISIBLE);
			
		} else {
			System.out.println("不是首次使用");
			
		}

		return rootView;
	}

	private void loadDataInSql() {
		// TODO Auto-generated method stub
		loadmore_state = LOADMORE_FROM_LOCAL;
		
		Date d = new Date();
		current_time = d.getTime() / 1000;
		//current_time = 1400745135;
		showList = MyUtils.ItemListInSql2MessageList(dbManager
				.queryMultipleItemsByTime(current_time+"", UPDATE_NUM));
		
		mAdapter = new MessageAdapter(mContext, showList);
		mListView.setAdapter(mAdapter);
		
		try {
			//数据库中数据 最近的时间
			local_sql_last_time = Long.parseLong(showList.get(0).getTime());
			//下拉刷新 的地方显示的 最近刷新时间
			mListView.setRefreshTime(TimeUtils.longToMinute(local_sql_last_time));
			loadmore_end_time = Long.parseLong(showList.get(
					showList.size() - 1).getTime()) - 1;
			Log.d(TAG, "loadmore_end_time = " + loadmore_end_time);

		} catch (Exception e) {
			local_sql_last_time = 0;
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
		}, 1000);

	}

	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {
				//将下拉刷新得到的list存入数据库
				insert_count = dbManager.addMultipleItemsOnline(MyUtils
						.MessageList2ItemInSqlList(updateList));
				
				Toast.makeText(mContext, "更新了" + insert_count + "条实习信息",
						Toast.LENGTH_LONG).show();
				if (insert_count == UPDATE_NUM) {
					
					loadmore_state = LOADMORE_FROM_INTERNET;
					showList= updateList;
					mAdapter = new MessageAdapter(mContext, showList);
					mListView.setAdapter(mAdapter);	
					
				}
				else{
					
					loadmore_state = LOADMORE_FROM_LOCAL;
					showList = MyUtils.ItemListInSql2MessageList(dbManager
							.queryMultipleItemsByTime(current_time + "",
									UPDATE_NUM));
					mAdapter = new MessageAdapter(mContext, showList);
					mListView.setAdapter(mAdapter);	
					
				}
				
				loadmore_end_time = Long.parseLong(showList.get(
						showList.size() - 1).getTime()) - 1;
				

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	};

	class LoadUpdateData implements Runnable {

		@Override
		public void run() {
			int response = 0;
			Log.d(TAG, "LoadUpdateData()");			
			try {
				Date d = new Date();
				current_time = d.getTime() / 1000;
				//current_time = 1400745135;
				String url = "http://211.155.86.159/online/info/get_message?startTime="
						+ 0
						+ "&endTime="
						+ current_time
						+ "&count=" + UPDATE_NUM + "&mac=" + user_mac;
				// String url =
				// "http://211.155.86.159:8008/info/get_message?startTime=0&endTime="+time_now+"&count=50";

				updateList = mMessageLoader.parserMovieJson(url);
				updateHandler.sendEmptyMessage(response);
				
				proDialog.dismiss();

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
				// 正常情况下，都是去读取旧的
				if (loadmore_state == LOADMORE_FROM_LOCAL) {

					showList.addAll(loadmoreList);
					
					mAdapter.notifyDataSetChanged();
					//如果本地查找的数据 不足20条，则说明 本地没有了，需要从网络上请求
					if(loadmoreList.size() < UPDATE_NUM){
						loadmore_state = LOADMORE_FROM_INTERNET;
						loadmore_end_time = Long.parseLong(showList.get(
								showList.size() - 1).getTime()) - 1;
						Log.d(TAG, "loadmore_end_time = " + loadmore_end_time);
						return;
					}

				}
				if (loadmore_state == LOADMORE_FROM_INTERNET) {
					// 不管啥情况，都要写进本地数据库
					insert_count = dbManager.addMultipleItemsOnline(MyUtils
							.MessageList2ItemInSqlList(loadmoreList));
					Log.d(TAG, "insert_count = " + insert_count);
					
					// 小于，说明余下的是在本地，接上了，所以要从本地显示出来
					if (insert_count < LOADMORE_NUM) {
						loadmore_state = LOADMORE_FROM_LOCAL;
						showList.addAll(MyUtils
								.ItemListInSql2MessageList(dbManager
										.queryMultipleItemsByTime(
												loadmore_end_time + "",
												LOADMORE_NUM)));
						mAdapter.notifyDataSetChanged();
					}
					// 本地的比较旧，仍有一些没有读出来
					else {
						showList.addAll(loadmoreList);
						mAdapter.notifyDataSetChanged();
					}
				}
				//无论任何情况，loadmore的结束时间 都应该是当前显示列表的 最后一项的时间-1
				loadmore_end_time = Long.parseLong(showList.get(
						showList.size() - 1).getTime()) - 1;
				Log.d(TAG, "loadmore_end_time = " + loadmore_end_time);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	};

	class LoadMoreData implements Runnable {

		@Override
		public void run() {
			int choice = 0;
			Log.d(TAG, "LoadMoreData()");
			if (loadmore_state == LOADMORE_FROM_LOCAL) {
				Log.d(TAG, "LOADMORE_FROM_LOCAL");
				loadmoreList = MyUtils.ItemListInSql2MessageList(dbManager
						.queryMultipleItemsByTime(loadmore_end_time + "",
								LOADMORE_NUM));
				Log.d(TAG, "loadmoreList'size = "+loadmoreList.size());
				
				loadmoreHandler.sendEmptyMessage(choice);
			}
			else{
				Log.d(TAG, "LOADMORE_FROM_INTERNET");
				try {
					String url = "http://211.155.86.159/online/info/get_message?startTime="
							+ 0
							+ "&endTime="
							+ loadmore_end_time
							+ "&count=" + LOADMORE_NUM + "&mac=" + user_mac;
					loadmoreList = mMessageLoader.parserMovieJson(url);
					loadmoreHandler.sendEmptyMessage(choice);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			
		}

	}
	
	
	
}
