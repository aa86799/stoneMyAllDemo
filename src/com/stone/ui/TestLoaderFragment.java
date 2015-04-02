package com.stone.ui;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;

import com.stone.R;
import com.stone.provider.SearchActionProvider;

/**
 * 装载器从android3.0开始引进。它使得在activity或fragment中异步加载数据变得简单。装载器具有如下特性：
 * 
 * 它们对每个Activity和Fragment都有效。
 * 
 * 他们提供了异步加载数据的能力。
 * 
 * 它们监视数据源的一将一动并在内容改变时传送新的结果。
 * 
 * 当由于配置改变而被重新创建后，它们自动重连到上一个加载器的游标，所以不必重新查询数据。
 * 
 * @author stone
 * 
 */
public class TestLoaderFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	/**
	 * 一个抽像类，关联到一个Activity或Fragment，管理一个或多个装载器的实例。
	 * 这帮助一个应用管理那些与Activity或Fragment的生命周期相关的长时间运行的的操作
	 * 。最常见的方式是与一个CursorLoader一起使用，然而应用是可以随便写它们自己的装载器以加载其它类型的数据。
	 * 每个activity或fragment只有一个LoaderManager。但是一个LoaderManager可以拥有多个装载器。
	 */
	LoaderManager mLoaderManager;
	/**
	 * 一个执行异步数据加载的抽象类。它是加载器的基类。你可以使用典型的CursorLoader，但是你也可以实现你自己的子类。一旦装载器被激活，
	 * 它们将监视它们的数据源并且在数据改变时发送新的结果。
	 */
	Loader mLoader;
	/**
	 * AsyncTaskLoader的子类，它查询ContentResolver然后返回一个Cursor。
	 * 这个类为查询cursor以标准的方式实现了装载器的协议，它的游标查询是通过AsyncTaskLoader在后台线程中执行，从而不会阻塞界面。
	 * 使用这个装载器是从一个ContentProvider异步加载数据的最好方式。
	 * 相比之下，通过fragment或activity的API来执行一个被管理的查询就不行了。
	 */
	CursorLoader mCursorLoader;
	/**
	 * 提供一个AsyncTask来执行异步加载工作的抽象类。
	 */
	AsyncTaskLoader mAsyncTaskLoader;

	SimpleCursorAdapter mAdapter;
	SearchView mSearchView;
	String mCurFilter;
	/** These are the Contacts rows that we will retrieve. */
	static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
			Contacts._ID, Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS,
			Contacts.LOOKUP_KEY, };

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setEmptyText("No phone numbers");

		// We have a menu item to show in action bar.
		setHasOptionsMenu(true);

		mAdapter = new SimpleCursorAdapter(
				getActivity(),
				android.R.layout.simple_list_item_2, //
				null,
				new String[] { Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS }, //
				new int[] { android.R.id.text1, android.R.id.text2 },
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		setListAdapter(mAdapter);
		setListShown(false);

		mLoaderManager = getLoaderManager();
		/*
		 * 会检查是否指定ID的装载器已经存在． 如果它不存在，将会触发LoaderManager.LoaderCallbacks
		 * 的方法onCreateLoader()
		 * 
		 * param1：指定一个唯一ID。param2：bundle。param3：LoaderCallbacks
		 */
		getLoaderManager().initLoader(1, null, this);

		// mLoader = mLoaderManager.restartLoader(0, null, this);
	}

	/**
	 * 跟据传入的ID，初始化并返回一个新的装载器
	 */
	@Override
	public Loader onCreateLoader(int id, Bundle args) {
		System.out.println("------------onCreateLoader----------------");
		// switch (id) {
		// case 1:
		// return new CursorLoader(getActivity());
		// case 2:
		// return new CursorLoader(getActivity());
		//
		// default:
		// return null;

		Uri baseUri;
		if (mCurFilter != null) {
			/*
			 * content://com.android.contacts/过滤文本-mCurFilter
			 */
			baseUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI,
					Uri.encode(mCurFilter));
		} else {
			/*
			 * content://
			 */
			baseUri = Contacts.CONTENT_URI;
		}

		// Now create and return a CursorLoader that will take care of
		// creating a Cursor for the data being displayed.
		String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
				+ Contacts.HAS_PHONE_NUMBER + "=1) AND ("
				+ Contacts.DISPLAY_NAME + " != '' ))";
		String order = Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		/*
		 * collate localized ASC 表示按本地语言校对 升序排序
		 * select = ( (display_name notnull) and (has_phone_number=1) and (display_name!='') ) collate localized ASC
		 */

		return new CursorLoader(getActivity(), baseUri,
				CONTACTS_SUMMARY_PROJECTION, select, null, order);
	}

	/**
	 * 当一个装载器完成了它的装载过程后被调用
	 */
	@Override
	public void onLoadFinished(Loader loader, Cursor data) {
		System.out.println("------------onLoadFinished----------------");
		mAdapter.swapCursor(data);

		// The list should now be shown.
		if (isResumed()) {
			setListShown(true);
		} else {
			setListShownNoAnimation(true);
		}
	}

	/**
	 * 当一个装载器被重置而其数据无效时被调用
	 */
	@Override
	public void onLoaderReset(Loader loader) {
		System.out.println("------------onLoaderReset----------------");
		mAdapter.swapCursor(null);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.cursor_main, menu);

		// Get the search item and update its action view
		MenuItem item = menu.findItem(R.id.action_search);
		mSearchView = (android.widget.SearchView) item.getActionView();
		mSearchView.setOnQueryTextListener(queryListener);
		mSearchView.setOnCloseListener(closeListener);
		mSearchView.setIconifiedByDefault(true);
		mSearchView.setQueryHint("please input");
		
		SearchActionProvider SearchActionProvider = (SearchActionProvider)item.getActionProvider();
		
	}

	/**
	 * The {@link OnCloseListener} called when the SearchView is closed. Resets
	 * the query field.
	 */
	private SearchView.OnCloseListener closeListener = new SearchView.OnCloseListener() {

		@Override
		public boolean onClose() {
			// Restore the SearchView if a query was entered
			if (!TextUtils.isEmpty(mSearchView.getQuery())) {
				mSearchView.setQuery(null, true);
			}
			return true;
		}
	};

	/**
	 * The {@link OnQueryTextListener} that is called when text in the
	 * {@link SearchView} is changed. Updates the query filter and triggers a
	 * reload of the {@link LoaderManager} to update the displayed list.
	 */
	private OnQueryTextListener queryListener = new OnQueryTextListener() {

		/**
		 * Called when the action bar search text has changed. Update the search
		 * filter, and restart the loader to do a new query with this filter.
		 * 
		 * @param newText
		 *            the new content of the query text field
		 * @return true, the action has been handled.
		 */
		public boolean onQueryTextChange(String newText) {

			String newFilter = !TextUtils.isEmpty(newText) ? newText : null;

			// Don't do anything if the filter hasn't actually changed.
			// Prevents restarting the loader when restoring state.
			if (mCurFilter == null && newFilter == null) {
				return true;
			}
			if (mCurFilter != null && mCurFilter.equals(newFilter)) {
				return true;
			}

			// Restart the Loader.
			// #onCreateLoader uses the value of mCurFilter as a filter when
			// creating the query for the Loader.
			mCurFilter = newFilter;
			getLoaderManager().restartLoader(0, null, TestLoaderFragment.this);

			return true;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			// Don't care about this.
			return true;
		}
	};

	class MyLoader extends Loader {

		public MyLoader(Context context) {
			super(context);
		}

	}

}
