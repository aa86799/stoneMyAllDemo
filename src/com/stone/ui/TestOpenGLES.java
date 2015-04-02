package com.stone.ui;

import android.app.Activity;
import android.os.Bundle;

import com.stone.R;
import com.stone.view.MyGLSurfaceView;

public class TestOpenGLES extends Activity {
	MyGLSurfaceView mGLView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mGLView = new MyGLSurfaceView(this);
		setContentView(mGLView);
		
		String locale = getResources().getConfiguration().locale.getDisplayName();
		System.out.println("the locale is : " + locale);
		
		String format_string = getString(R.string.format_string, "abc", 98.33);
		System.out.println("the format_string is : " + format_string);
		
		int num = 2;
		String quantityString = getResources().getQuantityString(R.plurals.planes, num, num);
		System.out.println("the quantityString is : " + quantityString);
		
		boolean is_show = getResources().getBoolean(R.bool.is_show);
		System.out.println("the boolean is : " + is_show);
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mGLView.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mGLView.onResume();
	}
}
