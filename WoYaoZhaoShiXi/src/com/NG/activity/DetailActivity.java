package com.NG.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
import com.NG.util.ShareContentCustomizeInterface;
import com.NG.util.TimeUtils;
import com.ngstudio.zhaoshixi.R;


public class DetailActivity extends BaseActivity{

	final static String TAG = "DetailActivity";
	final static int LOADDETAIL_WRONG = 0;
	final static int LOADDETAIL_OK = 1;
	

	private TextView titleView;
	private TextView timeView;
	private TextView sourceView;
	private TextView contentView;
	private View sourceButton;

	//private Button b_collect;
	//private Button b_share;
	
	private ProgressDialog proDialog;
	
	private ShixiItem mItem;
	private ShixiItemLoader mItemLoader;
	
	private ShixiItemInSqlite item_insql;
	
	private String url;
	private static String user_mac;
	
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
		user_mac = MyUtils.getLocalMacAddress(this);

		Bundle bundle = getIntent().getExtras();
		int item_id = bundle.getInt("item_id");
		//int item_id = 0 ;
		item_insql = dbManager.querySingleItemOnline(item_id);
		
		
		url = "http://211.155.86.159/online/info/get_item?item_id=" + item_id
				+ "&mac=" + user_mac;
		initView();
		
		// loadData();

		mItemLoader = new ShixiItemLoader();
		Log.d(TAG, "getIs_collected = "+item_insql.getIs_collected());
		
		//this.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
		
		if(item_insql.getIs_collected()==1){
			
			isCollected = true;
			mItem = MyUtils.ItemInSql2Item(item_insql);
			
			showViews();			
		}
		else{
			isCollected = false;
			new Thread(new LoadData()).start();
			proDialog.show();
			
		}
		
		
		
		//contentView.setText(Html.fromHtml(html));
	
	}
	
	public void showViews(){
		titleView.setText(mItem.getTitle());
		contentView.setText(Html.fromHtml(mItem.getText_body()));
		timeView.setText(TimeUtils.stringToDay(mItem.getTime()));
		sourceView.setText(mItem.getSource());
		contentView.setAutoLinkMask(Linkify.ALL); 
		contentView.setMovementMethod(LinkMovementMethod.getInstance()); 
	}
	
	/*
	 * 最开始判断该详情是否已经收藏
	 * 
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		System.out.println("执行了onPrepareOptionsMenu");
		MenuItem item = menu.getItem(0);
		if(item_insql.getIs_collected()==1){
			item.setIcon(R.drawable.icon_collected);	
		}
		else{
			//
		}
		
		return super.onPrepareOptionsMenu(menu);
	}


	

	public void initView(){
		
		/* 显示App icon左侧的back键 */
		ActionBar actionBar = getSupportActionBar();
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
		
		
		
	}
	
	@Override  
	public boolean onCreateOptionsMenu(Menu menu) {  
	    MenuInflater inflater = getMenuInflater();  
	    inflater.inflate(R.menu.detail_menu, menu);
	    return true;  
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
		switch (item.getItemId()) {
		
		case android.R.id.home:
			finish();
			return true;

		case R.id.action_collect_btn:
			item.setIcon(R.drawable.icon_collected);
			
			Context context = getApplicationContext();
			CharSequence text ;
			int duration = Toast.LENGTH_SHORT;				
			
			if(!isCollected){
				text = "添加到我的收藏";
				ShixiItemInSqlite shixi_item = new ShixiItemInSqlite();
				
				shixi_item.setItem_id(mItem.getItem_id());
				shixi_item.setTitle(mItem.getTitle());
				shixi_item.setTime(mItem.getTime());
				shixi_item.setSource(mItem.getSource());
				shixi_item.setSource_url(mItem.getSource_url());
				shixi_item.setIs_clicked(1);
				shixi_item.setIs_collected(1);
				shixi_item.setText_body(mItem.getText_body());
				
				int status = dbManager.updateItemOnline(shixi_item);
				if(status==0){
					dbManager.addSingleItemOnline(shixi_item);
				}
				
				
				//dbManager.addSingleItemOnline(item);	
				item.setIcon(R.drawable.icon_collected);
				
				isCollected = true;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				
			}
			else{
				text = "取消收藏";
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();

				ShixiItemInSqlite shixi_item = new ShixiItemInSqlite();

				shixi_item.setItem_id(mItem.getItem_id());
				shixi_item.setTitle(mItem.getTitle());
				shixi_item.setTime(mItem.getTime());
				shixi_item.setSource(mItem.getSource());
				shixi_item.setSource_url(mItem.getSource_url());
				shixi_item.setIs_clicked(1);
				shixi_item.setIs_collected(0);
				shixi_item.setText_body(mItem.getText_body());

				dbManager.updateItemOnline(shixi_item);
				
				item.setIcon(R.drawable.icon_collect);

				isCollected = false;
				
			}
			return true;
			
		case R.id.action_share_btn:
			oneClickShare();
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
	public void oneClickShare() {
		// 实例化一个OnekeyShare对象
		OnekeyShare oks = new OnekeyShare();
		
		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher, "51zhaoshixi");
		
		// address是接收人地址，仅在信息和邮件使用		
		// 这里本质是需要用回调来把短信和邮件发送分开，可以让用户设置手机号码和邮箱地址
		// oks.setAddress("13811582143");
		
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("哇！实习");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		// oks.setTitleUrl("http://www.51zhaoshixi.com");
		oks.setTitleUrl(mItem.getSource_url());
		
		// text是分享文本，所有平台都需要这个字段
		// QQ的分享的时候以空格作为截断，因此把title中间的空格用下划线替代
		oks.setText(mItem.getTitle().replaceAll("\\s", "_"));
		
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath(Main.SHARE_IMAGE);
		// imageUrl是图片的网络路径，新浪微博、人人网、QQ空间、
		// 微信的两个平台、Linked-In支持此字段
		// oks.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/25/1374723172663.jpg");
		
		// url仅在微信（包括好友和朋友圈）中使用
		// oks.setUrl("http://www.51zhaoshixi.com");
		oks.setUrl(mItem.getSource_url());
		// appPath是待分享应用程序的本地路劲，仅在微信中使用
		// oks.setAppPath(DetailActivity.TEST_IMAGE);
		
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		// oks.setComment(getContext().getString(R.string.share));
		
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite("哇！实习");
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		// oks.setSiteUrl("http://www.51zhaoshixi.cn");
		oks.setSiteUrl(mItem.getSource_url());
		
		// venueName是分享社区名称，仅在Foursquare使用
		// oks.setVenueName("Southeast in China");
		
		// venueDescription是分享社区描述，仅在Foursquare使用
		// oks.setVenueDescription("This is a beautiful place!");
		
		// latitude是维度数据，仅在新浪微博、腾讯微博和Foursquare使用
		// oks.setLatitude(23.122619f);
		
		// longitude是经度数据，仅在新浪微博、腾讯微博和Foursquare使用
		// oks.setLongitude(113.372338f);
		
		// 是否直接分享（true则直接分享）
		oks.setSilent(false);
		
		// 指定分享平台，和slient一起使用可以直接分享到指定的平台
		// if (platform != null) {
		// oks.setPlatform(platform);
		// }
		
		// 去除注释可通过OneKeyShareCallback来捕获快捷分享的处理结果
		// oks.setCallback(new OneKeyShareCallback());
		
		// 通过OneKeyShareCallback来修改不同平台分享的内容
		oks.setShareContentCustomizeCallback(
		
		new ShareContentCustomizeInterface());
		
		oks.show(DetailActivity.this);
	}
		
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			switch(message.what){
			case LOADDETAIL_WRONG:
				Toast.makeText(getApplicationContext(), "请求错误，请检查您的网络连接",
					     Toast.LENGTH_SHORT).show();
				proDialog.dismiss();
				break;
			case LOADDETAIL_OK:
				
				try {
					showViews();
					} catch (Exception e) {
					// TODO: handle exception
					System.out.println("有些没有");
				}
				proDialog.dismiss();
				break;
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
				if(mItem.equals(null)){
					mHandler.sendEmptyMessage(LOADDETAIL_WRONG);
				}
				mHandler.sendEmptyMessage(LOADDETAIL_OK);
				
				Log.d(TAG, mItem.getTitle());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				mHandler.sendEmptyMessage(LOADDETAIL_WRONG);
				e.printStackTrace();
			}
		}

	}
	

}
