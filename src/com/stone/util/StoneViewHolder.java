package com.stone.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/24 14 27
 */
public class StoneViewHolder {

    private int mPosition;
    private View mConvertView;
    private SparseArray<View> mViews;  //管理listView-item中的view

    //    private SparseArrayCompat    //support.v4.util.SparseArrayCompat 提供了v4的平台22的 SparseArray实现
    /*
    SparseArray 内部实现是Array数组。当长度不够时，会调用System.arrayCopy
        内部有 keys和values两个数组。
        put(key, value); 二分法查找key应该存放的位置  因为key是Integer类型
        put、get时 两个数组都是操作的同一个位置上的数据

    SparseArray 用于替代形如  HashMap<Integer, Object>
    SparseBooleanArray 用于替代形如  HashMap<Integer, Boolean>
    SparseIntArray 用于替代形如  HashMap<Integer, Integer>
    SparseLongArray 用于替代形如  HashMap<Integer, Long>
     */


    public StoneViewHolder(Context context, int layoutId, int position, ViewGroup parent) {
        this.mPosition = position;

        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);

        this.mViews = new SparseArray<View>();
    }

    public View getConvertView() {
        return mConvertView;
    }

    public static StoneViewHolder getInstance(Context context, int layoutId, int position, View
            convertView, ViewGroup parent) {
        if (convertView == null) {
            return new StoneViewHolder(context, layoutId, position, parent);
        } else {
            StoneViewHolder holder = (StoneViewHolder) convertView.getTag();
            holder.mPosition = position;  //更新复用的convertView中  position
            return holder;
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public <T> void setTag(int viewId, T tag) {
        getView(viewId).setTag(tag);
    }

    public <T> T getTag(int viewId) {
        return (T) getView(viewId).getTag();
    }

    /*------------------------  设置view属性(以后扩展) --------------------------------*/

    public StoneViewHolder setText(int viewId, String text) {
        ((TextView) getView(viewId)).setText(text);
        return this;
    }

    public StoneViewHolder setText(int viewId, int resId) {//R.string.
        ((TextView) getView(viewId)).setText(resId);
        return this;
    }

    public StoneViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    public StoneViewHolder setImageResource(int viewId, int resId) {
        ((ImageView) getView(viewId)).setImageResource(resId);
        return this;
    }
}
