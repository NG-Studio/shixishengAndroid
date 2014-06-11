package com.NG.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.NG.db.ShixiItem;
import com.NG.db.ShixiItemInSqlite;
import com.NG.db.ShixiMessage;

public class MyUtils {
	
	/** 
     * 获取mac地址 
     * @param context 
     * @return 
     */  
    public static String getLocalMacAddress(Context context) {  
        WifiManager wifi = (WifiManager) context.getSystemService(  
                Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    }
	
	/**
	 * 从数据库中读出的item 转成显示的 message
	 * 
	 * */
	public static ShixiMessage ItemInSql2Message(ShixiItemInSqlite item) {

		ShixiMessage sm = new ShixiMessage();
		sm.setMessage_id(item.getItem_id());
		sm.setSource(item.getSource());
		sm.setSource_url(item.getSource_url());
		sm.setTime(item.getTime());
		sm.setTitle(item.getTitle());

		return sm;
	}
	
	/**
	 * 从数据库中读出的itemList 转成显示的 messageList
	 * 
	 * */
	public static List<ShixiMessage> ItemListInSql2MessageList(
			List<ShixiItemInSqlite> iList) {

		List<ShixiMessage> smList = new ArrayList<ShixiMessage>();
		for (ShixiItemInSqlite item : iList) {
			ShixiMessage sm = ItemInSql2Message(item);
			smList.add(sm);
		}

		return smList;

	}
	
	/**
	 * 下载的message 转成 数据中中存储的类
	 * 
	 * */
	public static ShixiItemInSqlite Message2ItemInSql(ShixiMessage m){
		
		ShixiItemInSqlite item = new ShixiItemInSqlite();
		
		item.setItem_id(m.getMessage_id());
		item.setTitle(m.getTitle());
		item.setTime(m.getTime());
		item.setSource(m.getSource());
		item.setSource_url(m.getSource_url());
		item.setIs_clicked(0);
		item.setIs_collected(0);
		
		
		return item;
	}
	
	/**
	 * 下载的message list 转成 数据中中存储的类
	 * 
	 * */
	public static List<ShixiItemInSqlite> MessageList2ItemInSqlList(List<ShixiMessage> mList){
		
		List<ShixiItemInSqlite> isqlList = new ArrayList<ShixiItemInSqlite>();
		for (ShixiMessage item : mList) {
			ShixiItemInSqlite sm = Message2ItemInSql(item);
			isqlList.add(sm);
		}
		
		return isqlList;
	}
	
	
	/**
	 * 从数据库中读出的item 转成显示的 shixiItem
	 * 
	 * */
	public static ShixiItem ItemInSql2Item(ShixiItemInSqlite item) {

		ShixiItem si = new ShixiItem();
		si.setItem_id(item.getItem_id());
		si.setSource(item.getSource());
		si.setSource_url(item.getSource_url());
		si.setTime(item.getTime());
		si.setTitle(item.getTitle());
		si.setText_body(item.getText_body());
		if(item.getIs_collected()==0){
			si.setIs_collected(false);
		}
		else{
			si.setIs_collected(true);
		}

		return si;
	}
	
	
	
	/**
	 * 解析json串，变成ShixiMessage的方法
	 * 
	 * */
	public static ShixiMessage jsonObjectToShixiMessage(JSONObject j) {
		ShixiMessage message = new ShixiMessage();
		try {

			message.setTitle(j.getString("item_title"));
			message.setMessage_id(j.getInt("item_id"));
			message.setSource(j.getString("item_source"));
			message.setSource_url(j.getString("item_url"));
			message.setTime(j.getString("publish_time"));

			// String time;
			// long time_long = Long.parseLong(j.getString("publish_time"));
			// time = TimeUtils.stringToSecond(time_long);
			// message.setTime(time_long);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
	
	/**
	 * json数组解析成shixiMessage 的list
	 * 
	 * */
	public static List<ShixiMessage> jsonObjectToMessageList(JSONArray ja) {

		int length = ja.length();
		System.out.println("Json Array's length is :" + length);

		List<ShixiMessage> list = new ArrayList<ShixiMessage>();
		for (int i = 0; i < length; i++) {
			try {
				ShixiMessage md = new ShixiMessage();

				md = jsonObjectToShixiMessage(ja.getJSONObject(i));

				list.add(md);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;

	}
	
	
	
}
