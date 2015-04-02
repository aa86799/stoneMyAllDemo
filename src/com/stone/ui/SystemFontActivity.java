package com.stone.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.stone.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

/*
 * mconfig.fontScale = 0.75f;//设置要改变字体大小的值，默认为1.0f  
 * 
 * 需要手机root， 或者说app具有系统权限  否则无效，报权限异常
 */
public class SystemFontActivity extends Activity {

	private Configuration mconfig = new Configuration();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.system_font);
		
	}
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_small:
			mconfig.fontScale = 0.75f;
			break;
		case R.id.btn_normal:
			mconfig.fontScale = 1.0f;
			break;
		case R.id.btn_larger:
			mconfig.fontScale = 1.25f;
			break;
		case R.id.btn_largest:
			mconfig.fontScale = 1.5f;
			break;

		default:
			break;
		}
		setFontSize();
	}

	/**
	 * 反射修改android系统字体大小
	 */
	public void setFontSize() {
		Method method;
		try {
			Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
			try {
				Object am = activityManagerNative.getMethod("getDefault").invoke(activityManagerNative);
				method = am.getClass().getMethod("updateConfiguration", android.content.res.Configuration.class);
				method.invoke(am, mconfig);// 设置字体大小的方法就是updateConfiguration(Configuration config);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
