package com.stone.ui;

import java.util.UUID;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.receiver.Book;
import com.stone.util.DBHelper;

public class TestSqliteActivity extends Activity {
	FragmentManager fm = getFragmentManager();
	FragmentTransaction ft = fm.beginTransaction();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		ActionBar actionBar = getActionBar();
		actionBar.show();
	}
	
	//初始化view
	private void initView() {
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		Button insertBtn = new Button(this);
		insertBtn.setLayoutParams(new LinearLayout.LayoutParams(//
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		insertBtn.setText("插入一条book记录");
		final Book book = new Book();
		book.title = "今天 你升了没";
		book.imgUrl = "http://image.33669.com/www/2012/02/28/blg8.jpg";
		
		final TextView tvInfo = new TextView(this);
		tvInfo.setLayoutParams(new LinearLayout.LayoutParams(//
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		insertBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				book.id = UUID.randomUUID().toString();
				DBHelper.getDBHelper(TestSqliteActivity.this).insert(book, "book", "id");
				System.out.println("点击了插入，" + "book.id="+book.id +",book.title="+book.title+",book.imgUrl="+book.imgUrl);
				Book object = (Book)DBHelper.getDBHelper(TestSqliteActivity.this).query(Book.class, "book", new String[]{"id","title","imgUrl"},//
						"id=?", new String[]{book.id});
				if (object == null) return;
				tvInfo.setText(object.id+"---"+object.title+"---"+object.imgUrl);
			}
		});
		
		
		Button clearBtn = new Button(this);
		clearBtn.setLayoutParams(new LinearLayout.LayoutParams(//
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		clearBtn.setText("清除book记录");
		clearBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int num = DBHelper.getDBHelper(TestSqliteActivity.this).clearDatas("book", null, null);
				if (num >=0 ) {
					tvInfo.setText("");
					Toast.makeText(getApplicationContext(), "delete success.", 0).show();
				}
			}
		});
		
		linearLayout.addView(insertBtn);
		linearLayout.addView(clearBtn);
		linearLayout.addView(tvInfo);
		setContentView(linearLayout);
	}
	
}
