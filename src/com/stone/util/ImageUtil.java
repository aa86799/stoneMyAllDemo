package com.stone.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class ImageUtil {
	private static final String TAG = "ImageUtil";
//	private static final String SDCARD_CACHE_IMG_PATH = Environment //
//			.getExternalStorageDirectory().getPath() + "/book/images/";
	private static final String SDCARD_CACHE_IMG_PATH = "mnt/sdcard/book/images/";

	/**
	 * 保存图片到SD卡
	 * @param imagePath
	 * @param buffer
	 * @throws IOException
	 */
	public static void saveImage(String imagePath, byte[] buffer) throws IOException {//
		File f = new File(imagePath);
		if (f.exists()) {
			return;
		} else {
			File parentFile = f.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdir(); // /book/images/
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(imagePath);
			fos.write(buffer);
			fos.flush();
			fos.close();
		}
	}
	
	/**
	 * 将bitmap对象 压制为png格式  保存在 sd卡中
	 * @param imagePath  sd卡 保存路径
	 * @param bitmap  对象
	 */
	private static void saveImage4PNG(String imagePath, Bitmap bitmap) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 0, baos);
			saveImage(imagePath, baos.toByteArray());
		} catch (IOException e) {
			LogUtils.printError(TAG, e.getMessage(), e);
		}
		
	}
			
	
	/**
	 * 从SD卡加载图片
	 * @param imagePath
	 * @return
	 */
	private static Bitmap getImageFromLocal(String imagePath){
		File file = new File(imagePath);
		if (file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			file.setLastModified(System.currentTimeMillis());
			return bitmap;
		}
			return null;
	}
	
	/**
	 * 先从sd卡中加载，如果没有，则从网上下载(下载时 ) 保存在sd卡中
	 * @param imagePath   sd卡 保存路径
	 * @param imgUrl	图片网上地址
	 * @param callback  该接口，实现 将bitmap 显示在 view上
	 * @return Bitmap 对象
	 */
	public static Bitmap loadImage(final String imagePath,final String imgUrl,//
			final ImageCallback callback, final int imgWidth, final int imgHeight) {
		Bitmap bitmap = getImageFromLocal(imagePath);
		if (bitmap != null) {
			return bitmap;
		}
		//从网上加载
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.obj != null) {
					Bitmap bitmap = (Bitmap) msg.obj;
					callback.loadImage(bitmap);//交由实现类 load img
				}
			}
		};
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(imgUrl);
//					System.out.println(imgUrl);
					URLConnection conn = url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					
					//取得图片参数 
			//BitmapFactory.Options 代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化   
					BitmapFactory.Options options = new BitmapFactory.Options();  
					options.inJustDecodeBounds = true;  
					BitmapFactory.decodeStream(is, null, options); // inJustDecodeBounds为true时，decode出的bmp 为null，但是能得到原始的 宽和高
					is.close();
					
					// 生成压缩的图片   
					int i = 0;  
				    while (true) {  
					   // 这一步是根据要设置 压缩后大小，使宽和高都能满足   
					if ((options.outWidth >> i <= imgWidth)  && (options.outHeight >> i <= imgHeight)) {  
					      // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！   
						conn = url.openConnection();
						conn.connect();
					    is = conn.getInputStream();
					     // 这个参数表示 新生成的图片为原始图片的几分之一。   
			            options.inSampleSize = (int) Math.pow(2.0D, i);  
			             // 这里之前设置为了true，所以要改为false，否则就创建不出图片   
			            options.inJustDecodeBounds = false;  
			  
			            bitmap = BitmapFactory.decodeStream(is, null, options);  
			            break;  
				      }  
				      	i++; 
				    }  
				    
					
					saveImage4PNG(imagePath, bitmap);
					Message msg = handler.obtainMessage();
					msg.obj = bitmap;
					handler.sendMessage(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		ThreadPoolManager.getInstance().addTask(runnable);
		
		bitmap = getImageFromLocal(imagePath);
		if(bitmap != null){
			return bitmap;
		}
		return null;
	}
	
	/**
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return 缩放因子
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, 
			int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
	}

	// 返回图片存到sd卡的路径
	public static String getCacheImgPath() {
		return SDCARD_CACHE_IMG_PATH;
	}
	

	
	/**
	 * 
	 * 加载图片的回调函数
	 */
	public interface ImageCallback {
		public void loadImage(Bitmap bitmap);
	}
	
	/**
	 * 将Drawable转化为Bitmap 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable){ 
//		int width = drawable.getIntrinsicWidth(); 
//		int height = drawable.getIntrinsicHeight(); 
//		Bitmap bitmap = Bitmap.createBitmap(width, height, 
//		drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565); 
//		Canvas canvas = new Canvas(bitmap); 
//		drawable.setBounds(0,0,width,height); 
//		drawable.draw(canvas); 
//		return bitmap;
		
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap();
		
	}

    /**
     * 转换Bitmap为Drawable
     * @param bitmap
     * @return
     */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}
	
	/**
	 * 画倒影
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap){ 
		final int reflectionGap = 4; 
		int width = bitmap.getWidth(); 
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix(); 
		matrix.postScale(1, -1); //垂直变换
        //裁剪原图的(0,w/2,w,w/2)位置上的矩形  以matrix变换  即垂直翻转
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height/2, width, height/2, matrix, false);
        //创建新的可变位图：宽w，高1.5h
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height/2), Config.ARGB_8888);
        //以位图创建画布
		Canvas canvas = new Canvas(bitmapWithReflection);
        //绘制原图  这时宽高占 画布(或者说新位图)的100%和2/3
		canvas.drawBitmap(bitmap, 0, 0, null);
        Paint paint = new Paint();
        //绘制倒立图的矩形区
		canvas.drawRect(0, height,width,height + reflectionGap, paint);
        //绘制倒立图
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		//绘制阴影
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(),//
				0, bitmapWithReflection.getHeight() + reflectionGap, 0xabff0000, 0x00ffff00,
                TileMode.MIRROR);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
		return bitmapWithReflection;
	}
	/**
     * 水平镜像反转
     * @param bitmap
     * @return
     */
    public static Bitmap mirrorImageReverseByX(Bitmap bitmap) {
        Matrix mx = new Matrix();
//        mx.postScale(-1, 1); //x方向反转，例如前置摄像头自拍时，完成的照片和预览时的照片就像反转了一样
        mx.preScale(-1, 1); //x方向反转，例如前置摄像头自拍时，完成的照片和预览时的照片就像反转了一样

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapWithReflection = Bitmap.createBitmap(bitmap, 0, 0, width, height, mx, false);
        return bitmapWithReflection;
    }

    /**
     *
     * @param resource
     * @return
     */
    public static Bitmap mirrorImageReverseByX(Context ctx, int resource) {
        Bitmap drawableToBitmap = drawableToBitmap(ctx.getResources().getDrawable(resource));
        return mirrorImageReverseByX(drawableToBitmap);
    }

    /**
     * 垂直镜像反转
     * @param bitmap
     * @return
     */
    public static Bitmap mirrorImageReverseByY(Bitmap bitmap) {
        Matrix mx = new Matrix();
		mx.postScale(1, -1); //y方向反转，例如绘制倒影

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapWithReflection = Bitmap.createBitmap(bitmap, 0, 0, width, height, mx, false);
        return bitmapWithReflection;
    }

    /**
     *
     * @param resource
     * @return
     */
    public static Bitmap mirrorImageReverseByY(Context ctx, int resource) {
        Bitmap drawableToBitmap = drawableToBitmap(ctx.getResources().getDrawable(resource));
        return mirrorImageReverseByY(drawableToBitmap);
    }


}
