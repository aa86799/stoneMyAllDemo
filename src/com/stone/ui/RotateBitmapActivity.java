package com.stone.ui;

import com.stone.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RotateBitmapActivity extends Activity {
	
	Button btnRotate1, btnRotate2;
	ImageView ivBitmap;
	
	//逆时针转45度
	RotateAnimation rotateAnimation = new RotateAnimation(0, -45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("旋转图片");
		setContentView(R.layout.rorate_bitmap);
		btnRotate1 = (Button) findViewById(R.id.btn_rorate);
		btnRotate2 = (Button) findViewById(R.id.btn_rorate_anim);
		ivBitmap = (ImageView) findViewById(R.id.iv_bitmap);
		
        btnRotate1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rotateBitmap1();
			}
		});
        btnRotate2.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		rotateBitmap2();
        	}
        });
	}
	/**
	 * 使用矩阵对象 旋转
	 */
	private void rotateBitmap1() {
		LinearLayout linLayout = new LinearLayout(this); 
		BitmapDrawable bitmapDrawable = (BitmapDrawable) ivBitmap.getDrawable();
		//获取这个图片的宽和高
        int width = bitmapDrawable.getBitmap().getWidth(); 
        int height = bitmapDrawable.getBitmap().getHeight(); 
        //定义预转换成的图片的宽度和高度
        int newWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth() / 2; 
        int newHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight() / 2;
        //计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) newWidth) / width; 
        float scaleHeight = ((float) newHeight) / height; 
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix(); 
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight); 
        //旋转图片 动作
        matrix.postRotate(90); 
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapDrawable.getBitmap(), 0, 0, width, height, matrix, true); 
       
        ivBitmap.setImageBitmap(resizedBitmap);
	}
	
	/**
	 * 通过动画方式旋转，实际位置没有改变，当再次运行该函数，图像还是从原来的位置开始转动
	 */
	private void rotateBitmap2() {
		 
		rotateAnimation.setDuration(1000);
		ivBitmap.startAnimation(rotateAnimation);
		
		rotateAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				rotateAnimation.setFillAfter(true);
				//逆时针转45度  坐标点
//				ivBitmap.layout(l, t, r, b);
			}
		});
		
	}
	
	
}
