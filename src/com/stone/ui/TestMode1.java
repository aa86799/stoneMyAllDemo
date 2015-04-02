package com.stone.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stone.util.LogUtils;

/*
 * launchMode = standard
 * 每次启动新的Activity, 在相同的task栈中，taskid一致
 */
public class TestMode1 extends Activity {
	private static String obj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtils.printInfo("TestMode1", "-----------TestMode1 oncreate------");
		
		Button btn = new Button(this);
		btn.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setContentView(btn);
		
		btn.setText("current taskId=" + getTaskId() //
				+ ", 传入的taskId=" + getIntent().getIntExtra("taskId", 0) //
				+ ", last Object="+ obj //
				+ ", current obj="+ this //
				+ "\n点击我，再弹出一个跟我一样的窗口，看看是不是新弹出了？回退需要几次？前面的taskid obj对象有什么变化？");
		obj = this.toString();
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), TestMode1.class).putExtra("taskId", getTaskId()));
			}
		});
		 
	}
	
	public static class MyReceiver extends BroadcastReceiver {
		
		public MyReceiver() {
			
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "收到了一个 MyReceiver", 0).show();
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtils.printInfo("TestMode1", "-----------TestMode1 onResume------");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtils.printInfo("TestMode1", "-----------TestMode1 onPause------");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtils.printInfo("TestMode1", "-----------TestMode1 onStart------");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtils.printInfo("TestMode1", "-----------TestMode1 onDestroy------");
	}
}	
