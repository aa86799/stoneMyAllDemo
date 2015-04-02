package com.stone.ui;

import com.stone.util.LogUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

/*
 * launchMode = singleTask
 * 同任务栈中 单一实例
 */
public class TestMode3 extends Activity {
	private static String obj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtils.printInfo("TestMode3", "-----------TestMode3 oncreate------");
		
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
				startActivity(new Intent(getApplicationContext(), TestMode3.class).putExtra("taskId", getTaskId()));
			}
		});
	}
}
