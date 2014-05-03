package com.NG.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.onekeyshare.OnekeyShare;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.NG.entity.ItemDetail;
import com.NG.entity.MessageDetail;
import com.NG.loader.DetailInfoLoader;
import com.NG.loader.MessageInfoLoader;
import com.ngstudio.zhaoshixi.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler.Callback;
import android.os.Handler;

import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DetailActivity extends Activity implements PlatformActionListener, Callback{

	final static String TAG = "DetailActivity";
	

	private TextView titleView;
	private TextView timeView;
	private TextView sourceView;
	private TextView contentView;
	

	private Button b_collect;
	//private Button b_share;
	
	private ProgressDialog proDialog;
	
	private ItemDetail mItem;
	private DetailInfoLoader mItemLoader;
	
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		// 初始化shareSDK
		ShareSDK.initSDK(this);
		
		
		b_collect = (Button)findViewById(R.id.button_collect);
		b_collect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Context context = getApplicationContext();
				CharSequence text = "Hello Collect!";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		});

		Bundle bundle = getIntent().getExtras();
		int item_id = bundle.getInt("item_id");
		url = "http://51zhaoshixi.com:8008/info/get_item?item_id=" + item_id;
		initView();
		
		//loadData();
		
		mItemLoader = new DetailInfoLoader();
		
		new Thread(new LoadData()).start();
		proDialog.show();
		
		//contentView.setText(Html.fromHtml(html));
	
	}
	
	

	public void initView(){
		
		titleView = (TextView) findViewById(R.id.item_title);
		timeView = (TextView) findViewById(R.id.item_time);
		sourceView  = (TextView) findViewById(R.id.item_source);
		contentView = (TextView) findViewById(R.id.item_content);
		
		titleView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();  
                // 设置Intent的源地址和目标地址  
                intent.setClass(DetailActivity.this, webActivity.class);  
                //Intent可以通过Bundle进行数据的传递  
                Bundle bundle = new Bundle();  
                bundle.putString("item_url", mItem.getSource_url());  
                intent.putExtras(bundle);  
                // 调用startActivity方法发送意图给系统  
                startActivity(intent);
			}
			
		});

		// ProgressDialog
		proDialog = new ProgressDialog(this);
		proDialog.setTitle(R.string.loading);
		proDialog.setMessage("请您耐心等待...");
	}
	
	private void loadData() {
	
		
	}

	private static final int MSG_TOAST = 1;
	private static final int MSG_ACTION_CCALLBACK = 2;
	private static final int MSG_CANCEL_NOTIFY = 3;
	
	// sdcard中的图片名称，分享图片需要
	//private static final String FILE_NAME = "/zhaoshixi.png";
	//public static String TEST_IMAGE;

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MSG_TOAST: {
			String text = String.valueOf(msg.obj);
			Toast.makeText(DetailActivity.this, text, Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_ACTION_CCALLBACK: {
			switch (msg.arg1) {
			case 1: // 成功后发送Notification
				showNotification(2000, "分享完成");
				break;
			case 2: // 失败后发送Notification
				showNotification(2000, "分享失败");
				break;
			case 3: // 取消
				showNotification(2000, "取消分享");
				break;
			}
		}
			break;
		case MSG_CANCEL_NOTIFY:
			NotificationManager nm = (NotificationManager) msg.obj;
			if (nm != null) {
				nm.cancel(msg.arg1);
			}
			break;
		}
		return false;
	}

	// 一键分享的点击事件
	public void oneClickShare(View v) {
		//实例化一个OnekeyShare对象
		OnekeyShare oks = new OnekeyShare();
		
		//设置Notification的显示图标和显示文字
		oks.setNotification(R.drawable.ic_launcher, "51zhaoshixi");
		
		//设置短信地址或者是邮箱地址，如果没有可以不设置
		oks.setAddress("13811582143");
		
		//分享内容的标题
		oks.setTitle("分享内容的标题");
		
		//标题对应的网址，如果没有可以不设置
		oks.setTitleUrl("http://www.51zhaoshixi.com");
		
		//设置分享的文本内容
		oks.setText("51zhaoshixi - NG Studio");
		
		//设置分享照片的本地路径，如果没有可以不设置
		//oks.setImagePath(DetailActivity.TEST_IMAGE);
		
		//设置分享照片的url地址，如果没有可以不设置
		//oks.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/25/1374723172663.jpg");
		
		//微信和易信的分享的网络连接，如果没有可以不设置
		oks.setUrl("http://sharesdk.cn");
		
		//人人平台特有的评论字段，如果没有可以不设置
		oks.setComment("comment");
		
		//程序的名称或者是站点名称
		oks.setSite("51zhaoshixi");
		
		//程序的名称或者是站点名称的链接地址
		oks.setSiteUrl("http://www.51zhaoshixi.com");
		
		//设置纬度
		oks.setLatitude(23.122619f);
		//设置精度
		oks.setLongitude(113.372338f);
		
		//设置是否是直接分享
		oks.setSilent(false);
		
		//显示
		oks.show(DetailActivity.this);
	}

		
	@Override
	public void onCancel(Platform platform, int action) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);

	}



	@Override
	public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);

	}



	@Override
	public void onError(Platform arg0, int action, Throwable t) {
		// TODO Auto-generated method stub
		t.printStackTrace();
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = t;
		UIHandler.sendMessage(msg, this);
	}
	
	// 根据传入的参数显示一个Notification
	@SuppressWarnings("deprecation")
	private void showNotification(long cancelTime, String text) {
		try {
			Context app = getApplicationContext();
			NotificationManager nm = (NotificationManager) app
					.getSystemService(Context.NOTIFICATION_SERVICE);
			final int id = Integer.MAX_VALUE / 13 + 1;
			nm.cancel(id);
			long when = System.currentTimeMillis();
			Notification notification = new Notification(
					R.drawable.ic_launcher, text, when);
			PendingIntent pi = PendingIntent.getActivity(app, 0, new Intent(),
					0);
			notification.setLatestEventInfo(app, "sharesdk test", text, pi);
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			nm.notify(id, notification);

			if (cancelTime > 0) {
				Message msg = new Message();
				msg.what = MSG_CANCEL_NOTIFY;
				msg.obj = nm;
				msg.arg1 = id;
				UIHandler.sendMessageDelayed(msg, cancelTime, this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {
				titleView.setText(mItem.getTitle());
				contentView.setText(Html.fromHtml(mItem.getText()));
				timeView.setText(mItem.getTime());
				sourceView.setText(mItem.getSource());
				proDialog.dismiss();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("有些没有");
			}
		}
	};
	class LoadData implements Runnable {

		@Override
		public void run() {
			int choice = 0;
			Log.d(TAG, "run()");
			try {
				
				//String url = "http://51zhaoshixi.com:8008/info/get_item?item_id=380";
				mItem = mItemLoader.parserMovieJson(url);
				//mdlist = mMessageLoader.parserMovieJson(url);
				mHandler.sendEmptyMessage(choice);
				
				Log.d(TAG, mItem.getTitle());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	

}
