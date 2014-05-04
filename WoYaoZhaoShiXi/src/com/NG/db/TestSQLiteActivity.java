package com.NG.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ngstudio.zhaoshixi.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TestSQLiteActivity extends Activity {
	private EditText ed1;
	private EditText ed2;
	private EditText ed3;
	private ListView listView;
	private ShixiDatabaseManager dbManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_sqlite_main);
		dbManager = new ShixiDatabaseManager(this);
		ed1 = (EditText)findViewById(R.id.test_message_ed1);
		ed2 = (EditText)findViewById(R.id.test_message_ed2);
		ed3 = (EditText)findViewById(R.id.test_message_ed3);
		listView = (ListView)findViewById(R.id.listView);
	}
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();
        dbManager.closeDB();
	}
	
	public void add(View view){
		ShixiMessage message = new ShixiMessage(Integer.parseInt(ed1.getText().toString().trim()), ed2.getText().toString().trim(), ed3.getText().toString().trim(), "test", "test", "test");
		dbManager.addSingleMessage(message);
	}
	
	public void delete(View view){
		dbManager.deleteMessage(Integer.parseInt(ed1.getText().toString().trim()));
	}
	
	public void update(View view){
		ShixiMessage message = new ShixiMessage(Integer.parseInt(ed1.getText().toString().trim()), ed2.getText().toString().trim(), ed3.getText().toString().trim(), "test", "test", "test");
		dbManager.updateMessage(message);
	}
	
	public void queryOne(View view){
		ShixiMessage message = dbManager.querySingleMessage(Integer.parseInt(ed1.getText().toString().trim()));
		List<ShixiMessage> messages = new ArrayList<ShixiMessage>();
		messages.add(message);
		setListView(messages);
	}
	
	public void queryMany(View view){
		List<ShixiMessage> messages = dbManager.queryMultipleMessages();
		setListView(messages);
	}
	
	public void setListView(List<ShixiMessage> messages){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(ShixiMessage m:messages){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message_id", m.getMessage_id());
			map.put("title", m.getTitle());
			map.put("source", m.getSource());
			list.add(map);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.test_sqlite_cell, new String[]{"message_id","title","source"}, new int[]{R.id.test_message_id,R.id.test_message_title,R.id.test_message_source});
	    listView.setAdapter(adapter);
	}
}
