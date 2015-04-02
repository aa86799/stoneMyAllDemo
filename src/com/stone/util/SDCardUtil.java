package com.stone.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class SDCardUtil {
	private Context context;
	
	public SDCardUtil(Context context) {
		this.context = context;
	}
	
	/*
	 * 获取sdcard状态
	 */
	public boolean readSIMCard() {
		TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if(isAirplaneModeOn()){
			return true;
		}
		switch(telMgr.getSimState()){ //getSimState()取得sim的状态  有下面6中状态
            case TelephonyManager.SIM_STATE_ABSENT :	//无卡
            	return false;
            case TelephonyManager.SIM_STATE_UNKNOWN ://未知状态
            	return false;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED ://需要NetworkPIN解锁
            	return false;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED ://需要PIN解锁
            	return false;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED ://需要PUK解锁
            	return false;
            case TelephonyManager.SIM_STATE_READY :
            	return true;
		}
		return false;
	}
	/*
	 * 是否开启飞行模式
	 */
    public boolean isAirplaneModeOn() {  
        return Settings.System.getInt(context.getContentResolver(),  
        				Settings.System.AIRPLANE_MODE_ON, 0) != 0;  
 	}  
}	
