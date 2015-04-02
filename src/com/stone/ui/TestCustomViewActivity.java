package com.stone.ui;

import com.stone.R;
import com.stone.view.CustomView1;
import com.stone.view.CustomView2;

import android.app.Activity;
import android.os.Bundle;

public class TestCustomViewActivity extends Activity {
	CustomView1 customView1;
	CustomView2 customView2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		customView1 = new CustomView1(this);
//		setContentView(customView1);
		
		setContentView(R.layout.customview1);
		
//		customView2 = new CustomView2(this);
//		setContentView(customView2);
	}
}
