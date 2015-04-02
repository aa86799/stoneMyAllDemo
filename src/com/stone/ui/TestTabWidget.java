package com.stone.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;

import com.stone.R;
import com.stone.fragment.Frag1;
import com.stone.fragment.Frag2;
import com.stone.fragment.Frag3;
/*
 * ____________
 * |		  |
 * |		  |
 * |tabcontent|
 * |显示标签页	  |
 * |__________|  区域是由tabhost控制， 显示内容的区域为tabcontent
 * tab标签 组合 fragment
 */
public class TestTabWidget extends Activity {
	TabHost tabHost;
	Fragment frag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_tabwidget);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				System.out.println("current tabid=" + tabId);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				if (TextUtils.equals("first", tabId)) {
					//add/replace fragment first
					frag = new Frag1();
					System.out.println("load f1");
				} else if (TextUtils.equals("second", tabId)) {
					//add/replace fragment second
					frag = new Frag2();
					System.out.println("load f2");
				} else if (TextUtils.equals("third", tabId)) {
					//add/replace fragment third
					frag = new Frag3();
					System.out.println("load f3");
				}
				ft.replace(android.R.id.tabcontent, frag, "frag");
				ft.commit();
			}
		});
		tabHost.addTab(tabHost.newTabSpec("first").setIndicator("First")
				.setContent(new DummyTabFactory(this)));
		tabHost.addTab(tabHost.newTabSpec("second").setIndicator("Second")//setIndicator 设置标签样式
				.setContent(new DummyTabFactory(this))); //setContent 点击标签后触发
		tabHost.addTab(tabHost.newTabSpec("third").setIndicator("Third")
				.setContent(new DummyTabFactory(this)));
	}
	
	static class DummyTabFactory implements TabHost.TabContentFactory {
		private Context context;
		public DummyTabFactory(Context ctx) {
			this.context = ctx;
		}
		@Override
		public View createTabContent(String tag) {//创建宽高均为0的view 
			View v = new ImageView(context);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
		
	}
}
