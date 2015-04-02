package com.stone.receiver;

import java.util.Arrays;

import com.stone.R;
import com.stone.service.TimerWidgetService;

import android.app.PendingIntent;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.widget.RemoteViews;

/*
 * AppWidgetProvider 必须要接收android.appwidget.action.APPWIDGET_UPDATE 广播
 * 
 */
public class TimerWidgetProvider extends AppWidgetProvider {
	
	@Override //更新部件时调用，在第1次添加部件时也会调用
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		System.out.println("onUpdate widget：" + Arrays.toString(appWidgetIds));
		/*
		 * 构造pendingIntent发广播，onReceive()根据收到的广播，更新
		 */
		Intent intent = new Intent(context, TimerWidgetService.class);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
		RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.timer_widget);
		rv.setOnClickPendingIntent(R.id.start_stop, pendingIntent);
		appWidgetManager.updateAppWidget(appWidgetIds, rv);
	}
	
	@Override	//部件从host中删除
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		System.out.println("onDeleted widget");
	}
	
	@Override //第1次创建时调用，之后再创建不会调用
	public void onEnabled(Context context) {
		super.onEnabled(context);
		System.out.println("onEnabled widget");
	}
	
	@Override //当最后一个部件实例 被删除时 调用  用于清除onEnabled执行的操作
	public void onDisabled(Context context) {
		super.onDisabled(context);
		System.out.println("onDisabled widget");
	}
	
	@Override //
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		System.out.println("onReceive widget");
		/*
		 * 接收 <action android:name="com.stone.action.start"/>
           在其他组件或activity或service中发送这些广播
           
		 */
		if (intent.getAction().equals("com.stone.action.start")) {
			long time = intent.getLongExtra("time", 0);
			 updateWidget(context, time);
			 System.out.println("receive com.stone.action.start");
		}
	}
	
	private void updateWidget(Context context, long time) {
		//RemoteViews处理异进程中的View
		RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.timer_widget);
		System.out.println("time=" + time);
		rv.setTextViewText(R.id.counter, DateUtils.formatElapsedTime(time / 1000));
		
		
		AppWidgetManager am = AppWidgetManager.getInstance(context);
		int[] appWidgetIds = am.getAppWidgetIds(new ComponentName(context, TimerWidgetProvider.class));
		am.updateAppWidget(appWidgetIds, rv);//更新 所有实例
	}
}
