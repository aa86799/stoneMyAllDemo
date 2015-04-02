package com.stone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimerWidgetService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		/*
		 * do some thing
		 */
		//发送广播通知 widget 更新 状态
		sendBroadcast(new Intent("com.stone.action.start").putExtra("time", System.currentTimeMillis()));
		return Service.START_STICKY;
	}
}
