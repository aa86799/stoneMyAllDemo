package com.stone.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.text.TextUtils;

import com.stone.R;
import com.stone.util.LogUtils;

/*
 * PreferenceFragment 3.0之后使用的，在v4包中也没有它。 3.0之前使用 PreferenceActivity.addPreferencesFromResource(R.xml.p)
 * 详情见TestPreferenceActivity TestPreferenceActi
 */
public class MyPreferenceFragment extends PreferenceFragment {
	private final String TAG = "MyPreferenceFragment"; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
//		2.复用，加载不同的xml, 需要一个preference-headers的xml文件来定义
		String settingValue = getArguments().getString("preKey");
		if (TextUtils.equals("pre1", settingValue)) {
			addPreferencesFromResource(R.xml.preference1);
			LogUtils.printInfo(TAG, "加载了偏好文件1");
		} else if (TextUtils.equals("pre2", settingValue)) {
			addPreferencesFromResource(R.xml.preference2);
			LogUtils.printInfo(TAG, "加载了偏好文件2");
		} else if (TextUtils.equals("pre_default", settingValue)){
//			直接加载一个preference的xml
			addPreferencesFromResource(R.xml.preference);
			LogUtils.printInfo(TAG, "加载了默认偏好文件");
		}
	}
}
