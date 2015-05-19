package com.stone.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.R;
import com.stone.eventbus.EventBusTest;

import de.greenrobot.event.EventBus;


/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/5/14 10 39
 */
  /*
使用注意：
  1. 在register(Object subscription) 注册的subscription对象中 定义onEvent... 方法，至少需要定义一种
  2. 如果所有的onEvent... 方法都注册 那么 都将收到消息(内部机制就是反射判断有哪些方法，从消息队列中取event)
  3. 实际使用时 只要保留一种onEvent... 方法即可。 如onEventAsync， 小心ANR
  4. 在代码混淆时，注意保留onEvent... 方法
     */

public class EventBusActivity extends Activity {

    private TextView mTVContent;

    EventBusTest test = new EventBusTest(true);//收集事件 存在内部集合中
//  EventBusTest test = new EventBusTest();// 默认false 不收集事件;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.eventbus);

        test.register(test); //注册 subscription

        EventBus.getDefault().register(this);
    }

    public void eventBusEvent(View view) {
        test.testPostInCurrentThread();
    }

    //两个activity中使用eventBus
    public void eventBusEventWith2Acti(View view) {
        startActivity(new Intent(this, EventBusActivity2.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        test.unregister(test);

        EventBus.getDefault().unregister(this);
    }

    public void onEvent(Object event) {
        Looper.prepare();
        Toast.makeText(this, "recevie a event:" + event + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

}
