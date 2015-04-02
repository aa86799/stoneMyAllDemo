package com.google.bitmapfun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.google.bitmapfun.ImageCache.ImageCacheParams;
import com.stone.R;


public class ImageActivity extends FragmentActivity {
	private final String TAG = ImageActivity.class.getSimpleName();
	
	private String IMAGE_CACHE_DIR = "thumbs"; // 图片缓存目录
	private int mImageThumbSize; // 图存size
	private int mImageThumbSpacing; // 图片space
	private int mImageWidth;
	private int mImageHeight;
	private ImageFetcher mImageFetcher; //

	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_activity);
		
		mImageView = (ImageView) findViewById(R.id.iv_display);
		
		setImageProperties();
		
		String url = getIntent().getStringExtra("imageUrl");
		mImageFetcher.loadImage(url, mImageView);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		String url = getIntent().getStringExtra("imageUrl");
		mImageFetcher.loadImage(url, mImageView);
	}
	
	private void setImageProperties() {
		mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
		mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
		ImageCacheParams cacheParams = new ImageCacheParams(this,IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageFetcher = new ImageFetcher(this, mImageThumbSize);
//		mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		mImageFetcher.addImageCache(this.getSupportFragmentManager(), cacheParams);
		mImageFetcher.setImageSize(mImageWidth, mImageHeight);
	}

	
	private void setPauseWork(boolean flag) {
		mImageFetcher.setPauseWork(flag);
	}
	
	private void clearCache() {
		mImageFetcher.clearCache();
	}

	@Override
	public void onResume() {
		super.onResume();
		mImageFetcher.setExitTasksEarly(false);
	}

	@Override
	public void onPause() {
		super.onPause();
		mImageFetcher.setPauseWork(false);
		mImageFetcher.setExitTasksEarly(true);
		mImageFetcher.flushCache();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mImageFetcher.closeCache();
	}
}
