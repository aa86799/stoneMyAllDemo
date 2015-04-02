package com.stone.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyAdapter<T> extends BaseAdapter {
	
	protected Context mContext;
	protected List<T> mList;
	public MyAdapter(Context context, List<T> list) {
		this.mList = list;
		if (this.mList == null) {
			this.mList = new ArrayList<T>();
		}
	}

	public void setList(List<T> list) {
		this.mList = list;
	}
	
	public List<T> getList() {
		return mList;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return produceView(position, convertView, parent);
	}
	
	public abstract View produceView(int position, View convertView, ViewGroup parent);
}
