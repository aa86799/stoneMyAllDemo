package com.stone.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.R;

public class ToastUtil {
	private static ToastUtil util;
	private TextView toastContent;
	private Toast toast;
	
	public static ToastUtil getInstance() {
		if (util == null) {
			util = new ToastUtil();
		}
		return util;
	}
	/**
	 * 初始化 Toast，
	 * @param context
	 * @param inflater
	 */
	public void initToastView(Context context, LayoutInflater inflater) {
		toast = new Toast(context);
		View toastLayout = inflater.inflate(R.layout.toast, null);
		toast.setView(toastLayout);
		toastContent = (TextView) toastLayout.findViewById(R.id.tv_toast);
		toast.setGravity(Gravity.CENTER, 0, 0);
	}
	/**
	 * 显示 Toast
	 * @param msg
	 */
	public void showToast(String msg) {
		toastContent.setText(msg);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}
}
