package com.stone.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.stone.R;
import com.stone.util.LruCacheUtil;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/22 12 10
 */
public class MatrixActivity extends Activity {

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_matrix);
        findViewById(R.id.ll_menu).setVisibility(View.GONE);
        mImageView = (ImageView) findViewById(R.id.iv_img);
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        mImageView.setImageResource(R.drawable.a2);

        Matrix matrix = new Matrix();
        matrix.postRotate(30, 100, 100);

//        matrix.setValues(new float[]{
//                0.8660254f, -0.5f, 63.39746f,
//                0.5f, 0.8660254f, -36.60254f,
//                0.0f, 0.0f, 1.0f});

        matrix.postTranslate(100, 100);
        matrix.setScale(1, 3);
//        matrix.setRotate(45);
//        matrix.setTranslate(100, 100);
//        matrix.postTranslate(200, 200);
//        matrix.preRotate(30);
        matrix.postScale(2, 1);
//        matrix.preTranslate(-100, -100);

        mImageView.setImageMatrix(matrix);

        System.out.println(matrix);
        System.out.println(mImageView.getImageMatrix());

        /**
         * set 方法 会清除之前所有的 操作
         * pre 会加载到队列最前面
         * post会加载到队列最后面
         *
         * scalex, skewx, transx,
         * skewy,  scaley, transy,
         * per0,   per1,   per2
         *
         * 初始化Matrix：
         * |1 0 0|
         * |0 1 0|
         * |0 0 1|   形如单位矩阵
         */

        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ALPHA_8);
        new LruCacheUtil().addBitmapToMemoryCache("abc",  bitmap);
    }
}
