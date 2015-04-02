package com.stone.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 承载widget的容器
 */
public class WidgetLayout extends ViewGroup {
	//存放touch的坐标
	private int[] cellInfo = new int[2];
	private OnLongClickListener mLongClickListener;	

	public WidgetLayout(Context context) {
		super(context);
		mLongClickListener = new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				return false;
			}
		};
	}
	
	public void addInScreen(View child, int width, int height) {
		LayoutParams lp = new LayoutParams(width, height);
		lp.x = cellInfo[0];
		lp.y = cellInfo[1];
		child.setOnLongClickListener(mLongClickListener);
		addView(child, lp);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		LayoutParams lp;
		for(int index=0; index<getChildCount(); index++) {
			lp = (LayoutParams) getChildAt(index).getLayoutParams();
			getChildAt(index).measure(
					MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, lp.width), 
					MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, lp.height));
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		cellInfo[0] = (int)event.getX();
		cellInfo[1] = (int)event.getY();
		return super.dispatchTouchEvent(event);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		LayoutParams lp;
		for(int index=0; index<getChildCount(); index++) {
			lp = (LayoutParams) getChildAt(index).getLayoutParams();
			getChildAt(index).layout(lp.x, lp.y, lp.x+lp.width, lp.y+lp.height);
		}
	}
	
	public static class LayoutParams extends ViewGroup.LayoutParams {
		int x;
		int y;

		public LayoutParams(int width, int height) {
			super(width, height);
		}		
	}
}
