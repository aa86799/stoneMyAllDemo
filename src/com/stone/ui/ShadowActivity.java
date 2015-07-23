package com.stone.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.stone.R;

/**
 * 阴影
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/23 13 40
 */
public class ShadowActivity extends Activity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageEffect effect = new ImageEffect(this, R.drawable.a8);
        setContentView(effect);
    }

    private class ImageEffect extends View {

        private Paint mPaint;
        private int mResid;

        public ImageEffect(Context context, int resid) {
            super(context);
            this.mResid = resid;

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setShadowLayer(5, 15, 15, Color.BLACK); //设置阴影层
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        }

        public void onDraw(Canvas canvas){
            super.onDraw(canvas);
            int posX  = 20;
            int posY = 50;
            int PicWidth,PicHeight;

            Drawable drawable = getResources().getDrawable(mResid);
            Drawable mutate = getResources().getDrawable(mResid).mutate();//如果不调用mutate方法，则原图也会被改变，因为调用的资源是同一个，所有对象是共享状态的。
            Drawable drawTest = getResources().getDrawable(mResid);
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), mResid);
            PicWidth = drawable.getIntrinsicWidth();
            PicHeight = drawable.getIntrinsicHeight();

            drawTest.setBounds(posX, (2 * posY) + PicHeight, posX + PicWidth, (2 * posY) + 2 * PicHeight );
            drawable.setBounds(posX,posY,posX+PicWidth,posY+PicHeight);
            mutate.setBounds(0, 0, PicWidth, PicHeight);

            canvas.drawColor(Color.WHITE);//设置画布颜色
            canvas.save();
            mutate.setColorFilter(0x7f000000, PorterDuff.Mode.SRC_IN);
            canvas.translate(posX + (int)(0.9 * PicWidth/2), posY + PicHeight/2);//图像平移为了刚好在原图后形成影子效果。
            canvas.skew(-0.8F, 0.0F);//图像倾斜效果。
            canvas.scale(1.0f, 0.5f);//图像（其实是画布）缩放，Y方向缩小为1/2。
            mutate.draw(canvas);//此处为画原图像影子效果图，比原图先画，则会在下层。
            drawable.clearColorFilter();
            canvas.restore();

            canvas.save();
            drawable.draw(canvas);//此处为画原图像，由于canvas有层次效果，因此会盖在影子之上。
            canvas.restore();

            //默认无效果原图
            canvas.save();
            drawTest.draw(canvas);
            canvas.restore();

            //图片阴影效果
            Rect rect = new Rect(2*posX + PicWidth + 10, 2*posY + PicHeight + 10, 2*posX +
                    2*PicWidth + 24, 2*posY + 2*PicHeight + 12);
            //由于图片的实际尺寸比显示出来的图像要大一些，因此需要适当更改下大小，以达到较好的效果

            RectF rectF = new RectF(rect);
            mPaint.setColor(Color.parseColor("#d8abfefe"));
            canvas.drawRoundRect(rectF, 10f, 10f, mPaint);//在原有矩形基础上，画成圆角矩形，同时带有阴影层。
            canvas.drawBitmap(bmp, 2* posX + PicWidth, 2 * posY + PicHeight, null);//画上原图。
            canvas.restore();
        }
    }
}
