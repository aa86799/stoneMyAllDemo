package com.stone.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.stone.R;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/17 10 11
 */
public class RectShapeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layerlist);
        ImageView imageView = (ImageView) findViewById(R.id.iv_img);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400, 400);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(params);
//        imageView.setImageResource(R.drawable.a2);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        RectShape rectShape = new RectShape();
        ShapeDrawable drawable = new ShapeDrawable(rectShape);
        drawable.getPaint().setColor(Color.RED);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        imageView.setBackgroundDrawable(drawable);

    }
}
