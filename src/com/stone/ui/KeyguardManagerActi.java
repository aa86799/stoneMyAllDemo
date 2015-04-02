package com.stone.ui;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.os.Bundle;
import android.util.Log;

/*
 * Keyguard 键盘锁
 * android.app.KeyguardManager类用于对Keyguard进行管理，即对锁屏进行管理
 */
public class KeyguardManagerActi extends Activity {
	KeyguardManager mKeyguardManager;
	KeyguardLock mLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//		mLock = mKeyguardManager.newKeyguardLock(getLocalClassName());
//		mLock.disableKeyguard(); // 解除锁屏
//		mLock.reenableKeyguard();
		
//		test();
	}

	void test() {
		KeyguardManager.OnKeyguardExitResult keyguardExitResultListener = new KeyguardManager.OnKeyguardExitResult() {
			@Override
			public void onKeyguardExitResult(boolean success) {
				
			}
		};
		mKeyguardManager.exitKeyguardSecurely(keyguardExitResultListener);
		KeyguardManager.KeyguardLock keyguardLock = mKeyguardManager
				.newKeyguardLock(getLocalClassName());
		for (int i = 10; i > 0; i--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		keyguardLock.disableKeyguard();
		for (int i = 20; i > 0; i--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long t = System.currentTimeMillis();
		keyguardLock.reenableKeyguard();
	}
}
