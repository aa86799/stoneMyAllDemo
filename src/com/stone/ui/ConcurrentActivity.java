package com.stone.ui;

import android.app.Activity;
import android.os.Bundle;

import com.stone.concurrent.SerialThreadPool;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/5/13 17 16
 */
public class ConcurrentActivity extends Activity {
    static int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SerialThreadPool threadPool = new SerialThreadPool();
        final ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < 10; i++) {
            deque.offer(i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("主线程" + i);
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(deque.poll()+"cname = " + Thread.currentThread().getName());
                }
            });
        }
    }
}
