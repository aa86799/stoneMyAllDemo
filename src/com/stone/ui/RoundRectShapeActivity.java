package com.stone.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.stone.R;
import com.stone.util.ImageUtil;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/16 18 29
 */
public class RoundRectShapeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layerlist);
        ImageView imageView = (ImageView) findViewById(R.id.iv_img);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400, 400);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(params);
//        imageView.setImageResource(R.drawable.a2);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        float[] outerRadii = {20, 20, 40, 40, 60, 60, 80, 80};//外矩形 左上、右上、右下、左下 圆角半径
//        float[] outerRadii = {20, 20, 20, 20, 20, 20, 20, 20};//外矩形 左上、右上、右下、左下 圆角半径
        RectF inset = new RectF(100, 100, 200, 200);//内矩形距外矩形，左上角x,y距离， 右下角x,y距离
        float[] innerRadii = {20, 20, 20, 20, 20, 20, 20, 20};//内矩形 圆角半径
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, inset, innerRadii);
//        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, innerRadii); //无内矩形

        ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
        drawable.getPaint().setColor(Color.MAGENTA);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.STROKE);//描边
        imageView.setBackground(drawable);

    }
}
