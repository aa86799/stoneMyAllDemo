package com.stone.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class SIMUtil {
	private static Context context;
	public SIMUtil(Context context) {
		context = context;
	}
	private TelephonyManager telMgr;
	/*
	 * 获得本机IMSI号   国际移动用户识别码
	 */
	public String loadingGetIMSI() {
		telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telMgr.getSubscriberId();
		if(imsi==null || "".equals(imsi.trim()))return "na";
		return imsi;
	}
	/*
	 * 获得手机IMEI号	国际移动设备身份码
	 */
  	public  String loadingGetIMEI() {
		telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telMgr.getDeviceId();
		return imei;
	}
	/*
  	 * 获取手机的制式
  	 */
	public String getSIMType(){
		String simType = null;
		telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); 
		int type = telMgr.getNetworkType();
		
		if (type == TelephonyManager.NETWORK_TYPE_EDGE) {//移动2G: 类型为EDGE定义为EDGE的SIM卡 ,通用分组无线服务（2.5G）
			simType = "SIM";
		}else  if (type == TelephonyManager.NETWORK_TYPE_GPRS) {// 联通2G:类型为GPRS定义为GPRS的SIM卡,通用分组无线服务（2.5G
			simType = "SIM";
		}else if (type == TelephonyManager.NETWORK_TYPE_UMTS //联通3G:// 类型为UMTS定义为WCDMA的USIM卡 , 全球移动通信系统（3G）
				|| type==TelephonyManager.NETWORK_TYPE_HSDPA//HSDPA:超3G业务,WCDMA发展趋势(下行链路),移动:全球通、TD
				|| type==TelephonyManager.NETWORK_TYPE_HSUPA//HSUPA:超3G业务,WCDMA发展趋势(上行链路)
				|| type==TelephonyManager.NETWORK_TYPE_HSPA) {
			simType = "SIM";
		}else if(type==TelephonyManager.NETWORK_TYPE_1xRTT || type==TelephonyManager.NETWORK_TYPE_CDMA){//电信2G:定义为CDMA的UIM卡
			simType = "UIM";
		}else if(type==TelephonyManager.NETWORK_TYPE_EVDO_A || type==TelephonyManager.NETWORK_TYPE_EVDO_0){// 电信3G:类型定义为cdma2000的UIM卡 (天翼卡)　
			simType = "UIM";
		}else if(type==TelephonyManager.NETWORK_TYPE_UNKNOWN){
			simType = "unknown";
		}else{
			simType = "SIM";
		}
		return simType;
	}
	/*
	 * 获得手机运营商
	 */
	public  String checkType(){
		String imsi = loadingGetIMSI();
		if(imsi!=null){
	        if(imsi.startsWith("46000") || imsi.startsWith("46002")){//因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
	        	//中国移动
	        	return "YD";
	        }else if(imsi.startsWith("46001")){//中国联通
	        	return "LT";
	        }else if(imsi.startsWith("46003")){//中国电信
	        	return "DX";
	        }
		}
		return "";
	}
	/*
	 * 是否开启飞行模式
	 */
    public boolean isAirplaneModeOn() {  
        return Settings.System.getInt(context.getContentResolver(),  
        				Settings.System.AIRPLANE_MODE_ON, 0) != 0;  
 	}  
}
