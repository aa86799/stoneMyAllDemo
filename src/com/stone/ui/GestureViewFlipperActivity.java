package com.stone.ui;

import com.stone.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

//ViewFlipper 和手势 左右滑动 切换 Activity
public class GestureViewFlipperActivity extends Activity implements OnGestureListener{
	ViewFlipper flipper; //一次显示一个子view
	GestureDetector detector; //手势监测器
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		detector = new GestureDetector(getApplicationContext(), this);
		setContentView(R.layout.flipper);
		flipper = (ViewFlipper) findViewById(R.id.vf_flipper);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);//由检测器 执行
	}
	
	/*以下为 OnGestureListener 的方法*/
	@Override 
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() > 120) {//向左滑，右边显示
			//this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
			//this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out)); 
			this.flipper.showNext(); 
		}
		if (e1.getX() - e2.getX() < -120) {//向右滑，左边显示
			this.flipper.showPrevious(); 
		}
		return false;
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		
	}
	@Override //单击
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}
	@Override // 长按
	public void onLongPress(MotionEvent e) {
		
	}
	
}
