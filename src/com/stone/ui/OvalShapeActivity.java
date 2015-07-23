package com.stone.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.stone.R;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/17 10 54
 */
public class OvalShapeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layerlist);
        ImageView imageView = (ImageView) findViewById(R.id.iv_img);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400, 600);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(params);
//        imageView.setImageResource(R.drawable.a2);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        OvalShape ovalShape = new OvalShape();
        ShapeDrawable drawable = new ShapeDrawable(ovalShape);
        drawable.getPaint().setColor(Color.RED);
        drawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        imageView.setBackgroundDrawable(drawable);

    }
}
