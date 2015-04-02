package com.stone.jsonparser;

import java.util.List;

import org.json.JSONException;

import com.alibaba.fastjson.JSONObject;
import com.stone.receiver.Book;

public class BookParser extends BaseParser<Book> {

	@Override
	public Book parseJSON(String paramString) throws JSONException {
		Book parseObject = JSONObject.parseObject(paramString, Book.class);
		return parseObject;
	}
	
	@Override
	public List<Book> parseJSONArray(String paramString) throws JSONException {
		List<Book> list = JSONObject.parseArray(paramString, Book.class);
		return list;
	}
	
}
