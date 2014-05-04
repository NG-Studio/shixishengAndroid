package com.NG.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ShixiDatabaseManager {
	
	private ShixiDatabaseHelper helper;
	private SQLiteDatabase db;
	
	public ShixiDatabaseManager(Context context) {
		helper = new ShixiDatabaseHelper(context);
		db = helper.getWritableDatabase();
	}
	
	// Within the database helper instance, we can write all the CRUD methods here
	// Deal with Message
	/**
	 * 添加单条实习message
	 * @param message
	 */
	public void addSingleMessage(ShixiMessage message) {
		db.execSQL("insert into messages values(null, ?, ?, ?, ?, ?, ?)", new Object[] {message.getMessage_id(),
				message.getTitle(),
				message.getSource(),
				message.getTime(),
				message.getSource_url(),
				message.getIs_clicked()});
	}
	
	/**
	 * 在一个数据库事物中添加多条message信息
	 * @param messages
	 */
	public void addMultipleMessages(List<ShixiMessage> messages) {  
        db.beginTransaction();  
        try {  
            for (ShixiMessage m : messages) {  
            	addSingleMessage(m);
            }  
            db.setTransactionSuccessful();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            db.endTransaction();  
        }  
    }
	
	/**
	 * 更新数据库中的一条message
	 * @param message
	 */
	public void updateMessage(ShixiMessage message) {
		db.execSQL("update messages set title=?,source=?,time=?,source_url=?,is_clicked=? where message_id = ?",
				new Object[] {
				message.getTitle(),
				message.getSource(),
				message.getTime(),
				message.getSource_url(),
				message.getIs_clicked(),
				message.getMessage_id(),
		});
	}
	
	/**
	 * 删除数据库中的一条message
	 * @param message_id
	 */
	public void deleteMessage(int message_id) {
		db.execSQL("delete from messages where message_id=?", new Object[] {message_id});
	}
	
	/**
	 * 查询数据库中的一条message
	 * @param message_id
	 * @return
	 */
	public 	ShixiMessage querySingleMessage(int message_id){
		ShixiMessage message = new ShixiMessage();
		Cursor c = db.rawQuery("select * from messages where message_id=?", new String[] {message_id +""});
		while(c.moveToNext()) {
			message.setTitle(c.getString(c.getColumnIndex("title")));
			message.setSource(c.getString(c.getColumnIndex("source")));
			message.setTime(c.getString(c.getColumnIndex("time")));
			message.setSource_url(c.getString(c.getColumnIndex("source_url")));
			message.setIs_clicked(c.getString(c.getColumnIndex("is_clicked")));
		}
		c.close();
		
		return message;
	}
	
	/**
	 * 查询数据库中得多条message
	 * @return
	 */
	public List<ShixiMessage> queryMultipleMessages() {
		ArrayList<ShixiMessage> messages = new ArrayList<ShixiMessage>();
		Cursor c = db.rawQuery("select * from messages", null);
		while(c.moveToNext()) {
			ShixiMessage message = new ShixiMessage();
			
			message.setMessage_id(c.getInt(c.getColumnIndex("message_id")));
			message.setTitle(c.getString(c.getColumnIndex("title")));
			message.setSource(c.getString(c.getColumnIndex("source")));
			message.setTime(c.getString(c.getColumnIndex("time")));
			message.setSource_url(c.getString(c.getColumnIndex("source_url")));
			message.setIs_clicked(c.getString(c.getColumnIndex("is_clicked")));
			
			messages.add(message);
		}
		c.close();
		
		return messages;
	}
	
	// Deal with Item
		/**
		 * 添加单条实习item
		 * @param item
		 */
		public void addSingleItem(ShixiItem item) {
			db.execSQL("insert into items values(null, ?, ?, ?, ?, ?, ?, ?)", new Object[] {item.getItem_id(),
					item.getTitle(),
					item.getSource(),
					item.getTime(),
					item.getSource_url(),
					item.getAuthor(),
					item.getText_body()});
		}
		
		/**
		 * 在一个数据库事物中添加多条item信息
		 * @param items
		 */
		public void addMultipleItems(List<ShixiItem> items) {  
	        db.beginTransaction();  
	        try {  
	            for (ShixiItem i : items) {  
	            	addSingleItem(i);
	            }  
	            db.setTransactionSuccessful();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            db.endTransaction();  
	        }  
	    }
		
		/**
		 * 更新数据库中的一条item
		 * @param item
		 */
		public void updateItem(ShixiItem item) {
			db.execSQL("update items set title=?,source=?,time=?,source_url=?,author=?,text_body=? where item_id = ?",
					new Object[] {
					item.getTitle(),
					item.getSource(),
					item.getTime(),
					item.getSource_url(),
					item.getAuthor(),
					item.getText_body(),
					item.getItem_id(),
			});
		}
		
		/**
		 * 删除数据库中的一条item
		 * @param item_id
		 */
		public void deleteItem(int item_id) {
			db.execSQL("delete from items where item_id=?", new Object[] {item_id});
		}
		
		/**
		 * 查询数据库中的一条item
		 * @param item
		 * @return
		 */
		public 	ShixiItem querySingleItem(int item_id){
			ShixiItem item = new ShixiItem();
			Cursor c = db.rawQuery("select * from items where item_id=?", new String[] {item_id +""});
			while(c.moveToNext()) {
				item.setTitle(c.getString(c.getColumnIndex("title")));
				item.setSource(c.getString(c.getColumnIndex("source")));
				item.setTime(c.getString(c.getColumnIndex("time")));
				item.setSource_url(c.getString(c.getColumnIndex("source_url")));
				item.setAuthor(c.getString(c.getColumnIndex("author")));
				item.setText_body(c.getString(c.getColumnIndex("text_body")));
			}
			c.close();
			
			return item;
		}
		
		/**
		 * 查询数据库中得多条item
		 * @return
		 */
		public List<ShixiItem> queryMultipleItems() {
			ArrayList<ShixiItem> items = new ArrayList<ShixiItem>();
			Cursor c = db.rawQuery("select * from items", null);
			while(c.moveToNext()) {
				ShixiItem item = new ShixiItem();
				
				item.setTitle(c.getString(c.getColumnIndex("title")));
				item.setSource(c.getString(c.getColumnIndex("source")));
				item.setTime(c.getString(c.getColumnIndex("time")));
				item.setSource_url(c.getString(c.getColumnIndex("source_url")));
				item.setAuthor(c.getString(c.getColumnIndex("author")));
				item.setText_body(c.getString(c.getColumnIndex("text_body")));
				
				items.add(item);
			}
			c.close();
			
			return items;
		}
		
		/**
		 * 关闭数据库连接
		 */
		public void closeDB() {
			db.close();
		}
}
