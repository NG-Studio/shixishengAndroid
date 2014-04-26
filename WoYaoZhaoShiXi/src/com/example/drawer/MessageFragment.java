package com.example.drawer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.entity.Message;
import com.jy.adapter.MessageAdapter;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class MessageFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public MessageFragment() {
        // Empty constructor required for fragment subclasses
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
        /*
        

        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                        "drawable", getActivity().getPackageName());
        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
        getActivity().setTitle(planet);*/
        int x = getArguments().getInt(ARG_PLANET_NUMBER);
        String planet = getResources().getStringArray(R.array.planets_array)[x];
        getActivity().setTitle(planet);
        
        //绑定Layout里面的ListView   
        ListView list = (ListView) rootView.findViewById(R.id.ListView01);   
        /*
        //生成动态数组，加入数据   
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();   
        for(int i=0;i<10;i++)   
        {   
            HashMap<String, Object> map = new HashMap<String, Object>();   
            map.put("ItemImage", R.drawable.ic_launcher);//图像资源的ID   
            map.put("ItemTitle", "Level "+i);   
            map.put("ItemText", "Finished in 1 Min 54 Secs, 70 Moves! ");   
            listItem.add(map);   
        }   
        
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter listItemAdapter = new SimpleAdapter(
				this.getActivity(), listItem,// 数据源
				R.layout.list_items,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemTitle", "ItemText" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemImage, R.id.ItemTitle, R.id.ItemText });*/

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

        List<Message> listItem = new ArrayList<Message>();
        for(int i=0;i<10;i++){
        	Message m = new Message();
        	m.setSource("北邮人论坛");
        	
        	
        	m.setTitle(a[i]);
        	m.setTime("2014-04-18");
        	listItem.add(m);
        }

		MessageAdapter listItemAdapter = new MessageAdapter(this
				.getActivity().getApplicationContext(), listItem);

        //添加并且显示   
        list.setAdapter(listItemAdapter);   
        final Activity MainActivity =this.getActivity();
        //添加点击   
        list.setOnItemClickListener(new OnItemClickListener() {   
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   
                    long arg3) { 
            	
            	System.out.println("DIANJI");
            	Intent intent = new Intent();  
                // 设置Intent的源地址和目标地址  
                intent.setClass(MainActivity, DetailActivity.class);  
                //Intent可以通过Bundle进行数据的传递  
                Bundle bundle = new Bundle();  
                bundle.putString("item_id", "00000001");  
                intent.putExtras(bundle);  
                // 调用startActivity方法发送意图给系统  
                startActivity(intent);  
                //关闭当前activity，添加了该语句后，用户通过点击返回键是无法返回该activity的  
            }   
        });   
        
        
        
        return rootView;
    }
}