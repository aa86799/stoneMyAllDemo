package com.stone.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 计算公式 pixels = dips * (density / 160)
 * 
 */
public class DensityUtil {
	
	private static final String TAG = DensityUtil.class.getSimpleName();
	
	// 当前屏幕的densityDpi
	private float dmDensityDpi = 0.0f;
	private DisplayMetrics dm;
	private float scale = 0.0f;

	public static class Builder {
		private static DensityUtil instance;


		public static DensityUtil build(Context context) {
			if (instance == null) {
				instance = new DensityUtil(context);
			}
			return instance;
		}
	}

	/**
	 * 
	 * 根据构造函数获得当前手机的屏幕系数
	 * 
	 * */
	private DensityUtil(Context context) {
		// 获取当前屏幕
		dm = context.getApplicationContext().getResources().getDisplayMetrics();
		// 设置DensityDpi
		setDmDensityDpi(dm.densityDpi);
		// 密度因子
		scale = getDmDensityDpi() / 160;
		LogUtils.printInfo(TAG, toString());
	}

	/**
	 * 当前屏幕的density因子
	 * 
	 * @param DmDensity
	 * @retrun DmDensity Getter
	 * */
	public float getDmDensityDpi() {
		return dmDensityDpi;
	}

	/**
	 * 当前屏幕的density因子
	 * 
	 * @param DmDensity
	 * @retrun DmDensity Setter
	 * */
	public void setDmDensityDpi(float dmDensityDpi) {
		this.dmDensityDpi = dmDensityDpi;
	}

	/**
	 * 密度转换像素
	 * */
	public int dip2px(float dipValue) {
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 像素转换密度
	 * */
	public int px2dip(float pxValue) {
		return (int) (pxValue / scale + 0.5f);
	}

	@Override
	public String toString() {
		return " dmDensityDpi:" + dmDensityDpi + ", densityScale:" + scale;
	}
}