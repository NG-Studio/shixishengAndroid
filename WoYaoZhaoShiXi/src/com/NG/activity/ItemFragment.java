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
import com.example.drawer.R;

public class ItemFragment {
	
	public ItemFragment() {
        // Empty constructor required for fragment subclasses
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_detail, container, false);
        
        
        getActivity().setTitle("详情");
        
        //绑定Layout里面的ListView   
        ListView list = (ListView) rootView.findViewById(R.id.ListView01);   

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

        List<MessageDetail> listItem = new ArrayList<MessageDetail>();
        for(int i=0;i<10;i++){
        	MessageDetail m = new MessageDetail();
        	m.setSource("北邮人论坛");
        	
        	
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
