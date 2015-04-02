package com.stone.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.stone.R;

/*
	
 */
public class MyPreferenceFragment2 extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		1.直接加载一个preference的xml
		addPreferencesFromResource(R.xml.preference2);
		
	}
}
