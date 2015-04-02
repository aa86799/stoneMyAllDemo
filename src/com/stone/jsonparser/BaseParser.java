package com.stone.jsonparser;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public abstract class BaseParser<T> {
	/**
	 * 将json数据 转换为对象 
	 * @param paramString
	 * @return
	 * @throws JSONException
	 */
	public abstract T parseJSON(String paramString) throws JSONException;
	/**
	 * 将json数据 转换为对象 
	 * @param paramString
	 * @return
	 * @throws JSONException
	 */
	public abstract List<T> parseJSONArray(String paramString) throws JSONException;

	/**
	 * 检查响应的json数据
	 * @param res
	 * @throws JSONException
	 */
	public String checkResponse(String paramString) throws JSONException {
		if (paramString == null) {
			return null;
		} else {
			JSONObject jsonObject = new JSONObject(paramString);
			String result = jsonObject.getString("response");//{response:{..},}}
			if (!TextUtils.equals("error", result)) {
				return result;
			} else if (TextUtils.equals("error", result)){
				return "error";
			} else {
				return null;
			}
		}
	}
}
