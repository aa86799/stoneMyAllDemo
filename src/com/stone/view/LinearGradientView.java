package com.stone.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/16 16 15
 */
public class LinearGradientView extends View {

    private LinearGradient mLinearGradient;
    private ShapeDrawable mShapeDrawable;

    public LinearGradientView(Context context, Bitmap bitmap) {
        super(context);

//        mLinearGradient = new LinearGradient(0, 0, bitmap.getWidth(), bitmap.getHeight(),
//                0xddff0000,0x2300ff00, Shader.TileMode.MIRROR);
        mLinearGradient = new LinearGradient(0, 0, bitmap.getWidth(), bitmap.getHeight(),
                new int[]{0xddff0000, 0x2300ff00, 0x470000ff, 0xffabc777},
                new float[]{0.1f, 0.3f, 0.5f, 1.0f}, Shader.TileMode.CLAMP);

        mShapeDrawable = new ShapeDrawable(new OvalShape());
//        mShapeDrawable = new ShapeDrawable(new ArcShape(90, 180 ));//左半
//        mShapeDrawable = new ShapeDrawable(new ArcShape(270, 180 ));//右半
        mShapeDrawable.getPaint().setShader(mLinearGradient);
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
