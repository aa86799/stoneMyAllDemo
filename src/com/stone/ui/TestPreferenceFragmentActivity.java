package com.stone.ui;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.stone.R;
import com.stone.fragment.MyPreferenceFragment;
import com.stone.util.LogUtils;

/**
 * 3.0以上使用，，优先使用header的  见TestPreferenceFragmentHeaderActivity
 * @author stone
 *
 */
//使用普通的Activity加载preference fragment
public class TestPreferenceFragmentActivity extends Activity {
	private final String TAG = "TestPreferenceFragmentActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//表示是否多次设置默认值，需要在sharedpreference文件使用前设置
//		PreferenceManager.setDefaultValues(this, R.xml.preference, true); //true 多次， false 一次
//		PreferenceManager.getDefaultSharedPreferences(this).getString("", "");
		
		//类似在普通的Activity中加载一个fragment那样加载
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		MyPreferenceFragment fragment = new MyPreferenceFragment();
		Bundle bundle = new Bundle();
		bundle.putString("preKey", "pre_default");
		LogUtils.printInfo(TAG, "设置了arguments，打开默认偏好文件");
		fragment.setArguments(bundle);
		ft.replace(android.R.id.content, fragment, "frag_prefe");
		ft.commit();
		
	}
	
}
