package com.stone.ui;

import java.lang.reflect.Field;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;

import com.stone.R;

public class TestActionBar extends Activity implements TabListener {
	ActionBar bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//theme = android:Theme.Holo.Light  使用这个主题后才得到了bar对象 
		bar = getActionBar();
		bar.hide();
		bar.show();
//		bar.setLogo(R.drawable.a8);//
		//设置操作栏的导航模式 添加在上方的操作栏上
//		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD); //标准模式，即不显示tab，也不显示list,只是普通的

		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);//标签式
		Tab tab = bar.newTab();
		tab.setIcon(R.drawable.a11);
		tab.setText("tab text");
		tab.setTabListener(this);
//		tab.setCustomView(view); //设置自定义view为标签
		bar.addTab(tab);//添加标签
		
		Tab tab2 = bar.newTab();
		tab2.setIcon(R.drawable.a2);
		tab2.setText("tab2 text");
		tab2.setTabListener(this);
		bar.addTab(tab2);//添加标签
		
		setContentView(R.layout.test);
		
		findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//开启一个列表式actionbar
				startActivity(new Intent(getApplicationContext(), TestActionBar2.class));
				
			}
		});
		findViewById(R.id.btn_delete).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				startActivity(new Intent(getApplicationContext(), TestActionBar2.class));
			}
		});
		
		getOverflowMenu();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * 在actionbar可用时，发现menu操作项直接显示在了屏幕的下方
		 * 在actionbar不可用即为null时，发现menu操作项被隐藏了 按menu呼出
		 */
		getMenuInflater().inflate(R.menu.actionbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("onOptionsItemSelected::::" + item.getItemId());
		return super.onOptionsItemSelected(item);
	}

	@Override //TabListener
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		switch (tab.getPosition()) {
		case 0:
			System.out.println("ft.add fragment1");
			break;
		case 1:
			System.out.println("ft.add fragment2");
			break;

		default:
			break;
		}
		System.out.println(tab.getText() + "---Tab Selected---");
	}

	@Override //TabListener
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		switch (tab.getPosition()) {
		case 0:
			System.out.println("ft.remove fragment1");
			break;
		case 1:
			System.out.println("ft.remove fragment2");
			break;

		default:
			break;
		}
		System.out.println(tab.getText() + "---Tab Unselected---");
	}

	@Override //TabListener
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		System.out.println(tab.getText() + "---Tab Reselected---");
	}
	
	private void getOverflowMenu() {
	    try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false); //使用menu key 为false，当action bar操作栏空间不足时，会显示出更多按钮
	            //新版本上，会自动显示出更多按钮   该函数没什么大用
	        }
//	        config.hasPermanentMenuKey();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
