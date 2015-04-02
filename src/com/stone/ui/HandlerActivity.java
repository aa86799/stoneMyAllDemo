package com.stone.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class HandlerActivity extends Activity {
	Handler handlerMain = new Handler() {
		public void handleMessage(android.os.Message msg) {
			System.out.println("----main----handler----");
			System.out.println("thread.id" + Thread.currentThread().getId());
		};
	};
	Handler handlerSub;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new Thread() {
			public void run() {
				Looper.prepare();
				handlerSub = new Handler() {
					public void handleMessage(android.os.Message msg) {
						System.out.println("----sub----handler----");
						System.out.println("thread.id" + Thread.currentThread().getId());
					};
				};
				handlerSub.sendEmptyMessage(0);
				Looper.loop();
			};
		}.start();
		handlerMain.sendEmptyMessage(0);
		handlerMain.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("handlerMain.postRunnable");
				System.out.println("thread.id" + Thread.currentThread().getId());
				handlerSub.sendEmptyMessage(0);
				handlerSub.post(new Runnable() {
					
					@Override
					public void run() {
						System.out.println("handlerSub.postRunnable");
						System.out.println("thread.id" + Thread.currentThread().getId());
					}
				});
			}
		}, 1000);
	}
}	
