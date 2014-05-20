package com.NG.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.NG.db.ShixiDatabaseManager;
import com.NG.db.ShixiItem;
import com.NG.db.ShixiItemInSqlite;
import com.NG.loader.ShixiItemLoader;
import com.NG.util.MyUtils;
import com.NG.util.TimeUtils;
import com.ngstudio.zhaoshixi.R;


public class DetailActivity extends Activity{

	final static String TAG = "DetailActivity";
	

	private TextView titleView;
	private TextView timeView;
	private TextView sourceView;
	private TextView contentView;
	private View sourceButton;

	private Button b_collect;
	//private Button b_share;
	
	private ProgressDialog proDialog;
	
	private ShixiItem mItem;
	private ShixiItemLoader mItemLoader;
	
	private String url;
	
	private ShixiDatabaseManager dbManager;
	private boolean isCollected = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		// 初始化shareSDK
		ShareSDK.initSDK(this);
		
		dbManager = new ShixiDatabaseManager(this);
		

		Bundle bundle = getIntent().getExtras();
		int item_id = bundle.getInt("item_id");
		
		ShixiItemInSqlite item = dbManager.querySingleItemOnline(item_id);
		
		
		url = "http://211.155.86.159/online/info/get_item?item_id=" + item_id;
		initView();
		
		// loadData();

		mItemLoader = new ShixiItemLoader();
		Log.d(TAG, "getIs_collected = "+item.getIs_collected());
		
		if(item.getIs_collected()==1){
			
			isCollected = true;
			b_collect.setTextColor(Color.BLUE);
			b_collect.setText("已收藏");
			mItem = MyUtils.ItemInSql2Item(item);
			titleView.setText(mItem.getTitle());
			contentView.setText(Html.fromHtml(mItem.getText_body()));
			timeView.setText(TimeUtils.stringToDay(mItem.getTime()));
			sourceView.setText(mItem.getSource());
		}
		else{
			isCollected = false;
			new Thread(new LoadData()).start();
			proDialog.show();
			
		}
		
		
		
		//contentView.setText(Html.fromHtml(html));
	
	}
	
	

	public void initView(){
		
		/* 显示App icon左侧的back键 */
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		titleView = (TextView) findViewById(R.id.item_title);
		timeView = (TextView) findViewById(R.id.item_time);
		sourceView  = (TextView) findViewById(R.id.item_source);
		contentView = (TextView) findViewById(R.id.item_content);
		
		sourceButton = (View)findViewById(R.id.btn_source);
		
		sourceButton.setOnClickListener(new OnClickListener(){

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
		
		
		b_collect = (Button)findViewById(R.id.button_collect);
		b_collect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Context context = getApplicationContext();
				CharSequence text ;
				int duration = Toast.LENGTH_SHORT;				
				
				if(!isCollected){
					text = "添加到我的收藏";
					ShixiItemInSqlite item = new ShixiItemInSqlite();
					
					item.setItem_id(mItem.getItem_id());
					item.setTitle(mItem.getTitle());
					item.setTime(mItem.getTime());
					item.setSource(mItem.getSource());
					item.setSource_url(mItem.getSource_url());
					item.setIs_clicked(1);
					item.setIs_collected(1);
					item.setText_body(mItem.getText_body());
					
					dbManager.updateItemOnline(item);
					
					//dbManager.addSingleItem(mItem);			
					b_collect.setTextColor(Color.BLUE);
					b_collect.setText("已收藏");
					isCollected = true;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					
				}
				else{
					text = "取消收藏";
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();

					ShixiItemInSqlite item = new ShixiItemInSqlite();

					item.setItem_id(mItem.getItem_id());
					item.setTitle(mItem.getTitle());
					item.setTime(mItem.getTime());
					item.setSource(mItem.getSource());
					item.setSource_url(mItem.getSource_url());
					item.setIs_clicked(1);
					item.setIs_collected(0);
					item.setText_body(mItem.getText_body());

					dbManager.updateItemOnline(item);

					b_collect.setTextColor(Color.BLACK);
					b_collect.setText("收藏");
					isCollected = false;
				}
				
				
				
			}
		});
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbManager.closeDB();
	}

	// 一键分享的点击事件
	public void oneClickShare(View v) {

	}
		
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {
				titleView.setText(mItem.getTitle());
				contentView.setText(Html.fromHtml(mItem.getText_body()));
				timeView.setText(TimeUtils.stringToDay(mItem.getTime()));
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
