package com.stone.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper {
	private final String TAG = "DBHelper";
	
	private final SqlLiteHelper helper;
	private SQLiteDatabase writeDB;
	private SQLiteDatabase readDB;
	private static DBHelper dbHelper;

	private DBHelper(Context context) {
//		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase("/data/data/"+ context.getPackageName() + "/test_book.db", null);
		helper = new SqlLiteHelper(context);
		writeDB = helper.getWritableDatabase();
		readDB = helper.getReadableDatabase();
	}

	public static DBHelper getDBHelper(Context context) {
		if (dbHelper == null) {
			dbHelper = new DBHelper(context);
		}
		return dbHelper;
	}
	/**
	 * 通过反射 将对象保存在DB
	 * @param t		保存的对象
	 * @param table	表名
	 * @param nullColumnHack	返回的主键
	 */
	public <T> void insert(T t, String table,  String nullColumnHack) {
		try {
			ContentValues contentValues = new ContentValues();
			Field[] fields = t.getClass().getDeclaredFields();
			Object value = null;
			for (Field field : fields) {
				value = field.get(t);
				contentValues.put(field.getName(), value.toString());
			}
			writeDB.insert(table, nullColumnHack, contentValues);
//			writeDB.close();
		} catch (Exception e) {
			LogUtils.printError(TAG, "插入book记录", e);
		}
	}
	
	public void delete() {
//		writeDB.delete(table, whereClause, whereArgs)
	}
	public void update() {
//		writeDB.update(table, values, whereClause, whereArgs)
//		writeDB.update(table, values, "id=? and title=?", new String[idvalue, titlevalue]);
	}
	/**
	 *  通过反射 查询出实体对象 ，只能查询不含外键引用的
	 * @param clz
	 * @param table
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public Object query(Class clz,String table,String[] columns, //
			String selection, String[] selectionArgs) {
		try {
			Cursor c = readDB.query(table, columns, selection, selectionArgs, null, null, null);
			Constructor constructor = clz.getConstructor(); 
			Object instance = constructor.newInstance();
			Field[] fields = clz.getDeclaredFields();
			if (c.moveToNext()) {
				LogUtils.printInfo(TAG, "cursor has sons" +  c.getCount());
				for (Field field : fields) {
//					field.setAccessible(true);
					field.set(instance, c.getString(c.getColumnIndex(field.getName())));
				}
			}
			c.close();
//			readDB.close();
			return instance;
		} catch (Exception e) {
			LogUtils.printError(TAG,"----查询一条book记录出错---", e);
			return null;
		}
		
	}
	
	public int clearDatas(String table, String whereClause, String[] whereArgs) {
		try {
			return writeDB.delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			LogUtils.printError(TAG,"----clear book记录出错---", e);}
		return -1;
	}
}
