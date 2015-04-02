package com.stone.ui;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.stone.R;

public class TestActionBar2 extends Activity implements TabListener {
	ActionBar bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//theme = android:Theme.Holo.Light  使用这个主题后才得到了bar对象 
		bar = getActionBar();
		System.out.println(bar==null); //false
		bar.hide();
		bar.show();
//		bar.setLogo(R.drawable.a8);//
		//设置操作栏的导航模式 添加在上方的操作栏上
//		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);//导航方式：列表
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add("bar item" + i);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		bar.setListNavigationCallbacks(adapter, new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				//bar.getSelectedNavigationIndex() <==> itemPosition
				System.out.println((bar.getSelectedNavigationIndex() == itemPosition) +""+ itemPosition);
				return false;
			}
			
		});
		
		
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
}
