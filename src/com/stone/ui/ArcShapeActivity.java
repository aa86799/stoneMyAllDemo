package com.stone.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.stone.R;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/17 10 27
 */
public class ArcShapeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layerlist);
        ImageView imageView = (ImageView) findViewById(R.id.iv_img);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(600, 600);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(params);
//        imageView.setImageResource(R.drawable.a2);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        ArcShape arcShape = new ArcShape(45, 270); //顺时针  开始角度45， 扫描的角度270   扇形
        ShapeDrawable drawable = new ShapeDrawable(arcShape);
        drawable.getPaint().setColor(Color.RED);
        drawable.getPaint().setStyle(Paint.Style.FILL);

        Bitmap bitmap =  ((BitmapDrawable)getResources().getDrawable(R.drawable.aa)).getBitmap();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader
                .TileMode.REPEAT);
        Matrix matrix = new Matrix();
        matrix.preScale(600.00f / bitmap.getWidth(), 600.00f / bitmap.getHeight());
        bitmapShader.setLocalMatrix(matrix);
        drawable.getPaint().setShader(bitmapShader);

        imageView.setBackgroundDrawable(drawable);
    }
}
