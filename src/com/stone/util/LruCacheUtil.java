package com.stone.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/23 11 27
 */
public class LruCacheUtil {

    private final long MAX_MEMORY = Runtime.getRuntime().maxMemory(); //以字节为单位 byte
    private LruCache<String, Bitmap> mLruCache;

    public LruCacheUtil() {
        mLruCache = new LruCache<String, Bitmap>((int) (MAX_MEMORY / 8)) {//1/8 作缓存区

            @Override //必须重写 计算图片占用的内存大小 单位需要与 构造方法参数单位一致
            protected int sizeOf(String key, Bitmap value) {
                //三种计算size方法
                System.out.println(value.getByteCount() + "...");
                System.out.println(value.getRowBytes() * value.getHeight() + "...");
                System.out.println(value.getWidth() * value.getHeight() * getBytesForConfig(value
                        .getConfig()) + "...");

                return value.getByteCount();
            }
        };
    }

    private int getBytesForConfig(Bitmap.Config config) {
        switch (config) {
            case ALPHA_8:
                System.out.println("...ALPHA_8...");
                return 1;
            case ARGB_4444:
            case RGB_565:
                System.out.println("...4444-565...");
                return 2;
            case ARGB_8888:
                System.out.println("...8888...");
                return 4;
        }
        return 0;
    }

    public synchronized void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mLruCache.get(key) == null) {
            if (key != null && bitmap != null)
                mLruCache.put(key, bitmap);
        } else
//            Log.w("LruCache", "the res is already exists");
        ;
    }

    public synchronized Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    public synchronized void removeImageCache(String key) {
        if (key != null) {
            Bitmap bm = mLruCache.remove(key);
            if (bm != null)
                bm.recycle();
        }
    }

    public void clearCache() {
        if (mLruCache.size() > 0) {
            mLruCache.evictAll(); // mLruCache.trimToSize(-1);
        }
    }

}
