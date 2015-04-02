package com.stone.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

public class VelocityTrackerActi extends Activity {
	private TextView mInfo;

	private VelocityTracker mVelocityTracker;
	private int mMaxVelocity;

	private int mPointerId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mInfo = new TextView(this);
		mInfo.setLines(4);
		mInfo.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		mInfo.setTextColor(Color.WHITE);

		setContentView(mInfo);

//		mMaxVelocity = ViewConfiguration.get(this).getMaximumFlingVelocity();
		mMaxVelocity = ViewConfiguration.get(this).getScaledMaximumFlingVelocity();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		acquireVelocityTracker(event);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 求第一个触点的id， 此时可能有多个触点，但至少一个
			mPointerId = event.getPointerId(0);
			break;

		case MotionEvent.ACTION_MOVE:
			// 求伪瞬时速度
			mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
			final float velocityX = mVelocityTracker.getXVelocity(mPointerId);
			final float velocityY = mVelocityTracker.getYVelocity(mPointerId);
			recodeInfo(velocityX, velocityY);
			break;

		case MotionEvent.ACTION_UP:
			releaseVelocityTracker();
			break;

		case MotionEvent.ACTION_CANCEL:
			releaseVelocityTracker();
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 
	 * @param event
	 *            向VelocityTracker添加MotionEvent
	 * 
	 * @see android.view.VelocityTracker#obtain()
	 * @see android.view.VelocityTracker#addMovement(MotionEvent)
	 */
	private void acquireVelocityTracker(final MotionEvent event) {
		if (null == mVelocityTracker) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * 释放VelocityTracker
	 * 
	 * @see android.view.VelocityTracker#clear()
	 * @see android.view.VelocityTracker#recycle()
	 */
	private void releaseVelocityTracker() {
		if (null != mVelocityTracker) {
			mVelocityTracker.clear();
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}

	private static final String sFormatStr = "velocityX=%f\nvelocityY=%f";

	/**
	 * 记录当前速度
	 * 
	 * @param velocityX
	 *            x轴速度
	 * @param velocityY
	 *            y轴速度
	 */
	private void recodeInfo(final float velocityX, final float velocityY) {
		final String info = String.format(sFormatStr, velocityX, velocityY);
		mInfo.setText(info);
	}
}
