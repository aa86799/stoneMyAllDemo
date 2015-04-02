package com.stone.util;

import java.io.File;
import java.text.DecimalFormat;

import android.os.Environment;
import android.os.StatFs;

/**
 * 是否有存储卡
 *
 */
public class GetStorage {
	private final String TAG = "GetStorage";
	public void util() {
		// 判断是否有插入存储卡
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File path = Environment.getExternalStorageDirectory();
			// 取得sdcard文件路径
			StatFs statfs = new StatFs(path.getPath());
			// 获取block的SIZE ，
			long blocSize = statfs.getBlockSize();
			// 获取BLOCK数量
			long totalBlocks = statfs.getBlockCount();
			// 己使用的Block的数量
			long availaBlock = statfs.getAvailableBlocks();//获取当前可用的存储空间
			String[] total = filesize(totalBlocks * blocSize);
			String[] availale = filesize(availaBlock * blocSize);

		} else if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_REMOVED)) {
			LogUtils.printInfo(TAG, "没有sd卡");
		} 
		//Environment.MEDIA_UNMOUNTED   有存储卡，已经卸载了
	}
	// 返回数组，下标1代表大小，下标2代表单位 KB/MB

	private String[] filesize(long size) {
		String str = "";
		if (size >= 1024) {
			str = "KB";
			size /= 1024;
			if (size >= 1024) {
				str = "MB";
				size /= 1024;
			}
		}
		DecimalFormat formatter = new DecimalFormat();
		formatter.setGroupingSize(3);
		String result[] = new String[2];
		result[0] = formatter.format(size);
		result[1] = str;
		return result;
	}
}
