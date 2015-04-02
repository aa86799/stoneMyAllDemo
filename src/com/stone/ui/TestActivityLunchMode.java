package com.stone.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.stone.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivityLunchMode extends Activity implements OnClickListener{
	Button btn1, btn2, btn3, btn4, btn5, btn6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_luncher_mode);
		
		btn1 = (Button) findViewById(R.id.btn_001);
		btn2 = (Button) findViewById(R.id.btn_002);
		btn3 = (Button) findViewById(R.id.btn_003);
		btn4 = (Button) findViewById(R.id.btn_004);
		btn5 = (Button) findViewById(R.id.btn_005);
		btn6 = (Button) findViewById(R.id.btn_006);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("taskId", getTaskId());
		if (v == btn1) {
			intent.setClassName("com.stone", "com.stone.ui.TestMode1");
		} else if (v == btn2) {
			intent.setClassName("com.stone", "com.stone.ui.TestMode2");
		} else if (v == btn3) {
			intent.setClassName("com.stone", "com.stone.ui.TestMode3");
		} else if (v == btn4) {
			intent.setClassName("com.stone", "com.stone.ui.TestMode4");
		} else if (v == btn5) {
			intent.setAction("com.stone.action.test.broad1");
			sendBroadcast(intent);
			return;
		} else if (v == btn6) {
			intent.setAction("com.stone.action.test.broad2");
			sendStickyBroadcast(intent);
			return;
		} else {
			return;
		}
		
		startActivity(intent);
	}
}
