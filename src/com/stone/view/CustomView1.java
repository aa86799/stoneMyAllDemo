package com.stone.view;

import com.stone.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/*
 * extends View
 * extends ViewGroup	如layout，含子view
 */
/*
 * 视图的绘制流程：
 * 		被绘制的区域先标记为invalid，任何与其交互的区域都要重新绘制。
 * 	 系统绘制一个activity时，会调用setcontentView里的根节点的invalidate()，
 * 	 手动调用一个view的invalidate()，view就会重绘
 * 	 当绘制成功后，该区域被标记为valid
 * 		绘制分两个阶段
 *   1.测量尺寸，从根节点到子节点，再到子节点的子节点...  
 *   2.执行布局 
 *   3.遍历布局，从父到子绘制 成位图，展示给用户
 */
public class CustomView1 extends View {
	float mRotation;
	//由两个参数的构造方法中显示调用， 传递一个int style 给 defStyleAttr。
	public CustomView1(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	//xml创建 并使用自定义属性 declare-styleable name
	/*
	 * 可以参看android.R.attr 对应appcompat下的attrs.xml 中 查看如何定义属性
	 * 		android.content.res.TypedArray 查看它的一些get方法 get某某格式类型属性
	 * 已知的格式(format)类型有：reference(引用一个属性@...)、string、color、demission、boolean、integer
	 * 			float、fraction(百分比)、			
	 * 		enum、flag(标识,没在typedarray中看到，appcompat/attrs.xml中有使用) 使用方式
	 * 			<attr name=""> <enum name="v1" value="1"/> </attr>  要求整数
	 * 			<attr name="flag_attr"> <flag name="f1" value="1"/> </attr>  要求整数
	 * 		调用flag时可用按位或拼接
	 * 			<com.stone.view.Customview custom:flag_attr="f1|f2" .../>
	 * 
	 */
	public CustomView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		//
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.customview1);
		//系统会在自定义属性前加上它所属的declare-styleable 的name_
		int color = array.getColor(R.styleable.customview1_color, Color.WHITE);
		float rotation = array.getFloat(R.styleable.customview1_rotation, 0f);
		float score = array.getFraction(R.styleable.customview1_score, 0, 13, 10);
		array.recycle();//回收
		System.out.println("color=" + color + ", rotation=" + rotation + ", score=" + score);
		setBackgroundColor(color);
	}
	
	//代码创建
	public CustomView1(Context context) {
		super(context);
	}
	
	@Override //当前view和子view has been inflated
	protected void onFinishInflate() {
		super.onFinishInflate();
	}
	/* widthMeasureSpec  heightMeasureSpec 
	 * view static class MeasureSpec  一个模式+尺寸的结合来得到一个测量的规范，用一个整数值表示 
	 * 	UNSPECIFIED 不限制模式，父view没限制当前view
	 *  AT_MOST		最大值		当前view最大跟父view一致
	 *  EXACTLY(到底)	绝对值	当前view已经决定了尺寸  需要调用setMeasuredDimension	
	 */
	@Override //测量view 和子view 的尺寸  实际的宽高
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		System.out.println("---" + caclulateMeasure(widthMeasureSpec));
		int caclulateWidth = caclulateMeasure(widthMeasureSpec);
		System.out.println("上面是宽度的模式");
		int caclulateHeight = caclulateMeasure(heightMeasureSpec);
		System.out.println("上面是高度的模式");
		setMeasuredDimension(caclulateWidth, caclulateHeight);
	}
	
	/*
	 * onLayout和onMeasure的区别在于， onLayout定义了子view出现的位置，所占的大小在一定的矩形范围内，
	 * 		设onLayout中设置的size 为 lw 和 lh
	 * onMeasure测量了整个View和子View的宽高， 设onMeasure中设置的宽高为  mw 和 mh
	 * 		当mw>lw 或 mh>lh时， 界面上显示的效果就应该是：在lw和lh的范围内，mw和mh显示不完整
	 */
	@Override //分配 size和position 给所有子view	绘制的size(宽和高)		该方法一般在ViewGroup中需要重写
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
	}
	
	@Override	//szie发生变化时 是实际发生变化，还是绘制的发生变化？
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override //绘制内容
	protected void onDraw(Canvas canvas) {
//		canvas.drawColor(Color.RED); //ondraw在构造函数后, 这里设置了，就覆盖了
		
		canvas.save();
		System.out.println("mRotation===" + mRotation);
		canvas.rotate(mRotation);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.a11), null, new Rect(0,0,200,200), null);
		canvas.restore();
	}
	
	@Override //键按下
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	@Override //键长按
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		return super.onKeyLongPress(keyCode, event);
	}
	
	@Override //多次按键
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
		return super.onKeyMultiple(keyCode, repeatCount, event);
	}
	
	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		return super.onKeyPreIme(keyCode, event);
	}
	
	@Override //覆盖这个方法来实现本地视图键的快捷方式
	public boolean onKeyShortcut(int keyCode, KeyEvent event) {
		return super.onKeyShortcut(keyCode, event);
	}
	
	@Override //键抬起
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return super.onKeyUp(keyCode, event);
	}
	
	@Override //发生一个轨迹球运动事件
	public boolean onTrackballEvent(MotionEvent event) {
		return super.onTrackballEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	
	@Override //得到出失去焦点
	protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
	}
	
	@Override //包含当前View的窗口 得到或失去焦点
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
	}
	
	@Override	//附加到窗口后
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}
	
	@Override	//分离出窗口后
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
	
	@Override //容器的 可见性 变化后
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
	}
	
	private static final int DEFAULT_SIZE = 100;  //看成dp  默认值
	private int caclulateMeasure(int measureSpec) {
		int result = (int) (DEFAULT_SIZE * getResources().getDisplayMetrics().density); //计算得出像素
		
		//检索模式和尺寸
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		
		//基于模式选择尺寸
		switch (specMode) {
		case MeasureSpec.EXACTLY:
			result = specSize;
			System.out.println("caclulateMeasure--EXACTLY--");
			break;
		case MeasureSpec.AT_MOST:
			result = Math.min(result, specSize);
			System.out.println("caclulateMeasure--AT_MOST--");
			break;
		default://case MeasureSpec.UNSPECIFIED: use default size
			System.out.println("caclulateMeasure--UNSPECIFIED--");
			break;
		}
		
		return result; 
		/* view的onMeasure可能被多次调用，
		 * 如当父视图使用 UNSPECIFIED 规则来调用子view的onMeasure，如果得出的尺寸超出父view，
		 * 则可能需要重新计算子view的尺寸
		 */
	}
	
	public static class Custom1 extends View {//内部类需要写成静态的，否则，在xml中无法初始化外部类

		public Custom1(Context context) {
			super(context);
		}

		public Custom1(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
		}

		public Custom1(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
		
		@Override
			protected void onDraw(Canvas canvas) {
				canvas.drawColor(Color.BLUE);
			}
	}
}
