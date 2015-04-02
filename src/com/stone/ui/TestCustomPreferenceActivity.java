package com.stone.ui;

import com.stone.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class TestCustomPreferenceActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.custom_preference);
	}
}
