package com.stone.concurrent;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/5/12 11 42
 */
public class MyThreadPool {

    private static ExecutorService mThreadPool;
    private static  Handler mHandler = new Handler(Looper.getMainLooper());
    private static final int aaa  =0;

    public MyThreadPool() {
        this(null);
    }

    public MyThreadPool(ExecutorService threadPool) {
        if (mThreadPool != null) {
            shutdownNow();
        }
        if (threadPool == null) {
            mThreadPool = Executors.newCachedThreadPool();
        } else {
            this.mThreadPool = threadPool;
        }
    }

    public static synchronized void shutdownNow() {
        if (mThreadPool != null && !mThreadPool.isShutdown()) {
            mThreadPool.shutdownNow();
            mThreadPool = null;
        }
    }

    public void execute(Runnable command) {
        //使用线程池中空闲线程来执行  空闲线程是随机取的
        mThreadPool.execute(command);
    }

    public <T> FutureTask<T> execute(final Worker<T> worker) {
        Callable<T> callable = new Callable<T>() {
            @Override
            public T call() throws Exception {
                return worker.doInBackground();
            }
        };
        FutureTask<T> task = new FutureTask<T>(callable) {
            @Override
            protected void done() {
                super.done();
                try {
                    T data = get();
                    postExecute(worker, data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    worker.abort();
                    postCancel(worker);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    throw new RuntimeException("An error occured while executing doInBackground()");
                }
            }
        };
        mThreadPool.execute(task);
        return task;
    }

    public <T> FutureTask<T> execute(Callable<T> command) {
        FutureTask<T> task = new FutureTask<T>(command);
        mThreadPool.submit(task);
        return task;
    }

    private void postCancel(final Worker worker) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                worker.onCanceled();
            }
        });
    }

    private <T> void postExecute(final Worker<T> worker, final T result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                worker.onPostExecute(result);
            }
        });
    }



    public static abstract class Worker<T> {
        protected abstract T doInBackground();

        protected void onPostExecute(T data) {}

        protected void onCanceled() {}

        protected void abort() {}
    }
}