package com.stone.ui;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.stone.R;

/*
 * 配置部件
 * 在onCreate中setContentView()函数前添加setResult(RESULT_CANCLE) ，这样如果在Activity初始化完成前按下了BACK按键，则Widget不会启动；
 * 在setContentView()函数之后（不一定要在onCreate中，在Activity退出前即可），添加如下设置以指定需要启动的Widget
 */	
public class AppWidgetConfigureActivity extends Activity implements OnClickListener {
	Button config1, config2, config3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			int widgetid = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);//从intent中得出widgetid
			//通知 appwdiget 的配置已取消
			Intent reslut = new Intent();
			reslut.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetid);
			setResult(RESULT_CANCELED, reslut);
			System.out.println("result cancel");
		}
		setContentView(R.layout.switch_configure);
		
		config1 = (Button) findViewById(R.id.btn_config1);
		config2 = (Button) findViewById(R.id.btn_config2);
		config3 = (Button) findViewById(R.id.btn_config3);
		config1.setOnClickListener(this);
		config2.setOnClickListener(this);
		config3.setOnClickListener(this);
		
//		config1.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					System.out.println("down");
//					return true;
//				case MotionEvent.ACTION_MOVE:
//					System.out.println("move");
//				case MotionEvent.ACTION_UP:
//					System.out.println("up");
//				}
//				return true;
//			}
//		});
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("down");
			return true;
		case MotionEvent.ACTION_MOVE:
			System.out.println("move");
		case MotionEvent.ACTION_UP:
			System.out.println("up");
		}
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	
	private void onCompletedConfigure() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			int widgetid = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);//从intent中得出widgetid
			//通知 appwdiget 的配置已完成
			Intent reslut = new Intent();
			reslut.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetid);
			setResult(RESULT_OK, reslut);
			System.out.println("result ok");
			finish();
			System.out.println("finish ok");
		}
	}

	@Override
	public void onClick(View v) {
		if (v == config1) {
			//do config1
		} else if (v == config2) {
			//do config2
		} else if (v == config3) {
			//do config3
		}
		onCompletedConfigure();
	}
	
}
