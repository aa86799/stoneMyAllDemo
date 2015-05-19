package com.stone.eventbus;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import de.greenrobot.event.EventBus;

public abstract class AbstractEventBus<T> {

    protected EventBus eventBus;

    protected final AtomicInteger eventCount = new AtomicInteger();//原子性 事件数量
    protected final List<T> eventsReceived; //接收到的事件 list

    protected volatile T lastEvent;//最后、上次 收到的事件
    protected volatile Thread lastThread;//最后、上次 线程

    private EventPostHandler mainPosterHandler;

    @SuppressLint("HandlerLeak")
    class EventPostHandler extends Handler {
        public EventPostHandler(Looper looper) {
            super(looper);
        }

        @Override //handler收到msg后，用eventbus.post(msg.obj)
        public void handleMessage(Message msg) {
            eventBus.post(msg.obj);
        }

        private void post(T event) {
            sendMessage(obtainMessage(0, event));//handler.sendMessage
        }

    }

    public AbstractEventBus() {
        this(false);
    }

    /**
     * @param collectEventsReceived 是否收集 已接收到的事件
     */
    public AbstractEventBus(boolean collectEventsReceived) {
        if (collectEventsReceived) {
            //初始化 事件收集list  CopyOnWriteArrayList是线程安全的list
            eventsReceived = new CopyOnWriteArrayList<T>();
        } else {
            eventsReceived = null;
        }
        init();
    }

    protected void init() {
        EventBus.clearCaches(); //清除缓存
        eventBus = new EventBus(); //new方法的内部实现还是 单例 跟以下没有区别
//        eventBus = EventBus.builder().build();
//        eventBus = EventBus.getDefault();

        mainPosterHandler = new EventPostHandler(Looper.getMainLooper());
//        assertFalse(Looper.getMainLooper().getThread().equals(Thread.currentThread()));
    }

    protected void postInMainThread(T event) {
        mainPosterHandler.post(event);
    }

    /**
     * 追踪事件: 赋值事件、线程、事件数量
     * @param event
     */
    protected void trackEvent(T event) {
        lastEvent = event;
        lastThread = Thread.currentThread();
        if (eventsReceived != null) {
            eventsReceived.add(event); //list.add
        }
        // Must the the last one because we wait for this
        eventCount.incrementAndGet();

    }


    
    protected void assertEventCount(int expectedEventCount) {
//        assertEquals(expectedEventCount, eventCount.intValue());
    }
    
    protected void countDownAndAwaitLatch(CountDownLatch latch, long seconds) {
        latch.countDown();
        awaitLatch(latch, seconds);
    }

    protected void awaitLatch(CountDownLatch latch, long seconds) {
        try {
            latch.await(seconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(Object subscription) {
        /*
        在哪里注册 就表示 在哪里订阅，即在哪里实现onEventXxx方法
         */
        this.eventBus.register(subscription);
    }

    public void unregister(Object subscription) {
        this.eventBus.unregister(subscription);
    }

    public void onEvent(T event){}
    public void onEventMainThread(T event){}
    public void onEventBackgroundThread(T event){}
    //通常异步处理 所以 abstract
    public abstract void onEventAsync(T event);

}
