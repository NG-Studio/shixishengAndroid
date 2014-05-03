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
import com.ngstudio.zhaoshixi.R;

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

        getActivity().setTitle("ʵϰ");
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
		proDialog.setMessage("请耐心等待...");

		mMessageLoader = new MessageInfoLoader();

		new Thread(new LoadData()).start();
		proDialog.show();
		
        final Activity MainActivity =this.getActivity();
        mListView.setOnItemClickListener(new OnItemClickListener() {   
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   
                    long arg3) { 
            	
            	System.out.println("DIANJI");
            	Intent intent = new Intent();  
                intent.setClass(MainActivity, DetailActivity.class);   
                Bundle bundle = new Bundle();  
                bundle.putInt("item_id", mdList.get(arg2-1).getUid());  
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
			Log.d(TAG, "run()");
			try {
				
				String url = "http://51zhaoshixi.com:8008/info/get_message?cmd=%E5%AE%9E%E4%B9%A0%EF%BF%A50";
				mdList = mMessageLoader.parserMovieJson(url);
				handler.sendEmptyMessage(choice);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}