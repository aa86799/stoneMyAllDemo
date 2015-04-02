package com.stone.util;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.stone.R;

public class DialogUtil {
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, 0).show();
	}

	/**
	 * 显示提示框
	 */
	public static ProgressDialog getProgressDialog(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(context.getString(R.string.progress_title));
		progressDialog.setMessage(context.getString(R.string.progress_message));
		return progressDialog;
	}

	/**
	 * 关闭提示框
	 */
	public static void closeProgressDialog(ProgressDialog dialog) {
		if (dialog != null) {
			dialog.dismiss();
		}
	}
	
	public static void showInfoDialog(Context context, String message){
		showInfoDialog(context, message, "提示", "确定", null);
	}
	/**
	 * 
	 * @param context
	 * @param message
	 * @param titleStr
	 * @param positiveStr  确定文本
	 * @param onClickListener 确定click listener
	 */
	public static void showInfoDialog(Context context, String message, String titleStr, String positiveStr,
			DialogInterface.OnClickListener onClickListener){
		 AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		 localBuilder.setTitle(titleStr);
		 localBuilder.setMessage(message);
		 if(onClickListener == null) {
			 onClickListener = new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			};
		 }
		 localBuilder.setPositiveButton(positiveStr, onClickListener);
		 localBuilder.show();
	}
	/**
	 * 
	 * @param context
	 * @param message
	 * @param titleStr
	 * @param positiveStr  确定
	 * @param onClickListener 确定 click Listener
	 * @param negativeStr 	否定
	 */
	public static void showInfoDialog(Context context, String message, String titleStr, String positiveStr,
			DialogInterface.OnClickListener onClickListener, String negativeStr){
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if(onClickListener == null) {
			onClickListener = new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			};
		}
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		
		localBuilder.setNegativeButton(negativeStr, null);
		localBuilder.show(); //测试下该方法
	}
}
