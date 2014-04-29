package com.NG.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.NG.adapter.MessageAdapter;
import com.NG.entity.MessageDetail;
import com.ngstudio.zhaoshixi.R;

public class ItemFragment {
	
	public ItemFragment() {
        // Empty constructor required for fragment subclasses
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_detail, container, false);
        
        
        getActivity().setTitle("����");
        
        //��Layout�����ListView   
        ListView list = (ListView) rootView.findViewById(R.id.ListView01);   

		String[] a = { "���ٶ�У������ʵϰ��ʵϰ������Ӫ��������", 
				"���ٶ�У������ʵϰ��ʵϰ������Ӫ��������",
				"����Ƹ��NBT Partners��У԰��Ӫʵϰ��[����Ҫ���]",
				"��Ƹʵϰ��ְ�� ƽ������ ��С��ҵ������ҵ��", 
				"���п�Ժ�������ƸӲ���з�ʵϰ��",
				"ʯ��ӯ�ƣ���ʯ��IT��Ϣ����˾����Ƹ��������רҵʵϰ��", 
				"�������ա�����˿�����Ա����ʵϰ��",
				"�������ա�P2P�ͻ��˿�����Ա����ʵϰ��", 
				"�������ա�Linux�������߼�����ʦ����ʵϰ��",
				"��ʵϰ��������Զ�����Ƹ��Ŀ����ʵϰ��" 
				};

        List<MessageDetail> listItem = new ArrayList<MessageDetail>();
        for(int i=0;i<10;i++){
        	MessageDetail m = new MessageDetail();
        	m.setSource("��������̳");
        	
        	
        	m.setTitle(a[i]);
        	m.setTime("2014-04-18");
        	listItem.add(m);
        }
        
        
        
        return rootView;
    }

	private Activity getActivity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
