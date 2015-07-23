package com.stone.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.stone.R;
import com.stone.util.ImageUtil;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/10 10 26
 */
public class InvertedMirrorActivity extends Activity {

    private ImageView mImageView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_matrix);
        mImageView = (ImageView) findViewById(R.id.iv_img);
        mBitmap = ImageUtil.drawableToBitmap(getResources().getDrawable(R.drawable.a3));
        setImg(mBitmap);
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        mImageView.setBackgroundColor(Color.CYAN);
    }

    private void setImg(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }

    //倒影
    public void invertedImg(View view) {
//        Bitmap invertedBitmap = ImageUtil.createReflectionImageWithOrigin(mBitmap);

        //1. 倒立图
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        int w = mBitmap.getWidth();
        int h = mBitmap.getHeight();
        Bitmap reflectionImg = Bitmap.createBitmap(mBitmap, 0, h / 2, w, h / 2, matrix, false);


        //2. 要生成原图加倒立图，先生成一个可变的Bitmap，高度为1.5倍原h
        int gap = 10; //间隙 空白
        Bitmap newBitmap = Bitmap.createBitmap(w, h + h / 2 + gap, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(mBitmap, 0, 0, paint);  //绘制原图
        canvas.drawBitmap(reflectionImg, 0, h + gap, paint); //绘制倒立图

        //3. 画笔使用 LinearGradient 线性渐变渲染
        LinearGradient lg = new LinearGradient(0, h + gap, w, newBitmap.getHeight(), 0xabff0000,
                0x00ffff00, Shader.TileMode.MIRROR);
        paint.setShader(lg);
        //4. 指定画笔的Xfermode  即绘制的模式(不同的模式，绘制的区域不同)
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        //5. 在倒立图区，绘制矩形渲染图层
        canvas.drawRect(0, h + gap, w, newBitmap.getHeight(), paint);
        paint.setXfermode(null);

        setImg(newBitmap);
    }

    //水平
    public void mirrorByX(View view) {
        mBitmap = ImageUtil.mirrorImageReverseByX(mBitmap);
        setImg(mBitmap);
    }

    //垂直
    public void mirrorByY(View view) {
        mBitmap = ImageUtil.mirrorImageReverseByY(mBitmap);
        setImg(mBitmap);
    }

}
