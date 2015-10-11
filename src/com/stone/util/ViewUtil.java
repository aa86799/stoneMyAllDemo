package com.stone.util;

import android.app.Activity;

public class ViewUtil {

    public static <T> T findViewById(Activity acti, int resid) {
		return (T) acti.findViewById(resid);
	}
}