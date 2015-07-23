package com.stone.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/16 16 58
 */
public class SweepGradientView extends View {

    private SweepGradient mSweepGradient;
    private ShapeDrawable mShapeDrawable;

    public SweepGradientView(Context context, Bitmap bitmap) {
        super(context);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
//        mSweepGradient = new SweepGradient(w/2, h/2, 0xddff00f0, 0xffabc777);
        mSweepGradient = new SweepGradient(w, h, new int[]{0xffff0000, 0xff00ff00, 0xff0000ff,
                0xffabc777, 0xffEE00EE},
                new float[]{0.2f, 0.4f, 0.6f, 0.75f, 1f});

        mShapeDrawable = new ShapeDrawable(new OvalShape());
//        mShapeDrawable = new ShapeDrawable(new ArcShape(90, 180 ));//左半
//        mShapeDrawable = new ShapeDrawable(new ArcShape(270, 180 ));//右半
        mShapeDrawable.getPaint().setShader(mSweepGradient);
//        mShapeDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight()); //原图大小
        mShapeDrawable.setBounds(0, 0, bitmap.getWidth() * 2, bitmap.getHeight() * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        mShapeDrawable.draw(canvas);
    }
}
