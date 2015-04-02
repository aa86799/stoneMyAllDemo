package com.stone.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class NormalBaseAdapter<T> extends BaseAdapter {
	
	private List<T> mList;
	
	public void setDatas(List<T> data) {
		this.mList = data;
	}
	
	public List<T> getDatas() {
		return this.mList;
	}

	@Override
	public int getCount() {
		if (mList != null) {
			return mList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mList != null) {
			
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
