package com.NG.loader;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.NG.db.ShixiMessage;
import com.NG.util.MyUtils;

public class MyParser {
	
	public final static String TAG = "MyParser";
	
	public static List<ShixiMessage> parseShixiMessage(String s){
		
		List<ShixiMessage> messageList = new ArrayList<ShixiMessage>();
		
		JSONObject jsonObject = null;
		JSONArray messagesJson = null;

		try {
			
			jsonObject = new JSONObject(s);
			messagesJson = jsonObject.getJSONArray("data");
			messageList = MyUtils.jsonObjectToMessageList(messagesJson);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<messageList.size();i++){
			Log.d(TAG, messageList.get(i).getTitle());
		}
		return messageList;

	}
	
	
}
