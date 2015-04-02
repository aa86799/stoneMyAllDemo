package com.stone.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

/**
 * 使用了  SoftReference WeakReference
 *
 */
public class ImageDownloader {
    private static final String LOG_TAG = "ImageDownloader";
    /**
     * 从互联网下载指定图像 并绑定到提供的ImageView。  
     * 
     * @param url The URL of the image to download.这个绑定是即时的，如果图像出现在缓存并将异步完成
     * @param imageView The ImageView to bind the downloaded image to.否则,如果发生错误,一个空的bitmap 将关联到ImageView
     */
    public void download(String url, ImageView imageView) {
        resetPurgeTimer();
        if (cancelPotentialDownload(url, imageView)) {
        	BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);         
        	DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);         
        	imageView.setImageDrawable(downloadedDrawable);         
        	task.execute(url);     
        }
    }

    /** 取消 潜在的 下载任务
     * Returns true  如果下载任务已经取消 或 如果在imageview上没有下载的任务载
     * Returns false 如果下载过程中处理相同的url
     */
    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.url;
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
            	// bitmapUrl!=null  --  表示任务里已经有下载的url了
            	// bitmapUrl.equals(url) -- 表示任务的url没有变化
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param imageView Any imageView
     * @return in this imageView， 检查 当前活跃的下载任务(如果有的话)
     * 		   null --- if there is no such task.
     */
    private static BitmapDownloaderTask getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {//如果是DownloadedDrawable类型 ，表示imageView已经设置过了
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
                return downloadedDrawable.getBitmapDownloaderTask();  //表示下载完了 task 在弱引用中了
            }
        }
        return null;
    }
    /**
     *  非异步下载 
     * @param url
     * @return
     */
    private Bitmap downloadBitmap(String url) {
        final int IO_BUFFER_SIZE = 4 * 1024;

        HttpClient client = new DefaultHttpClient();
//        HttpPost request = new HttpPost(url);
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request);
            final int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("sdfsadfasdf~~~~" + statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode +
                        " while retrieving bitmap from " + url);
                return null;
            }
           
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                
                try {
                    inputStream = entity.getContent();
                    // return BitmapFactory.decodeStream(inputStream);
                    // Bug on slow connections, fixed in future release.
                    return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();//释放	
                }
            }
        } catch (IOException e) {
            request.abort();
            Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
        } catch (IllegalStateException e) {
            request.abort();
            Log.w(LOG_TAG, "Incorrect URL: " + url);
        } catch (Exception e) {
            request.abort();
            Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
        } finally {
            if ((client instanceof AndroidHttpClient)) {
                ((AndroidHttpClient) client).close();
            }
        }
        return null;
    }

    /*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read(); //in.read()只读一个字节,返回0~255的字节值
                    if (b < 0) {
                        break;  // 到文件结尾  即 b=-1
                    } else {
                        bytesSkipped = 1; // in.read()已经读到了一个字节，所以 置为1
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    /** 异步下载Task
     * The actual AsyncTask that will asynchronously download the image.
     */
    class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private String url;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        /**
         * Actual download method.
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            return downloadBitmap(url);
        }

        /**
         * Once the image is downloaded, associates it to the imageView
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            addBitmapToCache(url, bitmap);

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
                // Change bitmap only if this process is still associated with it
                // Or if we don't use any bitmap to task association (NO_DOWNLOADED_DRAWABLE mode)
                if ((this == bitmapDownloaderTask) /*|| (mode != Mode.CORRECT)*/) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    /** 含有BitmapDownloaderTask的弱引用对象
     * 有一个方法 能从弱引用中 返回BitmapDownloaderTask对象
     * DownloadedDrawable extends ColorDrawable 
     * -----对于ListView上的图像浏览，当用户快速滑动ListView时，
     * 		某一个ImageView对象会被用到很多次，每一次显示都会触发一个下载，
     * 		然后改变对应的图片。和大多数并行应用一样，有顺序相关的问题。
     * 		在这个程序中，不能保证下载会按开始的顺序结束，有可能先开始的后下载完，
     * 		结果就是，由于与该item对应的网络图片下载慢，
     * 		导致该item位置还暂时显示着之前的item显示的图片(还没被刷新，有可能长时间不被刷新）
     * 	      为了解决这个问题，我们应该记住下载的顺序，使得最后的下载会被有效地显示，
     * 		要让每一个ImageView记住它们的上一次下载任务。因此我们给出了DownloadedDrawable类，
     * 		向ImageView中加入对对应下载任务的弱引用来暂时绑定正在下载图片的ImageView。
     */
    static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;

        public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask) {
            super(Color.BLACK);
            bitmapDownloaderTaskReference =
                new WeakReference<BitmapDownloaderTask>(bitmapDownloaderTask);
        }
        /**
         * 从 弱引用中 get()  
         * @return BitmapDownloaderTask
         */
        public BitmapDownloaderTask getBitmapDownloaderTask() {
            return bitmapDownloaderTaskReference.get();
        }
    }
    /**
     * 设置 mode，并清理 两个map中的缓存
     */
    /*public void setMode(Mode mode) {
        this.mode = mode;
        clearCache();
    }*/
    
    /*
     * Cache-related fields and methods.
     * 
     * We use a hard and a soft cache. A soft reference cache is too aggressively cleared by the
     * Garbage Collector.
     */
    
    private static final int HARD_CACHE_CAPACITY = 10; //hard map 初始因子
    private static final int DELAY_BEFORE_PURGE = 10 * 1000; // in milliseconds

    // Hard cache, with a fixed maximum capacity and a life duration 
    private final HashMap<String, Bitmap> sHardBitmapCache =
        new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY / 2, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(LinkedHashMap.Entry<String, Bitmap> eldest) {
            if (size() > HARD_CACHE_CAPACITY) {  
                // 将硬引用缓存都 转移到软引用缓存
                sSoftBitmapCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
                return true;
            } else
                return false;
        }
    };

    // Soft cache for bitmaps kicked out of hard cache
    private final static ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache =
        new ConcurrentHashMap<String, SoftReference<Bitmap>>(HARD_CACHE_CAPACITY / 2);

    private final Handler purgeHandler = new Handler();
    /**
     * 清除缓存 runnable
     */
    private final Runnable purger = new Runnable() {
        public void run() {
            clearCache();
        }
    };

    /**
     * Adds this bitmap to the  硬缓存 map
     * @param bitmap The newly downloaded bitmap.
     */
    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (sHardBitmapCache) {
                sHardBitmapCache.put(url, bitmap);
            }
        }
    }

    /** 从硬缓存里 得到Bitmap对象，    null 表示没有缓存对象
     * @param url The URL of the image that will be retrieved from the cache.
     * @return The cached bitmap or null if it was not found.
     */
    private Bitmap getBitmapFromCache(String url) {
        // First try the hard reference cache     
        synchronized (sHardBitmapCache) {
            final Bitmap bitmap = sHardBitmapCache.get(url);
            if (bitmap != null) {
                // Bitmap found in hard cache
                // Move element to first position, so that it is removed last
                sHardBitmapCache.remove(url);
                sHardBitmapCache.put(url, bitmap);
                return bitmap;
            }
        }

        // Then try the soft reference cache
        SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(url);
        if (bitmapReference != null) {
            final Bitmap bitmap = bitmapReference.get();
            if (bitmap != null) {
                // Bitmap found in soft cache
                return bitmap;
            } else {
                // Soft reference has been Garbage Collected   已经回收了
                sSoftBitmapCache.remove(url);
            }
        }

        return null;
    }
 
    /**
     * 清除 软硬集合里的 缓存 数据
     */
    private void clearCache() {
        sHardBitmapCache.clear();
        sSoftBitmapCache.clear(); 
    }

    /**
     *  重置  (先清理线程 purger，再延迟启动)
     */
    private void resetPurgeTimer() {
        purgeHandler.removeCallbacks(purger); //清除 线程purger
        purgeHandler.postDelayed(purger, DELAY_BEFORE_PURGE);//延迟10秒后启动 purger
    }

}
/*
 *总结下载过程：
	download(url , imageview)——>
　　　　　　　　　　@创建一个和该imageview相对应的下载任务，这个任务对imageview进行弱引用
　　　　　　　　　　@创建与这个任务相对应的DownloadedDrawable，对这个任务弱引用
　　　　　　　　　　@imageview加载DownloadedDrawable
　　　　　　　　　　@执行下载任务，下载对应url的图像 ——execute（url)进入下载任务类
　　　　在下载任务类中：
　　　　　　　　——>doInBackground  ===>downloadBitmap(url) 下载图片，结果Bitmap作为下面的参数
　　　　　　　　——>onPostExcute(Bitmap)
　　　　　　　　　　　　　　　@获得任务引用的imageview
　　　　　　　　　　　　　　　@通过DownloadedDrawable获得该imageview所对应的任务
　　　　　　　　　　　　　　　@如果当前任务是这个imageview所对应的任务，则设置这个imageview的图片为下载下
　　　　　　　　　　　　　　　　来的Bitmap
	imageview和任务相互弱引用，形成绑定关系
*/

