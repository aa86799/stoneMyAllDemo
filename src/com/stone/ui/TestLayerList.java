package com.stone.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.stone.R;
import com.stone.util.DensityUtil;
//http://developer.android.com/guide/topics/resources/drawable-resource.html
public class TestLayerList extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DensityUtil util = DensityUtil.Builder.build(this);


//        FrameLayout frameLayout = new FrameLayout(this);
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(util.dip2px(200), 300);
//        setContentView(frameLayout, lp);
//        ImageView iv = new ImageView(this);
//        FrameLayout.LayoutParams ivLp = new FrameLayout.LayoutParams(util.dip2px(150), 200);
//        ivLp.gravity = Gravity.CENTER;
//        iv.setLayoutParams(ivLp);
//        frameLayout.addView(iv);


        setContentView(R.layout.activity_layerlist);
        ImageView iv = (ImageView) findViewById(R.id.iv_img);


//        iv.setBackgroundResource(R.drawable.layerlist);

        //ColorDrawable
//        ColorDrawable colorDrawable = new ColorDrawable(Color.argb(100,70,80,7));
//        iv.setBackgroundResource(R.drawable.color_drawable);
//        iv.setBackgroundColor(colorDrawable.getColor());

        //颜色渐变
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation
                .TL_BR, null);
        gradientDrawable.setColors(new int[]{Color.YELLOW, Color.CYAN, Color.RED, Color.BLUE, Color
                .GREEN});
        gradientDrawable.setGradientRadius(50);
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setUseLevel(false);
//        iv.setBackground(gradientDrawable);


        System.out.println("300dp转px:" + util.dip2px(300));
        System.out.println("上面的px再转dp:" + util.px2dip(util.dip2px(300)));


//        ScaleDrawable scaleDrawable = new ScaleDrawable(getResources().getDrawable(R.drawable
//                .a4), Gravity.CENTER, 1.0f, 1.0f);
        iv.setBackground(getResources().getDrawable(R.drawable.scale_drawable));


        //Level List drawable
        //inset drawable
        //Clip Drawable
    }

}
