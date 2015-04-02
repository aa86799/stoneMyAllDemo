package com.stone.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {

	private MyRenderer mRenderer;

	public MyGLSurfaceView(Context context) {
		super(context);
		mRenderer = new MyRenderer();
		setRenderer(mRenderer);
	}

	public MyGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			final float x = event.getX() / getWidth();
			final float y = event.getY() / getHeight();
			queueEvent(new Runnable() {
				
				@Override
				public void run() {
					mRenderer.setColor(x, y, x + y);
				}
			});
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	
}
