package com.stone.view;
import java.util.Calendar;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 自定义DigitalClock输出格式
 * 
 *
 */
public class DigitalClock extends android.widget.DigitalClock {
	/**倒计时*/
	private void countdown() {
		/*
		 * 倒计时  距离1分钟后的倒计时
		 */
		final long interval_total = 1 * 60 * 1000; //间隔总时间
		final long interval_scale = 1000; //刷新的间隔单位时间
		final long destTime = System.currentTimeMillis() + interval_total; //结束的目标系统时间： 这里可能年月日时分秒毫秒等 有变化
		new CountDownTimer(interval_total, interval_scale) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(destTime);//设置目标时间为Calendar对象
				/*
				 * 时、分、秒、毫秒 归0
				 */
				c.set(Calendar.HOUR, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				//当前毫秒值 加 余下的毫秒值 
				c.setTimeInMillis(c.getTimeInMillis() + millisUntilFinished);
				
				DateFormat format = new DateFormat();
				Log.i("倒计时", format.format("HH时:mm分:ss秒", c).toString());
			}
			@Override
			public void onFinish() {
				Log.i("倒计时", "finish");
			}
		}.start();
	}
    Calendar mCalendar;
    private final static String m12 = "h:mm:ss aa";//h:mm:ss aa
    private final static String m24 = "H:mm:ss";//H:mm:ss
    private FormatChangeObserver mFormatChangeObserver;

    private Runnable mTicker;
    private Handler mHandler;

    private boolean mTickerStopped = false;

    String mFormat;

    public DigitalClock(Context context) {
        super(context);
        initClock(context);
    }

    public DigitalClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClock(context);
    }

    private void initClock(Context context) {
        Resources r = context.getResources();

        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }

        mFormatChangeObserver = new FormatChangeObserver();
        getContext().getContentResolver().registerContentObserver(
                Settings.System.CONTENT_URI, true, mFormatChangeObserver);

        setFormat();
        
    }

    @Override
    protected void onAttachedToWindow() {
        mTickerStopped = false;
        super.onAttachedToWindow();
        mHandler = new Handler();

        /**
         * requests a tick on the next hard-second boundary
         */
        mTicker = new Runnable() {
                public void run() {
                    if (mTickerStopped) return;
                    mCalendar.setTimeInMillis(System.currentTimeMillis());
                    setText(DateFormat.format(mFormat, mCalendar));
                    invalidate();
                    long now = SystemClock.uptimeMillis();
                    long next = now + (1000 - now % 1000);
                    mHandler.postAtTime(mTicker, next);
                }
            };
        mTicker.run();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTickerStopped = true;
    }

    /**
     * Pulls 12/24 mode from system settings
     */
    private boolean get24HourMode() {
        return android.text.format.DateFormat.is24HourFormat(getContext());
    }

    private void setFormat() {
        if (get24HourMode()) {
            mFormat = m24;
        } else {
            mFormat = m12;
        }
    }

    private class FormatChangeObserver extends ContentObserver {
        public FormatChangeObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean selfChange) {
            setFormat();
        }
    }
}