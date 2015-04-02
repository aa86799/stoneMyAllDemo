package com.stone.ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.RemoteViews;

public class MyAppWidget extends AppWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}

	@Override
	// 周期更新时调用
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
	
	}

	@Override
	// 当桌面部件删除时调用
	public void onDeleted(Context context, int[] appWidgetIds) {
	
	}

	@Override
	// 当AppWidgetProvider提供的第一个部件被创建时调用
	public void onEnabled(Context context) {
		
	}

	@Override
	// 当AppWidgetProvider提供的最后一个部件被删除时调用
	public void onDisabled(Context context) {
	
	}

}
