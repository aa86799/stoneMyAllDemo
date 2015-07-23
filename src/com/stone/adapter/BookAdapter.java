package com.stone.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stone.R;
import com.stone.receiver.Book;
import com.stone.util.EncodeUtil;
import com.stone.util.ImageUtil;

public class BookAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Book> list;
	private String imgUrl;
	private Map<Integer,WeakReference<Bitmap>> bitmaps;
	
	public BookAdapter(Context context, ArrayList<Book> list) {
		this.context = context;
		this.list = list;
		bitmaps = new HashMap<Integer, WeakReference<Bitmap>>();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.ivBook = (ImageView) convertView.findViewById(R.id.iv_book);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Book book = list.get(position);
		holder.tvTitle.setText(book.title);
		imgUrl = book.imgUrl; 
		final ImageView imgView = holder.ivBook;
				
		String imagePath = ImageUtil.getCacheImgPath().concat(EncodeUtil.md5(book.imgUrl) + ".png");
		Bitmap bitmap=null;
		//弱引用 中bitmap 有对象
		if (bitmaps.get(position) != null && (bitmap = bitmaps.get(position).get())!=null);
		else {//弱引用 中bitmap 没有对象，需要加载
			bitmap = ImageUtil.loadImage(imagePath, imgUrl, new ImageUtil.ImageCallback() {
				
				@Override
				public void loadImage(Bitmap bitmap) {
					imgView.setImageBitmap(bitmap);
				}
			}, 100, 100);
			if (bitmap != null) {
				//gc运行后  关联的bitmap对象  被回收，
				WeakReference<Bitmap>  weakReference = new WeakReference<Bitmap>(bitmap);
				bitmaps.put(position, weakReference);
			}
		}
		if (bitmap != null) {
			holder.ivBook.setImageBitmap(bitmap);
		} else {
			holder.ivBook.setImageResource(R.drawable.ic_launcher);// 默认图片
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView tvTitle;
		ImageView ivBook;
	}
}
