package com.NG.activity;

import java.util.ArrayList;
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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.NG.activity.DetailActivity.LoadData;
import com.NG.adapter.MessageAdapter;
import com.NG.entity.MessageDetail;
import com.NG.loader.MessageInfoLoader;
import com.example.drawer.R;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class MessageFragment extends Fragment implements IXListViewListener{
    public static final String ARG_PLANET_NUMBER = "planet_number";
    private static final String TAG = "MessageFragment";
    
    private XListView mListView;
	private MessageAdapter mAdapter;
	private List<MessageDetail> mdList = new ArrayList<MessageDetail>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	
	private MessageInfoLoader mMessageLoader;
	
	private ProgressDialog proDialog;
	private Context mContext;

    public MessageFragment() {
        // Empty constructor required for fragment subclasses
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main, container, false);

        getActivity().setTitle("实习");
        mContext = this.getActivity().getApplicationContext();
        
        //geneItems();
		mListView = (XListView) rootView.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		
		mListView.setPullLoadEnable(false);
//		mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		
		
		// ProgressDialog
		proDialog = new ProgressDialog(this.getActivity());
		proDialog.setTitle(R.string.loading);
		proDialog.setMessage("请您耐心等待...");

		mMessageLoader = new MessageInfoLoader();

		new Thread(new LoadData()).start();
		proDialog.show();
		
        final Activity MainActivity =this.getActivity();
        //添加点击   
        mListView.setOnItemClickListener(new OnItemClickListener() {   
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   
                    long arg3) { 
            	
            	System.out.println("DIANJI");
            	Intent intent = new Intent();  
                // 设置Intent的源地址和目标地址  
                intent.setClass(MainActivity, DetailActivity.class);  
                //Intent可以通过Bundle进行数据的传递  
                Bundle bundle = new Bundle();  
                bundle.putInt("item_id", mdList.get(arg2).getUid());  
                intent.putExtras(bundle);  
                // 调用startActivity方法发送意图给系统  
                startActivity(intent);  
                //关闭当前activity，添加了该语句后，用户通过点击返回键是无法返回该activity的  
            }   
        }); 
        
        
        
        
        
        
        return rootView;
    }
    
    private void geneItems() {
    	String[] a = { "【百度校招暑期实习生】实习技术运营（北京）", 
				"【百度校招暑期实习生】实习技术运营（北京）",
				"【招聘】NBT Partners招校园运营实习生[不需要坐班]",
				"招聘实习兼职生 平安银行 中小企业金融事业部", 
				"【中科院软件所】招聘硬件研发实习生",
				"石化盈科（中石化IT信息化公司）招聘计算机相关专业实习生", 
				"【爱奇艺】服务端开发人员（可实习）",
				"【爱奇艺】P2P客户端开发人员（可实习）", 
				"【爱奇艺】Linux服务器高级工程师（可实习）",
				"【实习】北京致远软件招聘项目助理实习生" 
				};
    	for(int i=0;i<10;i++){
        	MessageDetail m = new MessageDetail();
        	m.setSource("北邮人论坛");   	 	
        	m.setTitle(a[i]);
        	m.setTime("2014-04-18");
        	mdList.add(m);
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

				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			try {
				
				mAdapter = new MessageAdapter(mContext, mdList);
				mListView.setAdapter(mAdapter);
				Log.d(TAG, "333"+mAdapter.getCount());
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
				
				String url = "http://51zhaoshixi.com:8008/info/get_message?cmd=%E5%AE%9E%E4%B9%A0%EF%BF%A5200";
				mdList = mMessageLoader.parserMovieJson(url);
				handler.sendEmptyMessage(choice);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}