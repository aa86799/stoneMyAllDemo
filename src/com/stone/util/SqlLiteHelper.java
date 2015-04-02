package com.stone.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteHelper extends SQLiteOpenHelper {
	private final String TAG = "SqlLiteHelper";
	public SqlLiteHelper(Context context) {
		super(context, Constants.DB_NAME, null, Constants.DB_VERSION); //建库
		LogUtils.printInfo(TAG, "------create database----");
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//建表
		db.execSQL("create table book( id varchar(50) primary key, title varchar(50), imgUrl varchar(200) )");
		LogUtils.printInfo(TAG, "------create table----");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("drop table if exists book");
//		db.execSQL("alter table book add column content varchar(500)");
	}
	
}
