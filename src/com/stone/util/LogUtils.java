package com.stone.util;

import android.util.Log;

public class LogUtils {
	


	public static void printInfo(String tag, Object object) {
		if (Constants.DEBUG) {
			Log.i(tag, object.toString());
		}
	}
	
	public static void printError(String tag, Object object, Exception e) {
		if (Constants.DEBUG) {
			Log.e(tag, object.toString());
			e.printStackTrace();
		}
	}

}
