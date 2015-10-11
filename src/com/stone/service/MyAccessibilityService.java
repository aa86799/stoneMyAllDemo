package com.stone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/23 16 08
 */
public class MyAccessibilityService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
