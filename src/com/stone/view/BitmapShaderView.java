package com.stone.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/14 16 08
 */
public class BitmapShaderView extends View {

    private BitmapShader mBitmapShader;
    private ShapeDrawable mShapeDrawable;

    public BitmapShaderView(Context context, Bitmap bitmap) {
        super(context);

//        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.REPEAT);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);

        mShapeDrawable = new ShapeDrawable(new OvalShape());
//        mShapeDrawable = new ShapeDrawable(new ArcShape(90, 180 ));//左半
//        mShapeDrawable = new ShapeDrawable(new ArcShape(270, 180 ));//右半
        mShapeDrawable.getPaint().setShader(mBitmapShader);
//        mShapeDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight()); //原图大小
        mShapeDrawable.setBounds(0, 0, bitmap.getWidth() * 2, bitmap.getHeight() * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.CYAN);

        mShapeDrawable.draw(canvas);
    }
}
