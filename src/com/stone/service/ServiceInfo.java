package com.stone.service;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

public class ServiceInfo extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		/*
		 * 
		 * 返回值：
		 *   START_STICKY: 和原来的onStart类似，在被系统kill掉后会restart，
		 * 	 但是不同的是onStartCommand会被调用并传入一个null值作为intent，这个时候service就可以对这种case做出处理。

			START_NOT_STICKY： 被kill掉后就不会自动restart了。

			START_REDELIVER_INTENT： 如果service被kill掉了，系统会把之前的intent再次发送给service，直到service处里完成。
			
			START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
		 */
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		super.onTaskRemoved(rootIntent);
	}

	@Override
	protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
		super.dump(fd, writer, args);
	}
	
	
}
