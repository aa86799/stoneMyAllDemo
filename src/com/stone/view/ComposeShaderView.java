package com.stone.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/16 16 15
 */
public class ComposeShaderView extends View {

    private ComposeShader mComposeShader;
    private ShapeDrawable mShapeDrawable;

    public ComposeShaderView(Context context, Bitmap bitmap) {
        super(context);
        LinearGradient shaderA = new LinearGradient(0, 0, bitmap.getWidth(), bitmap.getHeight(),
                0xB0C4DE,0xefffff00, Shader.TileMode.CLAMP);
        LinearGradient shaderC = new LinearGradient(0, 0, bitmap.getWidth(), bitmap.getHeight(),
                new int[]{0xddff0000, 0xAAEEE685, 0xffB0C4DE, 0xd800ff00},
                new float[]{0.1f, 0.3f, 0.7f, 1.0f}, Shader.TileMode.MIRROR);

        mComposeShader = new ComposeShader(shaderA, shaderC, PorterDuff.Mode.SRC_ATOP);

        mShapeDrawable = new ShapeDrawable(new OvalShape());
//        mShapeDrawable = new ShapeDrawable(new ArcShape(90, 180 ));//左半
//        mShapeDrawable = new ShapeDrawable(new ArcShape(270, 180 ));//右半
        mShapeDrawable.getPaint().setShader(mComposeShader);
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
