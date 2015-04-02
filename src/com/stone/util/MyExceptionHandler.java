package com.stone.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
/**
 *	异常处理 UncaughtExceptionHandler
 */
public class MyExceptionHandler implements UncaughtExceptionHandler {
	private static MyExceptionHandler meh;
	private static Context context;
	public MyExceptionHandler(Context context) {
		this.context = context;
	}
	
	public static synchronized MyExceptionHandler getInstance() {
		if (meh == null) {
			meh = new MyExceptionHandler(context);
		}
		return meh;
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		try {
			StringBuilder sb = new StringBuilder();
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			sb.append("程序的版本号为：" + info.versionName + "\n");
			//手机硬件信息
			Field[] fields = Build.class.getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				sb.append(f.getName() + "=" + f.get(null).toString() + "\n");
			}
			//程序错误的堆栈信息
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			String stackinfo = writer.toString();
			sb.append(stackinfo);
			
			//sb.toString(); //  发送到服务端
		} catch (Exception e) {
			
		}
	}

}
