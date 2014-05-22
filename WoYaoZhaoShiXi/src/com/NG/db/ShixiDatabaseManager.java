package com.NG.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ShixiDatabaseManager {

	private ShixiDatabaseHelper helper;
	private SQLiteDatabase db;
	
	private static String TAG = "ShixiDatabaseManager";

	public ShixiDatabaseManager(Context context) {
		helper = new ShixiDatabaseHelper(context);
		db = helper.getWritableDatabase();
	}

	// Within the database helper instance, we can write all the CRUD methods
	// here
	
	
	// Deal with ItemsOnline
	/**
	 * 添加单条实习item
	 * 
	 * @param item
	 */
	public int addSingleItemOnline(ShixiItemInSqlite item) {
		int item_id = item.getItem_id();
		Cursor c = db.rawQuery("select * from hello_item where item_id=?",
				new String[] { item_id + "" });
		if (c.moveToFirst() == false) {
			Log.d(TAG, "本地数据库中 没有这条item");
			db.execSQL(
					"insert into hello_item values(null, ?, ?, ?, ?, ?, ?, ?, ?)",
					new Object[] { item.getItem_id(), item.getTitle(),
							item.getSource(), item.getTime(),
							item.getSource_url(), item.getText_body(),
							item.getIs_clicked(), item.getIs_collected()

					});
			return 1;
		} else {
			Log.d(TAG, "本地数据库中 存在这条item");
			return 0;
		}

	}

	/**
	 * 在一个数据库事物中添加多条item信息
	 * 
	 * @param items
	 */
	public int addMultipleItemsOnline(List<ShixiItemInSqlite> items) {
		int count = 0;
		db.beginTransaction();
		try {
			for (ShixiItemInSqlite i : items) {
				count += addSingleItemOnline(i);
			}
			db.setTransactionSuccessful();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		Log.d(TAG, "当前操作 插入了"+count+"条数据");
		return count;
	}

	/**
	 * 更新数据库中的一条item
	 * 
	 * @param item
	 */
	public int updateItemOnline(ShixiItemInSqlite item) {
		if(querySingleItemOnline(item.getItem_id()).getItem_id()==0){
			return 0;
		}
		else{
			db.execSQL(
					"update hello_item set title=?,source=?,time=?,source_url=?,text_body=? " +
					",clicked=? ,collected=? where item_id =?",
					new Object[] { item.getTitle(), 
							item.getSource(),
							item.getTime(), 
							item.getSource_url(),
							item.getText_body(), 
							item.getIs_clicked(), 
							item.getIs_collected(), 
							item.getItem_id(),
							 });
			return 1;
		}
		
	}

	/**
	 * 删除数据库中的一条item
	 * 
	 * @param item_id
	 */
	public void deleteItemOnline(int item_id) {
		db.execSQL("delete from hello_item where item_id=?",
				new Object[] { item_id });
	}

	/**
	 * 查询数据库中的一条item
	 * 
	 * @param item
	 * @return
	 */
	public ShixiItemInSqlite querySingleItemOnline(int item_id) {
		ShixiItemInSqlite item = new ShixiItemInSqlite();
		Cursor c = db.rawQuery("select * from hello_item where item_id=?",
				new String[] { item_id + "" });
		while (c.moveToNext()) {
			item.setItem_id(c.getInt(c.getColumnIndex("item_id")));
			item.setTitle(c.getString(c.getColumnIndex("title")));
			item.setSource(c.getString(c.getColumnIndex("source")));
			item.setTime(c.getString(c.getColumnIndex("time")));
			item.setSource_url(c.getString(c.getColumnIndex("source_url")));
			item.setText_body(c.getString(c.getColumnIndex("text_body")));
			item.setIs_clicked(c.getInt(c.getColumnIndex("clicked")));
			item.setIs_collected(c.getInt(c.getColumnIndex("collected")));
			
		}
		c.close();

		return item;
	}

	/**
	 * 查询数据库中的所有item
	 * 
	 * @return
	 */
	public List<ShixiItemInSqlite> queryMultipleItemsOnline() {
		ArrayList<ShixiItemInSqlite> items = new ArrayList<ShixiItemInSqlite>();
		Cursor c = db.rawQuery("select * from hello_item order by time DESC", null);
		while (c.moveToNext()) {
			ShixiItemInSqlite item = new ShixiItemInSqlite();

			System.out.println("item_id = "
					+ c.getString(c.getColumnIndex("item_id")));

			item.setItem_id(c.getInt(c.getColumnIndex("item_id")));
			item.setTitle(c.getString(c.getColumnIndex("title")));
			item.setSource(c.getString(c.getColumnIndex("source")));
			item.setTime(c.getString(c.getColumnIndex("time")));
			item.setSource_url(c.getString(c.getColumnIndex("source_url")));
			item.setText_body(c.getString(c.getColumnIndex("text_body")));
			item.setIs_clicked(c.getInt(c.getColumnIndex("clicked")));
			item.setIs_collected(c.getInt(c.getColumnIndex("collected")));

			items.add(item);
		}
		c.close();

		return items;
	}
	
	/**
	 * 根据 time 查询数据库中对应的item
	 * 
	 * @return
	 */
	public List<ShixiItemInSqlite> queryMultipleItemsByTime(String time, int n) {
		ArrayList<ShixiItemInSqlite> items = new ArrayList<ShixiItemInSqlite>();
		Cursor c = db.rawQuery("select * from hello_item where time < " + time
				+ " order by time desc limit " + n, null);
		while (c.moveToNext()) {
			ShixiItemInSqlite item = new ShixiItemInSqlite();

			System.out.println("item_id = "
					+ c.getString(c.getColumnIndex("item_id")));

			item.setItem_id(c.getInt(c.getColumnIndex("item_id")));
			item.setTitle(c.getString(c.getColumnIndex("title")));
			item.setSource(c.getString(c.getColumnIndex("source")));
			item.setTime(c.getString(c.getColumnIndex("time")));
			item.setSource_url(c.getString(c.getColumnIndex("source_url")));
			item.setText_body(c.getString(c.getColumnIndex("text_body")));
			item.setIs_clicked(c.getInt(c.getColumnIndex("clicked")));
			item.setIs_collected(c.getInt(c.getColumnIndex("collected")));

			items.add(item);
		}
		c.close();

		return items;
	}

	/**
	 * 删除items表中所有数据
	 * */
	public void resetItems() {

		db.execSQL("delete from hello_item;" + 
				"select * from sqlite_sequence;" + 
				"update sqlite_sequence set seq=0 where name=hello_item; ");

	}

	/**
	 * 关闭数据库连接
	 */
	public void closeDB() {
		db.close();
	}
}
