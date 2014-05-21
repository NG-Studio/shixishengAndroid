package com.NG.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.NG.adapter.MessageAdapter;
import com.NG.db.ShixiMessage;
import com.NG.loader.ShixiMessageLoader;
import com.NG.util.MyUtils;
import com.ngstudio.zhaoshixi.R;

public class SearchActivity extends Activity implements IXListViewListener {
	private static final String TAG = "SearchActivity";

	private static long time_now = 0;
	private static long loadmore_time = 0;

	private XListView mListView;
	private MessageAdapter mAdapter;
	private List<ShixiMessage> mdList = new ArrayList<ShixiMessage>();
	private List<ShixiMessage> newmdList = new ArrayList<ShixiMessage>();
	private Handler mHandler;

	private ShixiMessageLoader mMessageLoader;

	private ProgressDialog proDialog;
	private Context mContext;
	
	private static String user_mac;

	private String name;
	private ImageView search_button;// 搜索按钮
	public EditText editText;// 搜索框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_search);
		// getActionBar().setTitle("实习搜索");

		mContext = this;
		user_mac = MyUtils.getLocalMacAddress(this);

		// geneItems();
		mListView = (XListView) findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mListView.setDividerHeight(0);

		// mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();

		search_button = (ImageView) findViewById(R.id.search_button);

		search_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				searchData();

				// mHandler.sendEmptyMessage(1);
			}
		});

		editText = (EditText) findViewById(R.id.edittext);

		// ProgressDialog
		proDialog = new ProgressDialog(this);
		proDialog.setTitle(R.string.loading);
		proDialog.setMessage("请耐心等待...");

		mMessageLoader = new ShixiMessageLoader();

		// new Thread(new LoadData()).start();
		// proDialog.show();

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				System.out.println("DIANJI");
				Intent intent = new Intent();
				intent.setClass(SearchActivity.this, DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("item_id", mdList.get(arg2 - 1).getMessage_id());
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

	}

	public void searchData() {
		proDialog.show();
		name = editText.getText().toString();
		if (name.trim().length() < 1) {
			openOptionDialog("搜索条件");
			proDialog.dismiss();
			return;

		} else {
			new Thread(new LoadData()).start();
		}
	}

	// 提示框方法
	public void openOptionDialog(String string) {
		new AlertDialog.Builder(this).setTitle("提示")
				.setMessage(string + "不能为空!")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}

				}).show();
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

				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {

		new Thread(new LoadMoreData()).start();
		onLoad();

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {

				mAdapter = new MessageAdapter(mContext, mdList);
				mListView.setAdapter(mAdapter);
				proDialog.dismiss();

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
				String ch = URLEncoder.encode(name, "utf-8");
				String url = "http://211.155.86.159/online/info/search?startTime=0&endTime="
						+ time_now
						+ "&count=50&query="
						+ ch
						+ "&mac="
						+ user_mac;
				System.out.println(url);

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
				String ch = URLEncoder.encode(name, "utf-8");
				String url = "http://211.155.86.159/online/info/search?startTime=0&endTime="
						+ loadmore_time
						+ "&count=20&query="
						+ ch
						+ "&mac="
						+ user_mac;
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
