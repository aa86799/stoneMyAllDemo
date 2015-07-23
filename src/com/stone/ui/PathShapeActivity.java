package com.stone.ui;

import android.app.Activity;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.stone.R;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/17 11 03
 */
public class PathShapeActivity extends Activity {

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

        Path path = new Path();
        path.moveTo(50, 0);
        path.lineTo(0, 50);
        path.lineTo(50, 100);
        path.lineTo(100, 50);
        path.lineTo(50, 0);
        PathShape pathShape = new PathShape(path, 200, 100);
        ShapeDrawable drawable = new ShapeDrawable(pathShape);
        drawable.getPaint().setColor(Color.RED);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        imageView.setBackgroundDrawable(drawable);

    }
}
