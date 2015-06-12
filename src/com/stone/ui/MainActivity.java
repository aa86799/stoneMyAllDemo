package com.stone.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.stone.R;
import com.stone.util.LogUtils;

public class MainActivity extends Activity implements OnItemClickListener {
	private final String TAG = "MainActivity";
	
	MultipleClickProcess mMultipleClickProcess;
	
	private ListView mListView;
	private String[] mDatas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.printInfo(TAG, "--------onCreate--------");
		setContentView(R.layout.main);

		mMultipleClickProcess = new MultipleClickProcess();
		
		radio();//录音
		
		/*
		 * onCreate onStart onResume
		 * 旋转屏幕
		 * onPause onSaveInstanceState onStop onDestroy onCreate onStart onRestoreInstanceState onResume
		 * 
		 */
		
		initDatas();
		mListView = (ListView) findViewById(R.id.lv_options);
		ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mDatas);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
	}
	
	private void initDatas() {
		mDatas = new String[] {
				"Gallery 加 ImageSwitcher",
				"ViewFlipper手势滑动切换",
				"MultipleClick 防多次点击",
				"PopupWindowActivity",
				"TestSqliteActivity",
				"TestWebView",
				"TestTransitionDrawable",
				"TestLayerList",
				"ToggleButton",
				"ExpandableListView",
				"TestFragmentActivity",
				"TestPreferenceActivity",
				"TestPreferenceFragmentActivity",
				"TestPreferenceFragmentHeaderActivity",
				"TestCustomPreferenceActivity",
				"TestActivityLunchMode",
				"TestLoaderActivity",
				"RotateBitmapActivity",
				"TestEditTextSpannable",
				"ListViewActivityByLoader",
				"HandlerActivity",
				"TestActionBar>sdk3.0",
				"TestTabWidget",
				"AppWidgetConfigureActivity",
				"AddWidgetActivity",
				"TestGesture",
				"ViewAnimationActivity",
				"PropertyAnimationActivity",
				"TestCustomViewActivity",
				"TestOpenGLES",
				"WebViewDemo",
				"TestRotateCircleViewActivity",
				"TestSwipeRefreshLayout",
				"DrawerLayoutActivity",
				"KeyguardManagerActi",
				"VelocityTrackerActi",
				"NotificationActi",
				"AudioFocusActi",
				"修改系统字体 SystemFontActivity",
				"TestFileProviderActivity",
				"测试volley，请求json和图片",
				"测试多线程",
				"测试EventBus",
				"测试v4-SlidingPaneLayout"

		};
	}
	
	private void startActi(Class clazz) {
		startActivity(new Intent(this, clazz));
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
			case 0: // Gallery 加 ImageSwitcher
				startActi(GalleryTestActivity.class);
				break;
			case 1: // viewflipper 和Gesture手势 左右滑动
				startActi(GestureViewFlipperActivity.class);
				break;
			case 2: // 防止在一定时间段内重复点击按钮
				mMultipleClickProcess.onClick(view);
				break;
			case 3: // popupwindow
				startActi(PopupWindowActivity.class);
				break;
			case 4: // TestSqlite
				startActi(TestSqliteActivity.class);
				break;
			case 5: // Test WebView
				startActi(TestWebView.class);
				break;
			case 6: // Test TransitionDrawable
				startActi(TestTransitionDrawable.class);
				break;
			case 7: // Test LayerList
				startActi(TestLayerList.class);
				break;
			case 8: // Test ToggleButton
				setContentView(R.layout.toggle_button);
				break;
			case 9: // Test ExpandableListView
				startActi(TestExpandableListView.class);
				break;
			case 10: // Test FragmentActivity
				startActi(TestFragmentActivity.class);
				break;
			case 11: // Test PreferenceActivity
				startActi(TestPreferenceActivity.class);
				break;
			case 12: // Test PreferenceFragmentActivity
				startActi(TestPreferenceFragmentActivity.class);
				break;
			case 13: // Test PreferenceFragmentHeaderActivity
				startActi(TestPreferenceFragmentHeaderActivity.class);
				break;
			case 14: // Test CustomPreference
				startActi(TestCustomPreferenceActivity.class);
				break;
			case 15: // Test ActivityLunchMode 
				startActi(TestActivityLunchMode.class);
				break;
			case 16: // Test Loader
				startActi(TestLoaderActivity.class);
				break;
			case 17: // Test rotate bitmap
				startActi(RotateBitmapActivity.class);
				break;
			case 18: // Test EditTextSpannable
				startActi(TestEditTextSpannable.class);
				break;
			case 19: // Test Loader
				startActi(ListViewActivityByLoader.class);
				break;
			case 20: // Test Handler
				startActi(HandlerActivity.class);
				break;
			case 21: // Test TestActionBar
				startActi(TestActionBar.class);
				break;
			case 22: // Test TabWidget
				startActi(TestTabWidget.class);
				break;
			case 23: // Test AppWidgetConfigureActivity
				startActi(AppWidgetConfigureActivity.class);
				break;
			case 24: // Test AddWidgetActivity
				startActi(AddWidgetActivity.class);
				break;
			case 25: // Test Gesture
				startActi(TestGesture.class);
				break;
			case 26: // Test Tween Animation 
				startActi(ViewAnimationActivity.class);
				break;
			case 27: // Test Property(ValueAnimation ObjectAnimation) Animation
				startActi(PropertyAnimationActivity.class);
				break;
			case 28: // Test CustomViewActivity
				startActi(TestCustomViewActivity.class);
				break;
			case 29: // Test OpenGLES
				startActi(TestOpenGLES.class);
				break;
			case 30: // Test WebViewDemo
				startActi(WebViewDemo.class);
				break;
			case 31: // Test RotateCircleView
				startActi(TestRotateCircleViewActivity.class);
				break;
			case 32: // Test SwipeRefreshLayout
				startActi(TestSwipeRefreshLayout.class);
				break;
			case 33: // Test DrawerLayoutActivity
				startActi(DrawerLayoutActivity.class);
				break;
			case 34: // Test KeyguardManagerActi
				startActi(KeyguardManagerActi.class);
				break;
			case 35: // Test KeyguardManagerActi
				startActi(VelocityTrackerActi.class);
				break;
			case 36: // Test NotificationActi
				startActi(NotificationActi.class);
				break;
			case 37: // Test AudioFocusActi
				startActi(AudioFocusActi.class);
				break;
			case 38: // Test SystemFontActivity
				startActi(SystemFontActivity.class);
				break;
			case 39: // Test FileProviderActivity
				startActi(TestFileProviderActivity.class);
				break;
			case 40: // Test FileProviderActivity
				startActi(VolleyActivity.class);
				break;
			case 41: // Test ConcurrentActivity
				startActi(ConcurrentActivity.class);
				break;
			case 42: // Test EventBusActivity
				startActi(EventBusActivity.class);
				break;
			case 43: // Test SlidingPaneLayoutActi
				startActi(SlidingPaneLayoutActi.class);
				break;
			default:
				break;
		}
	}

	String selection = null;
	String items[] = new String[] { "aaa", "bbb", "ccc" };

	/**
	 * 单选对话框
	 */
	private void radio() {

		OnClickListener listener = new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				System.out.println(which);
				selection = items[which];
			}
		};
		OnClickListener confirmListener = new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), selection, 0).show();
				dialog.dismiss();
			}
		};
		new AlertDialog.Builder(this)//
				.setTitle("单选框")//
				.setSingleChoiceItems(items, -1, listener)//
				.setPositiveButton("确定", confirmListener)//
				.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// getMenuInflater().inflate(R.menu.activity_main, menu);

		menu.add("增加");
		menu.add("修改");
		menu.add("删除");
		SubMenu subMenu = menu.addSubMenu("查询");// submenu 含有子项的 menu
		subMenu.add("按照序号查询");
		subMenu.add("按照姓名查询");
		subMenu.add("按照邮箱查询");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		LogUtils.printInfo(TAG, "--------onOptionsItemSelected--------");
		Toast.makeText(getApplicationContext(), item.getTitle(), 0).show();
		return super.onOptionsItemSelected(item);
	}
	
	@Override //程序易销毁时执行
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		LogUtils.printInfo(TAG, "--------onSaveInstanceState--------");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		LogUtils.printInfo(TAG, "--------onRestoreInstanceState--------");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtils.printInfo(TAG, "--------onStart--------");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtils.printInfo(TAG, "--------onResume--------");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtils.printInfo(TAG, "--------onPause--------");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogUtils.printInfo(TAG, "--------onStop--------");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtils.printInfo(TAG, "--------onDestroy--------");
	}

}
