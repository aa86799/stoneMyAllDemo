package com.stone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.stone.R;

/*
 * 如一个圆 分n等份
 * 拖动一次，旋转一格， 分两个方向，顺时针和逆时针
 */
public class RotateCircleView extends View {
	float radius;
	int sect = 1;
	
	public RotateCircleView(Context context) {
		super(context);
		init(context);
	}

	public RotateCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.rotate_circle);
		//系统会在自定义属性前加上它所属的declare-styleable 的name_
		sect = array.getInteger(R.styleable.rotate_circle_sect, sect);
		
		array.recycle();//回收
		
		init(context);
		// this(context, attrs, android.R.style.Theme_Black);
	}

//	public RotateCircleView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		init(context);
//	}

	private void init(Context context) {
		System.out.println(getX());
		System.out.println(getY());
		System.out.println(getLeft());
		System.out.println(getTop());
		System.out.println(getRight());
		System.out.println(getBottom());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure 会给一默认的size
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
		System.out.println("---" + measureWidth(widthMeasureSpec));
		System.out.println("---" + measureHeight(heightMeasureSpec));
		
		radius = measureWidth(widthMeasureSpec) / 2;
	}
	
	 /**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // Respect AT_MOST value if that was what is called for by measureSpec
            result = Math.min(result, specSize);
        }

        return result;
    }

    /**
     * Determines the height of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

   
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		canvas.drawCircle(radius, radius, radius, paint);//x,y 相对于view的宽高自身
		if (sect <= 1 || 360 % sect != 0) return;
		int angle = 360 / sect;
		/*
		 * 以圆心为原点，
		 */
		for (int i = 0; i < sect; i++) {
			double sina = 2 * Math.PI * i / sect;
			//sina <==> a / radius
			double a = radius * sina;
			paint.setColor(Color.RED);
			canvas.drawLine(radius, radius, radius - radius * i, (float) a, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

}
