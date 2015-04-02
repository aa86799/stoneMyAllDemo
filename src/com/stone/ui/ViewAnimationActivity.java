package com.stone.ui;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.stone.R;

/*
 * 视图使用的动画 即 Tween Animation
 * 包括translate rotate alpha scale 和 set,set由前面四种动画组合而成
 */
public class ViewAnimationActivity extends Activity {
	ImageView imageview;
	ImageView imageview_translate;
	ImageView imageview_rotate;
	ImageView imageview_alpha;
	ImageView imageview_scale;
	ImageView imageview_set;
	
	AnimationDrawable animationDrawable;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.animation_main);
		imageview = (ImageView) findViewById(R.id.imageview);
		imageview_translate = (ImageView) findViewById(R.id.imageview_translate);
		imageview_rotate = (ImageView) findViewById(R.id.imageview_rotate);
		imageview_alpha = (ImageView) findViewById(R.id.imageview_alpha);
		imageview_scale = (ImageView) findViewById(R.id.imageview_scale);
		imageview_set = (ImageView) findViewById(R.id.imageview_set);
		
		
//		imageview.setBackgroundResource(R.drawable.animationDrawable);
//		animationDrawable = (AnimationDrawable) imageview.getBackground();
		animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_drawable);
		imageview.setBackground(animationDrawable);
		
	}


	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			animationDrawable.start();
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	@Override //window焦点改变
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			//start不能直接用在onCreate中，因为此时，AnimationDrawable还没有完全在window中建立好。
			animationDrawable.start();
			
			Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate);
			imageview_translate.setBackground(getResources().getDrawable(R.drawable.a11));
			imageview_translate.startAnimation(translate);
			
			Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
			imageview_rotate.setBackground(getResources().getDrawable(R.drawable.a11));
			imageview_rotate.startAnimation(rotate);
			
			Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
			imageview_alpha.setBackground(getResources().getDrawable(R.drawable.a11));
			imageview_alpha.startAnimation(alpha);
			
			Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
			imageview_scale.setBackground(getResources().getDrawable(R.drawable.a11));
			imageview_scale.startAnimation(scale);
			
//			Animation set = AnimationUtils.loadAnimation(this, R.anim.set);
//			imageview_set.setBackground(getResources().getDrawable(R.drawable.a11));
//			imageview_set.startAnimation(set);
			
//			AnimationSet animationSet = new AnimationSet(true);
//			animationSet.addAnimation(scale);
//			animationSet.addAnimation(translate);
//			animationSet.addAnimation(alpha);
//			animationSet.addAnimation(rotate);
//			animationSet.setDuration(2000);
//			animationSet.setRepeatCount(50);
////			animationSet.setRepeatMode(Animation.RESTART);
//			animationSet.setRepeatMode(Animation.REVERSE);
//			imageview_set.setBackground(getResources().getDrawable(R.drawable.a11));
//			imageview_set.startAnimation(animationSet);
			
			TranslateAnimation translateAnimation;
			RotateAnimation rotateAnimation;
			AlphaAnimation alphaAnimation;
			ScaleAnimation scaleAnimation;
//			Animation.RELATIVE_TO_SELF	相对于自身
//			Animation.RELATIVE_TO_PARENT 相对于父View
			
			scale.setAnimationListener(new AnimationListener() {
				
				@Override //动画开始
				public void onAnimationStart(Animation animation) {
					
				}
				
				@Override //动画重复
				public void onAnimationRepeat(Animation animation) {
					
				}
				
				@Override //动画结束
				public void onAnimationEnd(Animation animation) {
//					OvershootInterpolator
				}
			});
		}
	}
}
