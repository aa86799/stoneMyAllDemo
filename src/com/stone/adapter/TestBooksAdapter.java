package com.stone.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.stone.receiver.Book;

public class TestBooksAdapter extends MyAdapter<Book>{


	public TestBooksAdapter(Context context, List<Book> list) {
		super(context, list);
		TestBooksAdapter booksAdapter = new TestBooksAdapter(context, list);
	}

	@Override
	public View produceView(int position, View convertView, ViewGroup parent) {
//		View view = LayoutInflater.from(mContext).inflate(resource, root)
//		Book book = mList.get(position);
		return null;
	}
	
}
