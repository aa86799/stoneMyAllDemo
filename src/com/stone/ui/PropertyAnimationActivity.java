package com.stone.ui;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.Animator.AnimatorPauseListener;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.R;
/*
 * View Animation是比较旧的一套系统，仅仅适用于View对象。
　　并且View Animation系统限制了可以动画的方面，比如缩放和旋转是可以的，但是背景颜色的动画是做不了的。
　　View Animation系统的另一个缺陷就是它仅仅改变了View绘制的位置，并没有改变View本身实际的位置。
　　比如，如果你让一个按钮通过动画移动到屏幕上的另一个位置，虽然它绘制在目标位置，但是你要点击它还是要在原来的位置，所以你需要自己写逻辑去处理这种问题。
　　Property animation系统就不存在上面的问题，它是确实地改变了View对象的真实属性。
	从3.0起可以使用
	包含：ValueAnimator 及其子类 ObjectAnimator
 */
public class PropertyAnimationActivity extends Activity {
	ImageView imageview;
	ImageView imageview_translate;
	ImageView imageview_rotate;
	ImageView imageview_alpha;
	ImageView imageview_scale;
	ImageView imageview_set;
	LinearLayout ll_animation;
	private static final int RED = 0xffFF8080;
	private static final int BLUE = 0xff8080FF;
	private static final int CYAN = 0xff80ffff;
	private static final int GREEN = 0xff80ff80;
	
	TextView tv_num;
	
	ObjectAnimator translateAnimator;
	ObjectAnimator scaleAnimator;
	AnimatorSet animatorSet;
	ObjectAnimator alphaAnimator;
	AnimatorSet animatorSet2;
	ObjectAnimator rotationYAni;
	ViewPropertyAnimator animate;
	ObjectAnimator animator;
	ValueAnimator valueAnimator;
      
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
		ll_animation = (LinearLayout) findViewById(R.id.ll_animation);
		tv_num = (TextView) findViewById(R.id.tv_num);
		 
	}
	
	public void setValueAnimator() {
		/*	ValueAnimator是所有属性动画的基类 需要一个Interpolator和一个TypeEvaluator
		 * 		来计算属性值
		 * 	默认的interpolator 是一个加速-减速
		 */
		ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
		animator.setDuration(2000);
		
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		
		imageview_translate.setBackground(getResources().getDrawable(R.drawable.a11));
		translateAnimator = ObjectAnimator.ofFloat(imageview_translate, "translationX", 100);
//		imageview_translate.setTranslationX(translationX);
//		imageview_translate.setTranslationY(translationY);
		translateAnimator.setPropertyName("translationY");//一个Animator只能设置一个属性，这里后设置的会将构造里的属性替换掉
		translateAnimator.setFloatValues(100);
		translateAnimator.setDuration(2000);
		translateAnimator.setRepeatCount(50);
		translateAnimator.setRepeatMode(Animation.REVERSE);
		
//		translateAnimator.addPauseListener(new AnimatorPauseListener() {
//			
//			@Override
//			public void onAnimationResume(Animator animation) {
////				animation.pause();
////				System.out.println("----pause----");
//			}
//			
//			@Override
//			public void onAnimationPause(Animator animation) {
//				animation.resume();
//				System.out.println("----resume----");
//			}
//		});
		
//		imageview_translate.postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				if (translateAnimator.isRunning()) {
//					translateAnimator.pause();
//				}
//			}
//		}, 2000);
//		translateAnimator.start();
		
		imageview_scale.setBackground(getResources().getDrawable(R.drawable.a11));
		scaleAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.scale_object_animator);
		scaleAnimator.setTarget(imageview_scale);//设置动画作用的目标对象
		scaleAnimator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				
			}
		});
		scaleAnimator.setDuration(1000);
		scaleAnimator.setRepeatCount(50);
		scaleAnimator.start();

		imageview_rotate.setLayerType(View.LAYER_TYPE_HARDWARE, null);//硬件加速启用
		imageview_rotate.setBackground(getResources().getDrawable(R.drawable.a11));
		animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.hide_animator);//set_rotate_scale
		animatorSet.setTarget(imageview_rotate);
		animatorSet.setDuration(1000);
		animatorSet.addListener(new AnimatorListenerAdapter() {//空实现了AnimatorListener

			@Override
			public void onAnimationCancel(Animator animation) {
				super.onAnimationCancel(animation);
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				imageview_rotate.setLayerType(View.LAYER_TYPE_NONE, null);//设置图层加速类型为空
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				super.onAnimationRepeat(animation);
			}

			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
			}

			@Override
			public void onAnimationPause(Animator animation) {
				super.onAnimationPause(animation);
			}

			@Override
			public void onAnimationResume(Animator animation) {
				super.onAnimationResume(animation);
			}
			
		});
//		animatorSet.setInterpolator(new BounceInterpolator());//设置end时的弹跳插入器
		animatorSet.start();
		
		imageview_alpha.setBackground(getResources().getDrawable(R.drawable.a11));
		alphaAnimator = ObjectAnimator.ofFloat(imageview_alpha, "alpha", 1f, 0f);
		alphaAnimator.setDuration(2000);
		alphaAnimator.setRepeatCount(50);
		alphaAnimator.setRepeatMode(Animation.REVERSE);
		alphaAnimator.start();
		
		imageview_set.setBackground(getResources().getDrawable(R.drawable.a11));
//		AnimatorSet animatorSet2 = new AnimatorSet();
//		animatorSet2.playSequentially(//顺序执行以下动画
//				ObjectAnimator.ofFloat(imageview_set, "alpha", 0f, 1f).setDuration(1000),
//				ObjectAnimator.ofFloat(imageview_set, "translationX", 100, 200f),
//				ObjectAnimator.ofFloat(imageview_set, "translationY", 100f, 200f)
//				);
//		animatorSet2.setDuration(3000);//在顺序执行时，内部动画各运行3秒，按序执行需要9秒。内部动画设置的时间被set设置的覆盖
//		animatorSet2.start();
		
		animatorSet2 = new AnimatorSet();
		rotationYAni = ObjectAnimator.ofFloat(imageview_set, "rotationX", 0f, 60f);
		rotationYAni.setDuration(1000);
		rotationYAni.setRepeatCount(50);
		rotationYAni.setRepeatMode(Animation.REVERSE);
//		rotationYAni.addUpdateListener(new AnimatorUpdateListener() {//监听每一帧的更新
//			
//			@Override
//			public void onAnimationUpdate(ValueAnimator animation) {
//				System.out.println("listen : every frame");
//			}
//		});
//		rotationYAni.pause();rotationYAni.resume();rotationYAni.reverse();
	
		animatorSet2.playTogether(//一起执行以下动画
				rotationYAni,
				ObjectAnimator.ofFloat(imageview_set, "translationX", 0, 800f).setDuration(100),
				ObjectAnimator.ofFloat(imageview_set, "translationY", 0, 300f).setDuration(200)
				);
		animatorSet2.setDuration(1000);//一并执行，只用3秒 内部动画各运行3秒，并行执行。内部动画设置的时间被set设置的覆盖
		animatorSet2.start();
		
		imageview.setBackground(getResources().getDrawable(R.drawable.a11));
		animate = imageview.animate();//该对象没有setRepeat的方法
		//通过一些动画属性来设置 组合成类似set的效果
		animate.alpha(0);
		animate.rotationX(50);
		animate.translationXBy(500);
		animate.scaleX(1.5f);
		animate.scaleY(1.5f);
		animate.setInterpolator(new BounceInterpolator());
		animate.setDuration(2000);
		animate.start();
		
		//使用PropertyValuesHolder 构造 Animator   组合成类似set的效果
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX",0f,2.5f);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY",0f,3f);     
		animator = ObjectAnimator.ofPropertyValuesHolder(imageview, pvhX,pvhY);
		animator.setDuration(2000);
		animator.start();
		
		//动画 变色  
		ObjectAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", CYAN, BLUE, RED);
		colorAnim.setTarget(ll_animation);
		colorAnim.setEvaluator(new ArgbEvaluator());
		colorAnim.setRepeatCount(ValueAnimator.INFINITE);
		colorAnim.setRepeatMode(ValueAnimator.REVERSE);
		colorAnim.setDuration(3000);
		colorAnim.start();
		
		/*
		 * ValueAnimator代码和xml设置中 没有setPropertyName 因为不是操作对象，只能是根据value进行某种动作
		 * 需要加监听器，监听值的变化 做相应的处理
		 */
		valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator);
		valueAnimator.setTarget(tv_num);
		valueAnimator.setEvaluator(new TypeEvaluator<Integer>() {

			@Override 
			public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
				System.out.println("百分比，fraction:" + fraction);
				System.out.println("结果值:" + (int)((startValue + fraction * (endValue - startValue)) / 10 * 10));
			    return (int)((startValue + fraction * (endValue - startValue)) / 10 * 10);
			}
		});
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				//在onAnimationUpdate中 该值返回第一个动画的 当前帧的evaluate 值
				System.out.println("animation.getAnimatedValue()==" + animation.getAnimatedValue());
				tv_num.setText(animation.getAnimatedValue() + "");
			}
		});
//		valueAnimator.setInterpolator(new LinearInterpolator());
		valueAnimator.start();
//		tv_num.postDelayed(new Runnable() {
//			public void run() {
//				valueAnimator.end();
//				valueAnimator.cancel();
//				valueAnimator = null;
//			}
//		}, 10000);
		
		/*
		 * ViewGroup中使用LayoutTransition 进行 监听布局的改变，而创建动画
		 * LayoutTransition.APPEARING 新增出现时
		 * 					CHANGE_APPEARING 
		 * 					CHANGE_DISAPPEARING 子view消失时
		 * 					CHANGING
		 * ViewGroup布局中：android:animateLayoutChanges="true"  使用的默认的LayoutTransition制作动画
		 */
		LayoutTransition layoutTransition = new LayoutTransition();
		layoutTransition.setDuration(5000);
		layoutTransition.setAnimator(LayoutTransition.APPEARING, scaleAnimator);
		ll_animation.setLayoutTransition(layoutTransition);
		
		final TextView tv = new TextView(this);
		tv.setWidth(100);
		tv.setHeight(100);
		tv.setText("中华人民共和国");
//		ll_animation.addView(tv);//对应type = APPEARING
		
//		ll_animation.postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				ll_animation.removeView(tv);
//			}
//		}, 2000);
	}
	
	
	/*
	 * pause 和 destroy 都停不了 动画，
	 * 好的作法是， 动画的次数不要太多，
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Toast.makeText(this, "onPause", 0).show();
//		translateAnimator.cancel();
//		scaleAnimator.cancel();
//		animatorSet.cancel();
//		alphaAnimator.cancel();
//		animatorSet2.cancel();
//		rotationYAni.cancel();
//		animator.cancel();
//		valueAnimator.cancel();
	}
}
