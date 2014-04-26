package com.NG.activity;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.NG.adapter.MessageAdapter;
import com.NG.entity.MessageDetail;
import com.example.drawer.R;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class MessageFragment extends Fragment implements IXListViewListener{
    public static final String ARG_PLANET_NUMBER = "planet_number";
    
    private XListView mListView;
	private MessageAdapter mAdapter;
	private ArrayList<MessageDetail> items = new ArrayList<MessageDetail>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;

    public MessageFragment() {
        // Empty constructor required for fragment subclasses
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main, container, false);

        getActivity().setTitle("ʵϰ");
        
        geneItems();
		mListView = (XListView) rootView.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mAdapter = new MessageAdapter(this.getActivity()
				.getApplicationContext(), items);
		mListView.setAdapter(mAdapter);
		mListView.setPullLoadEnable(false);
//		mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		

		
        final Activity MainActivity =this.getActivity();
        //��ӵ��   
        mListView.setOnItemClickListener(new OnItemClickListener() {   
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   
                    long arg3) { 
            	
            	System.out.println("DIANJI");
            	Intent intent = new Intent();  
                // ����Intent��Դ��ַ��Ŀ���ַ  
                intent.setClass(MainActivity, DetailActivity.class);  
                //Intent����ͨ��Bundle�������ݵĴ���  
                Bundle bundle = new Bundle();  
                bundle.putString("item_id", "00000001");  
                intent.putExtras(bundle);  
                // ����startActivity����������ͼ��ϵͳ  
                startActivity(intent);  
                //�رյ�ǰactivity������˸������û�ͨ��������ؼ����޷����ظ�activity��  
            }   
        });   
        
        
        
        return rootView;
    }
    
    private void geneItems() {
    	String[] a = { "���ٶ�У������ʵϰ����ʵϰ������Ӫ��������", 
				"���ٶ�У������ʵϰ����ʵϰ������Ӫ��������",
				"����Ƹ��NBT Partners��У԰��Ӫʵϰ��[����Ҫ����]",
				"��Ƹʵϰ��ְ�� ƽ������ ��С��ҵ������ҵ��", 
				"���п�Ժ���������ƸӲ���з�ʵϰ��",
				"ʯ��ӯ�ƣ���ʯ��IT��Ϣ����˾����Ƹ��������רҵʵϰ��", 
				"�������ա�����˿�����Ա����ʵϰ��",
				"�������ա�P2P�ͻ��˿�����Ա����ʵϰ��", 
				"�������ա�Linux�������߼�����ʦ����ʵϰ��",
				"��ʵϰ��������Զ�����Ƹ��Ŀ����ʵϰ��" 
				};
    	for(int i=0;i<10;i++){
        	MessageDetail m = new MessageDetail();
        	m.setSource("��������̳");
        	
        	
        	m.setTitle(a[i]);
        	m.setTime("2014-04-18");
        	items.add(m);
        }

	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("�ո�");
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
}