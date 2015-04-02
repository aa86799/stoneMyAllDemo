package com.stone.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.stone.R;

/*
 * PreferenceFragment 3.0之后使用的，在v4包中也没有它。 3.0之前使用 PreferenceActivity.addPreferencesFromResource(R.xml.p)
 * 详情见TestPreferenceActivity TestPreferenceActi
 */
public class MyPreferenceFragment1 extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		1.直接加载一个preference的xml
		addPreferencesFromResource(R.xml.preference1);
		
//		2.复用，加载不同的xml
	}
}
