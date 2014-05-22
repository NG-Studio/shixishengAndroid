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
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	// endtime
	private  long current_time = 0;
	private  long update_start_time = 0;
	private  long local_sql_last_time = 0;
	private  long loadmore_start_time = 0;
	private  long loadmore_end_time = 0;
	private int loadmore_state;
	
	//下拉刷新得到新数据的条数
	private int update_list_size;
	private int loadmore_list_size;
	
	//一次刷新的条目数
	private static int UPDATE_NUM = 20;
	private static int LOADMORE_NUM = 10;
	
	private static int LOADMORE_NORMAL = 0;
	private static int LOADMORE_UPDATE = 1;
	
	private static String user_mac = "";

	private Button iKnow;
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
		user_mac = MyUtils.getLocalMacAddress(mContext);
		dbManager = new ShixiDatabaseManager(mContext);
		
		final Activity MainActivity = this.getActivity();	
		
		
		iKnow = (Button)rootView.findViewById(R.id.btn_Iknow);
		iKnow.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG,"点击 了！");
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
			
			//guide_frame.setVisibility(View.VISIBLE);
			
		} else {
			System.out.println("不是首次使用");
			
		}

		return rootView;
	}

	private void loadDataInSql() {
		// TODO Auto-generated method stub
		loadmore_state = LOADMORE_NORMAL;
		
		showList = MyUtils.ItemListInSql2MessageList(dbManager
				.queryMultipleItemsOnline());
		mAdapter = new MessageAdapter(mContext, showList);
		mListView.setAdapter(mAdapter);
		try {

			local_sql_last_time = Long.parseLong(showList.get(0).getTime());
			mListView.setRefreshTime(TimeUtils.longToMinute(local_sql_last_time));
			//start_time = 1400483739;
			loadmore_end_time = Long.parseLong(showList
					.get(showList.size() - 1).getTime()) - 1;
			
			update_start_time = local_sql_last_time;

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
		}, 2000);

	}

	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {
				//将下拉刷新得到的list存入数据库
				dbManager.addMultipleItemsOnline(MyUtils
						.MessageList2ItemInSqlList(updateList));
				update_list_size = updateList.size();
				if(update_list_size==0){
					Toast.makeText(mContext, "没有新的信息哦^_^",
							Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(mContext, "更新了" + update_list_size + "条实习信息",
							Toast.LENGTH_LONG).show();
				}
				//接上
				if(update_list_size < UPDATE_NUM){	
					loadmore_state = LOADMORE_NORMAL;
					//从数据库中查找全部数据 得到应该显示的list
					showList = MyUtils.ItemListInSql2MessageList(dbManager
							.queryMultipleItemsOnline());

					mAdapter = new MessageAdapter(mContext, showList);
					mListView.setAdapter(mAdapter);
					//查出来的本地数据库中第一项 即是最新的一项
					local_sql_last_time = Long.parseLong(showList.get(0)
							.getTime());					
					//查出来的本地数据库中的最后一项，即是 最老的一项
					loadmore_end_time = Long.parseLong(showList.get(
							showList.size() - 1).getTime()) - 1;
					//loadmore 的 start时间 设置为0
					loadmore_start_time = 0;
				}
				//沒接上
				else{
					loadmore_state = LOADMORE_UPDATE;
					
					showList = updateList;
					mAdapter = new MessageAdapter(mContext, showList);
					mListView.setAdapter(mAdapter);

					//沒接上的情況，应该通过Loadmore 继续请求新的条目
					loadmore_end_time = Long.parseLong(showList.get(
							update_list_size - 1).getTime()) - 1;
					//start时间没有变化，还应该是之前 保存的本地最新时间，这个变量此种情况下不更新
					//loadmore_start_time = local_sql_last_time;

				}
				//下拉刷新开始时间，永远是本地数据库中最新时间
				update_start_time = Long.parseLong(dbManager
						.queryMultipleItemsOnline().get(0).getTime());
				

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
				current_time = d.getTime() / 1000;
				String url = "http://211.155.86.159/online/info/get_message?startTime="
						+ update_start_time
						+ "&endTime="
						+ current_time
						+ "&count=" + UPDATE_NUM + "&mac=" + user_mac;
				// String url =
				// "http://211.155.86.159:8008/info/get_message?startTime=0&endTime="+time_now+"&count=50";

				updateList = mMessageLoader.parserMovieJson(url);
				updateHandler.sendEmptyMessage(choice);
				
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
				//不管啥情况，都要写进本地数据库
				dbManager.addMultipleItemsOnline(MyUtils
						.MessageList2ItemInSqlList(loadmoreList));
				
				loadmore_list_size = loadmoreList.size();				
				//正常情况下，都是去读取旧的
				if(loadmore_state==LOADMORE_NORMAL){
					Toast.makeText(mContext, "载入了" + loadmore_list_size + "条数据",
							Toast.LENGTH_LONG).show();
					
					showList.addAll(loadmoreList);
					mAdapter.notifyDataSetChanged();
					
					loadmore_end_time = Long.parseLong(showList
							.get(showList.size() - 1).getTime()) - 1;
					Log.d(TAG, "loadmore_end_time = "+loadmore_end_time);
					
				}
				if(loadmore_state==LOADMORE_UPDATE){
					//小于，说明余下的是在本地，接上了，所以要从本地显示出来
					if(loadmore_list_size < LOADMORE_NUM){
						
						loadmore_start_time = 0;	
						loadmore_state = LOADMORE_NORMAL;
						showList = MyUtils.ItemListInSql2MessageList(dbManager.queryMultipleItemsOnline());
						mAdapter = new MessageAdapter(mContext, showList);
						mListView.setAdapter(mAdapter);
						
					}
					//本地的比较旧，仍有一些没有读出来
					else{								
						loadmore_end_time = Long.parseLong(showList
								.get(showList.size() - 1).getTime()) - 1;					
						showList.addAll(loadmoreList);
						mAdapter.notifyDataSetChanged();
					}					
				}

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
				if(loadmore_state==LOADMORE_NORMAL){
					loadmore_start_time=0;
				}
				if(loadmore_state==LOADMORE_UPDATE){
					loadmore_start_time = local_sql_last_time;
				}
				String url = "http://211.155.86.159/online/info/get_message?startTime="
						+ loadmore_start_time
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