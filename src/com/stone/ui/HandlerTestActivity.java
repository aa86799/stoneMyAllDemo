package com.stone.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;

public class HandlerTestActivity extends Activity {
	HandlerThread handlerThread = new HandlerThread("test");
	Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("cur_id="+Thread.currentThread().getId());
		
		handlerThread.start();
		System.out.println("handlerThread.id=" + handlerThread.getId());
		//post(runnable)，只是直接运行了run()，run()内ThreadId与UIThread是一样的
//		handler = new Handler(); 
		//post(runnable)，将runnable运行在handlerThread中，这是非UIThread的
		handler = new Handler(handlerThread.getLooper(), new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				System.out.println("receive message.whatA=" + msg.what);
				if (msg.what == 1) {
					return true;//不再向外层传递
				} else {
					return false; //外层的handleMessage() 继续执行
				}
				
			}
		}) {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				System.out.println("receive message.whatB=" + msg.what);
			}
		};
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("handler_post_cur_id="+Thread.currentThread().getId());
				handler.sendEmptyMessage(1);
				handler.sendEmptyMessage(2);
			}
		});
		
	}
}
