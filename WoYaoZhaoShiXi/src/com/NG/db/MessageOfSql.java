package com.NG.db;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;

import com.NG.activity.DetailActivity;
import com.NG.activity.SearchActivity;
import com.NG.adapter.MessageAdapter;
import com.NG.loader.ShixiMessageLoader;
import com.ngstudio.zhaoshixi.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MessageOfSql extends Activity {

	private MessageAdapter mAdapter;
	private List<ShixiMessage> mdList = new ArrayList<ShixiMessage>();
	private ShixiMessageLoader mMessageLoader;
	private ProgressDialog proDialog;
	private Context mContext;
	private ListView mListView;

	private ShixiDatabaseManager dbManager;

	private static long time_now = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_sql);
		mContext = this;
		dbManager = new ShixiDatabaseManager(this);

		mListView = (ListView) findViewById(R.id.sql_list);

		// ProgressDialog
		proDialog = new ProgressDialog(this);
		proDialog.setTitle(R.string.loading);
		proDialog.setMessage("请耐心等待...");

		mMessageLoader = new ShixiMessageLoader();

		new Thread(new LoadData()).start();
		proDialog.show();

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				System.out.println("单击了第" + arg2 + "个item");

			}
		});

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {

				mAdapter = new MessageAdapter(mContext, mdList);
				mListView.setAdapter(mAdapter);
				proDialog.dismiss();

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	};

	class LoadData implements Runnable {

		@Override
		public void run() {
			int choice = 0;
			try {
				Date d = new Date();
				time_now = d.getTime() / 1000;
				String url = "http://211.155.86.159/online/info/get_message?startTime=0&endTime="
						+ time_now + "&count=50";

				System.out.println(url);

				mdList = mMessageLoader.parserMovieJson(url);
				handler.sendEmptyMessage(choice);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbManager.closeDB();
	}

	public void add(View view) {
		// ShixiMessage message = new
		// ShixiMessage(Integer.parseInt(ed1.getText().toString().trim()),
		// ed2.getText().toString().trim(), ed3.getText().toString().trim(),
		// "test", "test", "test");
		// dbManager.addSingleMessage(message);
		Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT)
				.show();
		ShixiMessage m = new ShixiMessage();
		try {
			m.setMessage_id(mdList.get(0).getMessage_id());
			m.setSource(mdList.get(0).getSource());
			m.setSource_url(mdList.get(0).getSource_url());
			m.setTime(mdList.get(0).getTime() + "");
			m.setTitle(mdList.get(0).getTitle());
			m.setIs_clicked("0");

			dbManager.addSingleMessage(m);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(View view) {
		// dbManager.deleteMessage(Integer.parseInt(ed1.getText().toString().trim()));
		Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT)
				.show();
	}

	public void update(View view) {
		// ShixiMessage message = new
		// ShixiMessage(Integer.parseInt(ed1.getText().toString().trim()),
		// ed2.getText().toString().trim(), ed3.getText().toString().trim(),
		// "test", "test", "test");
		// dbManager.updateMessage(message);
		Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_SHORT)
				.show();
		ShixiMessage message = new ShixiMessage(0, "test", "test", "test",
				"test", "test");
		dbManager.updateMessage(message);

	}

	public void queryOne(View view) {
		// ShixiMessage message =
		// dbManager.querySingleMessage(Integer.parseInt(ed1.getText().toString().trim()));
		// List<ShixiMessage> messages = new ArrayList<ShixiMessage>();
		// messages.add(message);
		// setListView(messages);
		Toast.makeText(getApplicationContext(), "queryOne", Toast.LENGTH_SHORT)
				.show();

	}

	public void queryMany(View view) {
		// List<ShixiMessage> messages = dbManager.queryMultipleMessages();
		// setListView(messages);
		Toast.makeText(getApplicationContext(), "queryMany", Toast.LENGTH_SHORT)
				.show();

		List<ShixiMessage> messages = dbManager.queryMultipleMessages();
		// MessageDetail md = new MessageDetail();
		// List<MessageDetail> list = new ArrayList<MessageDetail>();
		//
		// for(int i=0;i<messages.size();i++){
		// ShixiMessage sm = messages.get(i);
		// md.setUid(sm.getMessage_id());
		// md.setTime(time_now);
		// md.setTitle(sm.getTitle());
		// md.setSource_url(sm.getSource_url());
		// md.setSource(sm.getSource());
		//
		// list.add(md);
		// }
		// MessageAdapter adapter = new MessageAdapter(mContext, list);
		//
		// mListView.setAdapter(adapter);
	}

}
