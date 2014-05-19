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

	// Within the database helper instance, we can write all the CRUD methods
	// here
	
	
	// Deal with ItemsOnline
	/**
	 * 添加单条实习item
	 * 
	 * @param item
	 */
	public void addSingleItemOnline(ShixiItemInSqlite item) {
		db.execSQL(
				"insert into hello_item values(null, ?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[] { item.getItem_id(), item.getTitle(),
						item.getSource(), item.getTime(), item.getSource_url(),
						item.getText_body(), item.getIs_clicked(),item.getIs_collected()

				});
	}

	/**
	 * 在一个数据库事物中添加多条item信息
	 * 
	 * @param items
	 */
	public void addMultipleItemsOnline(List<ShixiItemInSqlite> items) {
		db.beginTransaction();
		try {
			for (ShixiItemInSqlite i : items) {
				addSingleItemOnline(i);
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
	 * 
	 * @param item
	 */
	public void updateItemOnline(ShixiItemInSqlite item) {
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
	 * 查询数据库中得多条item
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
