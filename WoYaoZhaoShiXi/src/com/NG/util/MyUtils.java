package com.NG.util;

import java.util.ArrayList;
import java.util.List;

import com.NG.db.ShixiItem;
import com.NG.db.ShixiItemInSqlite;
import com.NG.db.ShixiMessage;

public class MyUtils {
	
	
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
	
	
	
	
}
