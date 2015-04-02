package com.stone.provider;

import com.stone.R;

import android.content.Context;
import android.view.ActionProvider;
import android.view.View;
import android.widget.EditText;

/*
 * ActionProvider support-v4下也有 跟actionbar一样
 * 
 */
public class SearchActionProvider extends ActionProvider {
	
	private Context mContext;
	private EditText mEditText;
	
	public SearchActionProvider(Context context) {
		super(context);
		
		mEditText = (EditText) View.inflate(context, R.layout.search_action_view, null);
		this.mContext = context;
	}

	@Override
	public View onCreateActionView() {
		
		return mEditText;
	}

}
