package com.stone.concurrent;

import java.util.ArrayDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 顺序线程 (与AsyncTask中内部实现的一致)
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/5/12 11 22
 */
public class SerialThreadPool implements Executor {

    private ArrayDeque<Runnable> mDeque;
    private Runnable mActive;
    private MyThreadPool mThreadPool;

    public SerialThreadPool() {
        mThreadPool = new MyThreadPool();
        mDeque = new ArrayDeque<Runnable>();
    }

    @Override
    public void execute(final Runnable command) {
        //offer 加载到队尾  未触发
        mDeque.offer(new Runnable() {
            @Override
            public void run() {
                command.run();
                scheduleNext();
            }
        });

        if (mActive == null) {//mActive当前活动 runnable  这个只会执行一次
            scheduleNext();
        }
    }

    /**
     * 顺序 获取需要执行的Runnable对象， 并执行它
     */
    protected synchronized void scheduleNext() {
        if ((mActive = mDeque.poll()) != null) {//先进先出 poll内部调用 pollFirst
            mThreadPool.execute(mActive);
        }

    }
}
