package com.stone.ui;

import com.stone.R;

import android.app.Activity;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class TestTransitionDrawable extends Activity {

	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		iv = (ImageView) findViewById(R.id.iv_splash);
		//只能显示两张图片的 过渡效果
		TransitionDrawable td = (TransitionDrawable) getResources().getDrawable(R.drawable.trans);
		iv.setImageDrawable(td);
		td.startTransition(3000);

//        ColorDrawable
	}
}
