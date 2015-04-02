package com.stone.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.stone.R;
import com.stone.inject.InjectView;
import com.stone.inject.Injector;
import com.stone.util.DialogUtil;

public class GalleryTestActivity extends Activity implements ViewFactory {
	@InjectView(R.id.gy_gallery)
	private Gallery gallery;
	@InjectView(R.id.is_switcher)
	private ImageSwitcher imageSwitcher;//一次只能显示一张图
	
	private int[] imagesIds = { 
			R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6,R.drawable.a7, //
			R.drawable.a8 };
	ImageView lastView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		Injector.get(this).inject();//init views
		
		//factory 只能设置 一个
		this.imageSwitcher.setFactory(GalleryTestActivity.this); //设置 factory
		
		ImageAdapter adapter = new ImageAdapter(this);
		this.gallery.setAdapter(adapter);
		this.gallery.setBackgroundColor(Color.CYAN);
		this.gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (lastView!=null) {
					lastView.setLayoutParams(new Gallery.LayoutParams(120,120));
					lastView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				}
				ImageView view2 = (ImageView) view;
				view2.setLayoutParams(new Gallery.LayoutParams(200,Gallery.LayoutParams.FILL_PARENT));
				view2.setScaleType(ImageView.ScaleType.FIT_CENTER);
				lastView = view2;
				
				DialogUtil.showToast(GalleryTestActivity.this, "选择的图片位置是：" + position);
				imageSwitcher.setImageResource(imagesIds[position]);
			} 
		});
	}
	
	private class ImageAdapter extends BaseAdapter {
		private Context context;
		
		public ImageAdapter(Context context) {
			this.context = context;
		}
		@Override
		public int getCount() {
			return imagesIds.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(imagesIds[position]);
			imageView.setLayoutParams(new Gallery.LayoutParams(120,120));
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			return imageView;
		}
	}
	
	@Override // android.widget.ViewSwitcher. makeView()
	public View makeView() {
		ImageView imageView = new ImageView(this);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		return imageView;
	}
}
