package com.stone.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.Loader.OnLoadCompleteListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;

import com.stone.R;

/*
 * 每个Activity和Fragment中，都会有且只有一个LoaderManager，而LoaderManager中可以有多个Loader
 */
public class ListViewActivityByLoader extends FragmentActivity implements OnQueryTextListener {

	private LoaderManager loaderManager;
	MyLoader loader;
	int loaderid = 0xff;
	SimpleCursorAdapter mAdapter;  
	String mCurFilter;  //搜索过虑器  
	ListView listView;

	// 这是我们想获取的联系人中一行的数据．  
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {  
        Contacts._ID,  
        Contacts.DISPLAY_NAME,  
        Contacts.CONTACT_STATUS,  
        Contacts.CONTACT_PRESENCE,  
        Contacts.PHOTO_ID,  
        Contacts.LOOKUP_KEY,  
    }; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);   // 无标题栏
		setContentView(R.layout.listview);
		loaderManager = getLoaderManager();
		
		//第1种构建
		loader = new MyLoader(this);
		loader.registerListener(0xcc, loadListerner);loader.loadInBackground();
		
		//第2种，通过loaderManager构建LoaderCallbacks   // 准备装载器．可以重连一个已经存在的也可以启动一个新的．  
		loaderManager.initLoader(loaderid, null, new LoaderCallbacks<String>() {

			@Override
			public Loader<String> onCreateLoader(int id, Bundle args) {
				return null;
			}

			@Override
			public void onLoadFinished(Loader<String> loader, String data) {
				
			}

			@Override
			public void onLoaderReset(Loader<String> loader) {//装载器被重置
				
			}
		});
		
		
		//实例 
		// 创建一个空的adapter，我们将用它显示加载后的数据  
        mAdapter = new SimpleCursorAdapter(this,  
                android.R.layout.simple_list_item_2, null,  
                new String[] { Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS },  
                new int[] { android.R.id.text1, android.R.id.text2 }, 0);  
        listView = (ListView) findViewById(R.id.lv_listview);
        listView.setAdapter(mAdapter);  
		loaderManager.initLoader(loaderid, null, loaderCallbacks);
		
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}
	
	
	
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		MenuItem item = menu.add("Search");  
		item.setIcon(android.R.drawable.ic_menu_search);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		SearchView sv = new SearchView(this);
		sv.setOnQueryTextListener(this); 
		item.setActionView(sv);  
		return true;
		
	};
	
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println(item.getTitle());
		SearchView sv = (SearchView) item.getActionView();
		sv.setQuery(mCurFilter="aa", true);
		return true;
	};
	
	LoaderCallbacks loaderCallbacks = new LoaderCallbacks<Cursor>() {
		
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			  // 当一个新的loader需被创建时调用．本例仅有一个Loader，  
	        //所以我们不需关心ID．首先设置base URI，URI指向的是联系人  
	        Uri baseUri = null;  
	        if (mCurFilter != null) {  
	            baseUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI,  
	                    Uri.encode(mCurFilter));  
	        } else {  
	            baseUri = Contacts.CONTENT_URI;  
	        }  
	  
	        // 现在创建并返回一个CursorLoader，它将负责创建一个  
	        // Cursor用于显示数据  
	        String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("  
	                + Contacts.HAS_PHONE_NUMBER + "=1) AND ("  
	                + Contacts.DISPLAY_NAME + " != '' ))";  
	        return new CursorLoader(getApplicationContext(), baseUri,  CONTACTS_SUMMARY_PROJECTION, select, null,  
	                Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");  
		}
		
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			 // 将新的cursor换进来．(框架将在我们返回时关心一下旧cursor的关闭)  
	        mAdapter.swapCursor(data);  
		}
		
		@Override
		public void onLoaderReset(Loader<Cursor> loader) {//装载器被重置
	        //在最后一个Cursor准备进入上面的onLoadFinished()之前．  
	        // Cursor要被关闭了，我们需要确保不再使用它．  
	        mAdapter.swapCursor(null);  
		}
	};
	
	OnLoadCompleteListener loadListerner = new OnLoadCompleteListener<String>() {

		@Override
		public void onLoadComplete(Loader<String> loader, String data) {
			System.out.println("AsyncTask执行 加载完成 ");
		}
	};
	
	class MyLoader extends AsyncTaskLoader<String> {//AsyncTaskLoader 内部有AsyncTask

		public MyLoader(Context context) {
			super(context);
		}

		@Override
		public String loadInBackground() {//必须实现父类的abstract方法，它被AsyncTask.doInBackground()调用 
			return null;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		loader.stopLoading();
		loader.cancelLoad();
		loader.unregisterListener(loadListerner);
		loaderManager.destroyLoader(loaderid);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// 在动作栏上的搜索字串改变时被调用．更新  搜索过滤器，并重启loader来执行一个新的查询 
		mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;  
		loaderManager.restartLoader(0, null, loaderCallbacks); 
		System.out.println("-----onQueryTextChange");
		return false;
	}
}