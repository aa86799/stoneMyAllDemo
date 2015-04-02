package com.stone.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

public class TestViewPager extends Activity {
	ViewPager viewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		viewPager = findViewById();
		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				System.out.println("=========" + arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return false;
		}
		
	}
}
