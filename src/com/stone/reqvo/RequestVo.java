package com.stone.reqvo;

import java.util.HashMap;

import android.content.Context;

import com.stone.jsonparser.BaseParser;
/**
 *	请求实体  封装了请求的url，Context，请求的map值集，对应的jsonParser
 */
public class RequestVo {
	public int requestUrl; //请求地址
	public Context context; //
	public HashMap<String,String> requestDataMap; //请求的数据
	public BaseParser<?> jsonParser; // json 解析器
}
