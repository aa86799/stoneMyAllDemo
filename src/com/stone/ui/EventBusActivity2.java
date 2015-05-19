package com.stone.ui;

import android.app.Activity;
import android.os.Bundle;

import de.greenrobot.event.EventBus;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/5/19 19 15
 */
public class EventBusActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread() {
            @Override
            public void run() {
                EventBus.getDefault().post(this.getClass().getSimpleName() + " 发了一个消息");
            }
        }.start();
        finish();
    }
}
