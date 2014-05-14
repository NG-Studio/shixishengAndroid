package com.NG.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import android.util.Log;

import com.NG.entity.ItemDetail;
import com.NG.entity.MessageDetail;
import com.NG.util.TimeUtils;

public class MessageInfoLoader {
	private static final String TAG = "MessageInfoLoader";
	
	private List<MessageDetail> messageList = new ArrayList<MessageDetail>();
	
	public MessageInfoLoader() {
		Log.d(TAG, "constractor()  do thing");
	}

	public List<MessageDetail> parserMovieJson(String singleUrl) throws IOException,
			ParserConfigurationException, SAXException {

		URL url = new URL(singleUrl);
		Log.d(TAG, singleUrl);

		//
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(url.openStream()));
		for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
				.readLine()) {
			stringBuilder.append(s);
		}
		
		Log.d(TAG, stringBuilder.toString());
		
		JSONObject jsonObject = null;
		JSONArray messagesJson = null;

		try {
			
			jsonObject = new JSONObject(stringBuilder.toString());
			
			messagesJson = jsonObject.getJSONArray("data");
			
			messageList = jsonObjectToMessageList(messagesJson);
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<messageList.size();i++){
			Log.d(TAG, messageList.get(i).getTitle());
		}
		return messageList;

	}
	
	public MessageDetail jsonObjectToAwardItem(JSONObject j){
		MessageDetail message = new MessageDetail();
		try {
			
			message.setTitle(j.getString("item_title"));
			message.setUid(j.getInt("item_id"));
			message.setSource(j.getString("item_source"));
			message.setSource_url(j.getString("item_url"));
			
			//String time;
			long time_long = Long.parseLong(j.getString("publish_time"));
			//time = TimeUtils.stringToSecond(time_long);
			message.setTime(time_long);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
	public List<MessageDetail> jsonObjectToMessageList(JSONArray ja){
		
		int length = ja.length();
		System.out.println("Json Array's length is :" +length );
		
		List<MessageDetail> list = new ArrayList<MessageDetail>();
		for(int i=0;i<length;i++){
			try {
				MessageDetail md = new MessageDetail();
				
				md = jsonObjectToAwardItem(ja.getJSONObject(i));
				
				list.add(md);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return list;
		
		
	}
	
	
}
