package com.stone.ui;

import java.util.List;

import com.stone.R;
import com.stone.util.LogUtils;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/*
 * 3.0以上使用偏好头文件有个好处：就是在大屏幕比如平板上，可以自动 显示为左右两个面板
 */
//本activity兼容3.0以下
public class TestPreferenceFragmentHeaderActivity extends PreferenceActivity {
	
	@Override	//>=3.0时 会直接调用该函数 不调用onCreate
	public void onBuildHeaders(List<Header> target) {
		super.onBuildHeaders(target);
		/*
		 加载 偏好头文件, 头文件中可以使用多个fragment来加载不同的preference文件；
		 也可以使用同一个，用<extra>来区分， getArguments.getString 来取出
		 */
		loadHeadersFromResource(R.xml.preference_header, target);
		LogUtils.printInfo("", "加载了header偏好头文件");
	}

	/*
	android4.4 KITKAT 修复了该漏洞，要重写  具体百度：Android框架层漏洞-Fragment注入
	 */
	@Override
	protected boolean isValidFragment(String fragmentName) {
//		return super.isValidFragment(fragmentName);
		return true;
	}


	@Override	//小于3.0时 加载
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			// Load the legacy preferences headers
	        addPreferencesFromResource(R.xml.preference);
	        LogUtils.printInfo("", "加载了old 偏好文件");
	        /*
	         * 加载旧偏好时，可以使用 <Preference><intent>跳转到同一个preferenceactivity，根据不同的action来加载不同的偏好文件
	         */
	    }
	}
}
