package com.stone.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	/*
	直接 new Gson().fromJson(jsonString, new TypeToken<List<User>>(){}.getType()) 这样使用是可以转成list的。
	但用new Gson().fromJson(jsonString, new TypeToken<List<T>>() 泛型 就错了。。  改成如下
	 */
	public static <T> List<T> getList(String jsonString, Class<T[]> clz) {

		List<T> list = null;
		try {
			T[] arr = new Gson().fromJson(jsonString, clz);
			list = Arrays.asList(arr);
		} catch (Exception e) {
		}
		return list;
	}



	public static <T> T getData(String jsonString, Class<T> cls) {
		T t = null;
		try {
			t = new Gson().fromJson(jsonString, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}

	/*
	 *  jsonString:{
	 *  			[
	 *  				{key:value, k:v}, 
	 *  				{k:v}
	 *  			]
	 *  		}
	 */
	public static <T> List<Map<String, T>> listKeyMaps(String jsonString) {
		List<Map<String, T>> list = new ArrayList<Map<String, T>>();
		try {
			list = new Gson().fromJson(jsonString,
					new TypeToken<List<Map<String, T>>>() {
					}.getType());
		} catch (Exception e) {

		}
		return list;
	}
}
