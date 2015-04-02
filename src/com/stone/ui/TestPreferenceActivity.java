package com.stone.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;

import com.stone.R;
import com.stone.util.LogUtils;

/**
 * 3.0以下使用的偏好设置
 * 使用xml中定义的相关key 自动存储在/data/data/com.stone/shared_prefs/com.stone_preferences.xml
 * @author stone
 *
 */
public class TestPreferenceActivity extends PreferenceActivity implements
		Preference.OnPreferenceClickListener,
		Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
	private static String TAG = "TestPreferenceActi";
	
	private CheckBoxPreference mapply_wifiPreference;       //打开wifi
	private CheckBoxPreference mapply_internetPreference;   //Internet共享
	private ListPreference depart_valuePreference;          //部门设置
	private EditTextPreference number_editPreference;       //输入电话号码
	private Preference mwifi_settingPreference;             //wifi设置
	private RingtonePreference alarmRingtonePreference, notificationRingtonePreference, ringtoneRingtonePreference;	//铃声

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
		
		//根据key值找到控件
		mapply_wifiPreference = (CheckBoxPreference) findPreference("apply_wifi");
		mapply_internetPreference = (CheckBoxPreference) findPreference("apply_internet");
		depart_valuePreference = (ListPreference) findPreference("depart_value");
		number_editPreference = (EditTextPreference) findPreference("number_edit");
		mwifi_settingPreference = (Preference) findPreference("wifi_setting");
		alarmRingtonePreference = (RingtonePreference) findPreference("ring_alarm");
		notificationRingtonePreference = (RingtonePreference) findPreference("ring_notification");
		ringtoneRingtonePreference = (RingtonePreference) findPreference("ring_ringtone");

		// 设置监听器
		mapply_internetPreference.setOnPreferenceClickListener(this); //点击
		mapply_internetPreference.setOnPreferenceChangeListener(this);//值改变 
		
		depart_valuePreference.setOnPreferenceClickListener(this);
		depart_valuePreference.setOnPreferenceChangeListener(this);
		
		number_editPreference.setOnPreferenceClickListener(this);
		number_editPreference.setOnPreferenceChangeListener(this);
		
		mwifi_settingPreference.setOnPreferenceClickListener(this);
		
		alarmRingtonePreference.setOnPreferenceChangeListener(this);
		notificationRingtonePreference.setOnPreferenceChangeListener(this);
		ringtoneRingtonePreference.setOnPreferenceChangeListener(this);
		

		// 得到我们的存储Preferences值的对象，然后对其进行相应操作
		SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(this);
		boolean apply_wifiChecked = shp.getBoolean("apply_wifi", false);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//注册sp 改变监听事件
		mapply_internetPreference.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mapply_internetPreference.getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	// 对控件进行的一些操作
	private void operatePreference(Preference preference) {
		if (preference == mapply_wifiPreference){                  //点击了    "打开wifi"
			LogUtils.printInfo(TAG, " Wifi CB, and isCheckd ="+ mapply_wifiPreference.isChecked());
		}else if (preference.getKey().equals("apply_internet")){   //点击了"Internet共享"
			LogUtils.printInfo(TAG, " internet CB, and isCheckd = "+ mapply_internetPreference.isChecked());
		}else if (preference == depart_valuePreference){           //点击了 "部门设置"
			LogUtils.printInfo(TAG, " department CB,and selectValue = " +//
					 depart_valuePreference.getValue() + ", Text="+ depart_valuePreference.getEntry());
		}else if (preference.getKey().equals("wifi_setting")) {    //点击了"wifi设置"
			mwifi_settingPreference.setTitle("its turn me.");
		}else if (preference == number_editPreference)             //点击了"输入电话号码"
			LogUtils.printInfo(TAG, "number:Old Value="+ number_editPreference.getText() + //
					", New Value="+ number_editPreference.getEditText().toString());
	}
	
	@Override // 点击事件触发
	public boolean onPreferenceClick(Preference preference) {
		LogUtils.printInfo(TAG, "onPreferenceClick----->"+String.valueOf(preference.getKey()));
		// 对控件进行操作
		operatePreference(preference);
		return false;
	}
   
	@Override  //点击事件触发 ，这个属于 PreferenceActivity
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		LogUtils.printInfo(TAG, "onPreferenceTreeClick----->"+preference.getKey());
		// 对控件进行操作
		operatePreference(preference);
		if (preference.getKey().equals("bluetooth_setting")) {
			/*
			 *  创建一个新的Intent，
			 *  函数如果返回true， 则跳转至该自定义的新的Intent;
			 *  函数如果返回false，则跳转至xml文件中配置的Intent;
			 */
			Intent i = new Intent(TestPreferenceActivity.this, TestWebView.class); 
			i.putExtra("type", "wifi");
			startActivity(i);
			return false;
		}
		if (preference.getKey().equals("wifi_setting")) {
			LogUtils.printInfo(TAG, "......wifi_setting....");
			return false;
		}
		return false;
	}

	// 当Preference的值发生改变时触发该事件，true则以新值更新控件的状态，false则do noting
	@Override
	public boolean onPreferenceChange(Preference preference, Object objValue) {
		LogUtils.printInfo(TAG, "onPreferenceChange----->"+String.valueOf(preference.getKey()));
		if (preference == mapply_wifiPreference){
			LogUtils.printInfo(TAG, "onPreferenceChange----->Wifi CB, and isCheckd = " + String.valueOf(objValue));
		}else if (preference.getKey().equals("apply_internet")) {
			LogUtils.printInfo(TAG, "onPreferenceChange----->internet CB, and isCheckd = "+ String.valueOf(objValue));
			return false;  //不保存新值
		}else if (preference == depart_valuePreference){
			LogUtils.printInfo(TAG, "onPreferenceChange----->Old Value"+ 
								depart_valuePreference.getValue()+" NewDeptName"+objValue);
		}else if (preference.getKey().equals("wifi_setting")) {
			LogUtils.printInfo(TAG, "onPreferenceChange----->change" + String.valueOf(objValue));
			mwifi_settingPreference.setTitle("its turn me.");  //重新设置title
		} else if (preference == number_editPreference) {
			LogUtils.printInfo(TAG, "onPreferenceChange----->Old Value = " + String.valueOf(objValue));
			return true; // 保存新值
		}
		return true;  //保存更新后的值， 如果preference的android:persistent=false, 那么系统不会保存它的值， 该属性默认为true
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Preference preference = findPreference(key);
		if (preference == number_editPreference) {
			LogUtils.printInfo(TAG, "-----"+sharedPreferences.getString(key, ""));
		}
		LogUtils.printInfo(TAG, "key="+key);
	}
}

