package com.intel.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	static final String DB_NAME = "timeline.db";
	static final int DB_VERSION = 1;
	static final String TABLE = "timeline";	
	static final String C_ID = "_id";	
	static final String C_POST = "post";	
	static final String C_USER = "user";	
	static final String C_CREATED_AT = "created_at";		
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//SQL for insert
		String SQL = "CREATE TABLE "+ TABLE + " ( " + 
					C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
					C_POST + " TEXT," + 
					C_USER + " TEXT," + 
					C_CREATED_AT + " integer" + 
					" );";
		
		Log.d("Yamba", "create table - " + SQL);
		
		//execute the SQL insert
		db.execSQL(SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//drop everything, we are lazzy
		Log.d("Yamba", "onUpgrade database");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE + ";");
		onCreate(db);
	}

}
