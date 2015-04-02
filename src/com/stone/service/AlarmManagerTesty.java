package com.stone.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
/**
 * AlarmManager  时钟管理器
 * 在特定的时刻为我们广播一个指定的Intent。
 * 简单的说就是我们设定一个时间，然后在该时间到来时，AlarmManager为我们广播一个我们设定的Intent。
 *
 */
public class AlarmManagerTesty extends Service {
	public static AlarmManager alarmManager;	//声明时钟管理器
	public static PendingIntent operation;	//声明PendingIntent
	private long triggerAtTime;  //触发器 时间
	private long interval; // 时间间隔
	private Intent intent;
	private BroadcastReceiver receiver;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter filter = new IntentFilter();
		receiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				
			}
		};
		filter.addAction("com.gg.attimetask");
		registerReceiver(receiver, filter);  //动态注册广播
		
		alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		triggerAtTime = System.currentTimeMillis(); 
		interval = 1000*60*1; // 1分钟
		intent = new Intent("com.gg.attimetask");
		operation = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);		
		if(alarmManager == null) {
			//获取时钟管理器
			alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);	
		}
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,//RTC_WAKEUP 在指定的时刻，发送广播，并唤醒设备 
				triggerAtTime, interval, operation);	//根据设定的时间间隔 重复的发送广播事件
		/*alarmManager.set(AlarmManager.RTC, //RTC 在指定的时刻，发送广播，但不唤醒设备 
				triggerAtTime, operation);// 只发送一次广播  
				*/
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(alarmManager != null){	
			alarmManager.cancel(operation);	//停止广播事件
		}
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
}
