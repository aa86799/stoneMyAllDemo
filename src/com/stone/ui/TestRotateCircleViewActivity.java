package com.stone.ui;

import android.app.Activity;
import android.os.Bundle;

import com.stone.R;
import com.stone.view.RotateCircleView;

public class TestRotateCircleViewActivity extends Activity {
	RotateCircleView circleView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.rotate_circle);
		
	}
}
