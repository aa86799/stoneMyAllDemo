package com.stone.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/24 14 46
 */
public abstract class StoneListAdapter<T> extends BaseAdapter {

    private List<T> mData;
    private Context mContext;
    private int mLayoutID;

    public StoneListAdapter(Context context, int layoutID, List<T> data) {
        this.mContext = context;
        this.mLayoutID = layoutID;
        this.mData = data == null ? new ArrayList<T>() : data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoneViewHolder holder = StoneViewHolder.getInstance(mContext, mLayoutID, position,
                convertView, parent);

        getView(mContext, holder, position);


        return holder.getConvertView();
    }

    protected abstract void getView(Context context, StoneViewHolder holder, int position);

}
