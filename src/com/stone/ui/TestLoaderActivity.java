package com.stone.ui;

import com.stone.R;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.renderscript.Sampler;
import android.widget.SimpleCursorAdapter;

/**
 * 装载器从android3.0开始引进。它使得在activity或fragment中异步加载数据变得简单。装载器具有如下特性：
 * 
 * 它们对每个Activity和Fragment都有效。
 * 
 * 他们提供了异步加载数据的能力。
 * 
 * 它们监视数据源的一举一动并在内容改变时传送新的结果。
 * 
 * 当由于配置改变而被重新创建后，它们自动重连到上一个加载器的游标，所以不必重新查询数据。
 * 
 * @author stone
 * 
 */
public class TestLoaderActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty);
//		getFragmentManager().beginTransaction().add(android.R.id.content, new TestLoaderFragment(), "").commit();
		
		
	}

	
}
