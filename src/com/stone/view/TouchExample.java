package com.stone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
/**
 * 神兽保佑－代码无BUG
	    ┏┓　　　┏┓
	   ┏┛┻━━━┛┻┓
	   ┃　　　　　　　┃ 　
	   ┃　　　━　　　┃
	   ┃　┳┛　┗┳　┃
	   ┃　　　　　　　┃
	   ┃　　　┻　　　┃
	   ┃　　　　　　　┃
	   ┗━┓　　　┏━┛
     	  ┃　　　┃   神兽保佑　　　　　　　　
     	  ┃　　　┃   代码无BUG！
     	  ┃　　　┗━━━┓
      	  ┃　　　　　　　┣┓
      	  ┃　　　　　　　┏┛
      	  ┗┓┓┏ ━┳┓┏┛
            ┃┫┫　 ┃┫┫
            ┗┻┛　 ┗┻┛ 
 * @author stone
 *
 */
public class TouchExample extends View {
	private Paint paint;
	private float fontSize;
	private float dx;
	private float dy = 100;
	
	private float scale = 1.0f;//绽放比率
	private GestureDetector gestureDetector; //手势探测器
	private ScaleGestureDetector scaleGestureDetector;//比率手势探测器
	
	public TouchExample(Context context) {
		super(context);
		fontSize = 16 * getResources().getDisplayMetrics().density;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(fontSize);
		
		gestureDetector = new GestureDetector(context, new ZoomGesture());
		scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGesture());
		
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		String text = "全力以赴";
		canvas.drawText(text, dx, dy, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//单点触摸 跟随
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN: //按下 = 0
//			dx = event.getX();
//			dy = event.getY();
//			invalidate();
//			break;
//		case MotionEvent.ACTION_MOVE://移动 = 2
//			
//			break;
//		case MotionEvent.ACTION_UP:// 抬起 = 1
//			
//			break;
//		default:
//			break;
//		}
		
		//单点双击 缩放
		gestureDetector.onTouchEvent(event);
		//双指 缩放
		scaleGestureDetector.onTouchEvent(event);
		return true;
		
		//多点时获取坐标示例
//		int pointerCount = event.getPointerCount();
////		switch (event.getAction() & MotionEvent.ACTION_MASK) {
//		switch (event.getActionMasked()) {
//		case MotionEvent.ACTION_DOWN:
//		case MotionEvent.ACTION_POINTER_DOWN:
//		case MotionEvent.ACTION_MOVE:
//			for (int i = 0; i < pointerCount; i++) {
//				int id = event.getPointerId(i); //同一点的id值保持不变
//				int index = i;//同一点的索引值，是可变的，当由多指逐渐减少时，index--
//				 dx = (int) event.getX(index);
//				 dy  = (int) event.getY(index);
//			}
//			invalidate();
//			break;
//		case MotionEvent.ACTION_CANCEL:
//
//		default:
//			break;
//		}
//		
//		return true;
		
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		return super.dispatchTouchEvent(event);
	}
	
	//SimpleOnGestureListener implements OnGestureListener, OnDoubleTapListener
	class ZoomGesture extends GestureDetector.SimpleOnGestureListener {//单手指操作
		private boolean normal = true;
		
		@Override //双击
		public boolean onDoubleTap(MotionEvent e) {
			System.out.println("--onDoubleTap---");
			scale = normal ? 3f : 1f;
			paint.setTextSize(fontSize * scale);
			normal = !normal;
			invalidate();
			return true;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			System.out.println("--onDoubleTapEvent---");
			return super.onDoubleTapEvent(e);
		}
	}
	
	//SimpleOnScaleGestureListener implements OnScaleGestureListener
	class ScaleGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener {//双手指操作

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			detector.getCurrentSpan();//两点间的距离跨度
			detector.getCurrentSpanX();//两点间的x距离
			detector.getCurrentSpanY();//两点间的y距离
			detector.getFocusX();		//
			detector.getFocusY();		//
			detector.getPreviousSpan();	//上次
			detector.getPreviousSpanX();//上次
			detector.getPreviousSpanY();//上次
			detector.getEventTime();	//当前事件的事件
			detector.getTimeDelta();    //两次事件间的时间差
			detector.getScaleFactor();  //与上次事件相比，得到的比例因子
			System.out.println(detector.getEventTime());
			scale *= detector.getScaleFactor();
			paint.setTextSize(fontSize * scale);
			invalidate();
			return true;
		}
		
	}
}
