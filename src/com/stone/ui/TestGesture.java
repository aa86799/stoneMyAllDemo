package com.stone.ui;

import android.app.Activity;
import android.os.Bundle;

import com.stone.view.TouchExample;

public class TestGesture extends Activity {
	TouchExample touchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		touchView = new TouchExample(this);
		
		setContentView(touchView);
	}
	
}
