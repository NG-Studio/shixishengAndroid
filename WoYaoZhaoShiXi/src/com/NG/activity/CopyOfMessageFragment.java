package com.NG.activity;

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

import com.NG.adapter.MessageAdapter;
import com.NG.entity.MessageDetail;
import com.ngstudio.zhaoshixi.R;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class CopyOfMessageFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public CopyOfMessageFragment() {
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
        
        //��Layout�����ListView   
        ListView list = (ListView) rootView.findViewById(R.id.ListView01);   
        /*
        //��ɶ�̬���飬�������   
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();   
        for(int i=0;i<10;i++)   
        {   
            HashMap<String, Object> map = new HashMap<String, Object>();   
            map.put("ItemImage", R.drawable.ic_launcher);//ͼ����Դ��ID   
            map.put("ItemTitle", "Level "+i);   
            map.put("ItemText", "Finished in 1 Min 54 Secs, 70 Moves! ");   
            listItem.add(map);   
        }   
        
		// �����������Item�Ͷ�̬�����Ӧ��Ԫ��
		SimpleAdapter listItemAdapter = new SimpleAdapter(
				this.getActivity(), listItem,// ���Դ
				R.layout.list_items,// ListItem��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "ItemImage", "ItemTitle", "ItemText" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.ItemImage, R.id.ItemTitle, R.id.ItemText });*/

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

		MessageAdapter listItemAdapter = new MessageAdapter(this
				.getActivity().getApplicationContext(), listItem);

        //��Ӳ�����ʾ   
        list.setAdapter(listItemAdapter);   
        final Activity MainActivity =this.getActivity();
        //��ӵ��   
        list.setOnItemClickListener(new OnItemClickListener() {   
  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   
                    long arg3) { 
            	
            	System.out.println("DIANJI");
            	Intent intent = new Intent();  
                // ����Intent��Դ��ַ��Ŀ���ַ  
                intent.setClass(MainActivity, DetailActivity.class);  
                //Intent����ͨ��Bundle������ݵĴ���  
                Bundle bundle = new Bundle();  
                bundle.putString("item_id", "00000001");  
                intent.putExtras(bundle);  
                // ����startActivity����������ͼ��ϵͳ  
                startActivity(intent);  
                //�رյ�ǰactivity������˸������û�ͨ�������ؼ����޷����ظ�activity��  
            }   
        });   
        
        
        
        return rootView;
    }
}