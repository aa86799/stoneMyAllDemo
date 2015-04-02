package com.stone.util;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode.VmPolicy;

public class ImageOpreationUtil {
	
	// 加载图片 特别是大图时，减少内存开销
	public Bitmap loadBitmap(Context context, int resID) {
		InputStream is = context.getResources().openRawResource(resID);
		Bitmap btp = BitmapFactory.decodeStream(is);
		
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = false;//解码前设为false，表示可以修改bounds
//		options.inSampleSize = 2; //宽高为原图的1/2
//		/*
//		 * decodeStream 最大的秘密在于其直接调用 JNI>>nativeDecodeAsset() 来完成 decode，无需再使用 Java 层的 createBitmap
//		 * 需要在不同的dpi资源目录下有相应的图片，否则不同分辨率的机器都是同样大小的图片
//		 */
//		Bitmap btp = BitmapFactory.decodeStream(is, null, options);
		return btp;
	}
}
