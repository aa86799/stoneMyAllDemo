package com.stone.ui;

import com.stone.util.MyExceptionHandler;

import android.app.Application;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// 设置 异常处理的 handler
		MyExceptionHandler exceptionHandler = MyExceptionHandler.getInstance();
		Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
	}
}
