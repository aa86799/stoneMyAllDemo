package com.stone.eventbus;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/5/19 16 39
 */
public class EventBusTest extends AbstractEventBus<String> {

    public EventBusTest() {
    }

    public EventBusTest(boolean collectEventsReceived) {
        super(collectEventsReceived);
    }

    public void testPostInCurrentThread() {
        eventBus.post("Hello, " + this.getClass().getSimpleName());
    }

    public void testPostFromMain() {
        postInMainThread("Hello testPostFromMain");
    }

    @Override
    public void onEvent(String event) {
       /* trackEvent(event); //存储事件相关信息
        System.out.println("onEvent:lastEvent==>" + super.lastEvent);
        System.out.println("onEvent:lastThread==>" + super.lastThread);
        System.out.println("onEvent:eventCount==>" + super.eventCount);
        if (super.eventsReceived != null) {
            for (String ev: super.eventsReceived) {
                System.out.println("onEvent:eventsReceived==>" + ev);
            }
        }*/
    }

    @Override
    public void onEventMainThread(String event) {
//        trackEvent(event); //存储事件相关信息
    }

    public void onEventBackgroundThread(String event) {
//        trackEvent(event); //存储事件相关信息
    }

    @Override
    public void onEventAsync(String event) {
        trackEvent(event); //存储事件相关信息
        System.out.println("onEventAsync:lastEvent==>" + super.lastEvent);
        System.out.println("onEventAsync:lastThread==>" + super.lastThread);
        System.out.println("onEventAsync:eventCount==>" + super.eventCount);
        if (super.eventsReceived != null) {
            for (String ev : super.eventsReceived) {
                System.out.println("onEventAsync:eventsReceived==>" + ev);
            }
        }
    }

}
