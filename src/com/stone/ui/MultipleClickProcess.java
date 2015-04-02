package com.stone.ui;

import com.stone.util.LogUtils;

import android.view.View;
import android.view.View.OnClickListener;



/**
 * 	处理在短时间内多次点击同一组件，界面异常
 */
public class MultipleClickProcess implements OnClickListener{
	private boolean flag = true;
	private synchronized void setFlag() {
		flag = false;
	}
	public void onClick(View view) {
		if (flag) {
			LogUtils.printInfo("", "点击了一下");
			setFlag();
			// do some things
			new TimeThread().start();
		}
	}
	/** 
	 * 计时线程（防止在一定时间段内重复点击按钮） 
	 */ 
	private class TimeThread extends Thread {  
		public void run() {
			try {
				Thread.sleep(2000);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
