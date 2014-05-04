package com.NG.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShixiDatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "zhaoshixi.db";
	private static final int DATABASE_VERSION = 1;
	
	// Description of Tables
	public interface Messages {
		public static final String TABLE_NAME = "messages";
		public static final String _ID = "_id";
		public static final String MESSAGE_ID = "message_id";
		public static final String TITLE = "title";
		public static final String SOURCE = "source";
		public static final String TIME = "time";
		public static final String SOURCE_URL = "source_url";
		public static final String IS_CLICKED = "is_clicked";
	}
	
	public interface Items {
		public static final String TABLE_NAME = "items";
		public static final String _ID = "_id";
		public static final String ITEM_ID = "item_id";
		public static final String TITLE = "title";
		public static final String SOURCE = "source";
		public static final String TIME = "time";
		public static final String SOURCE_URL = "source_url";
		public static final String AUTHOR = "author";
		public static final String TEXT_BODY = "text_body";
	}
	
	public ShixiDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// Create message Table
		db.execSQL("CREATE TABLE " + Messages.TABLE_NAME + " (" +
				Messages._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				Messages.MESSAGE_ID + " INTEGER," +
				Messages.TITLE + " TEXT," +
				Messages.SOURCE + " TEXT," +
				Messages.TIME + "  TEXT," +
				Messages.SOURCE_URL + " TEXT," +
				Messages.IS_CLICKED + " TEXT" +
        ");");
		
		// Create item table
		db.execSQL("CREATE TABLE " + Items.TABLE_NAME + " (" +
				Items._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				Items.ITEM_ID + " INTEGER," +
				Items.TITLE + " TEXT," +
				Items.SOURCE + " TEXT," +
				Items.TIME + "  TEXT," +
				Items.SOURCE_URL + " TEXT," +
				Items.AUTHOR + " TEXT," +
				Items.TEXT_BODY + " TEXT" +
        ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop tables and recreate
		db.execSQL("DROP TABLE IF EXISTS " + Messages.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Items.TABLE_NAME + ";");
        onCreate(db);  
	}
}
