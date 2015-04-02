package com.stone.ui;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;

import com.stone.jsonparser.BookParser;
import com.stone.receiver.Book;
import com.stone.reqvo.RequestVo;

/**
 *  base acti test
 *
 */
public class BookActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	protected void initView() {
		
	}

	@Override
	protected void addListener() {
		processLogic();
	}

	@Override
	protected void processLogic() {
		RequestVo vo = new RequestVo();
		vo.context = this;
		vo.requestDataMap = new HashMap<String, String>();
//		vo.requestDataMap.put...
		vo.jsonParser = new BookParser();
		getDataFromServer(vo, new IDataCallback<List<Book>>() {

			@Override
			public void processData(List<Book> paramObject, boolean paramBoolean) {
				
			}
		});
	}
	
	

}
