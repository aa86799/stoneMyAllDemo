package com.stone.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.stone.R;
import com.stone.util.ImageUtil;
import com.stone.view.BitmapShaderView;
import com.stone.view.ComposeShaderView;
import com.stone.view.LinearGradientView;
import com.stone.view.RadialGradientView;
import com.stone.view.SweepGradientView;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/14 17 55
 */
public class ShaderActivity extends Activity {
    private Bitmap mBitmap;
    private FrameLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shader);
        mLayout = (FrameLayout) findViewById(R.id.frame_layout);
        mBitmap = ImageUtil.drawableToBitmap(getResources().getDrawable(R.drawable.a3));


//        setContentView(new BitmapShaderView(this, mBitmap));
//        setContentView(new LinearGradientView(this, mBitmap));
//        setContentView(new ComposeShaderView(this, mBitmap));
//        setContentView(new RadialGradientView(this, mBitmap));
//        setContentView(new SweepGradientView(this, mBitmap));
    }

    public void bitmapShader(View view) {
        if (mLayout.getChildCount() > 0) {
            mLayout.removeAllViews();
        }
        mLayout.addView(new BitmapShaderView(this, mBitmap));
    }

    public void linearGradient(View view) {
        if (mLayout.getChildCount() > 0) {
            mLayout.removeAllViews();
        }
        mLayout.addView(new LinearGradientView(this, mBitmap));
    }

    public void composeShader(View view) {
        if (mLayout.getChildCount() > 0) {
            mLayout.removeAllViews();
        }
        mLayout.addView(new ComposeShaderView(this, mBitmap));
    }

    public void radialGradient(View view) {
        if (mLayout.getChildCount() > 0) {
            mLayout.removeAllViews();
        }
        mLayout.addView(new RadialGradientView(this, mBitmap));
    }

    public void sweepGradient(View view) {
        if (mLayout.getChildCount() > 0) {
            mLayout.removeAllViews();
        }
        mLayout.addView(new SweepGradientView(this, mBitmap));
    }
}
