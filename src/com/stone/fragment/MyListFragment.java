package com.stone.fragment;

import com.stone.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MyListFragment extends ListFragment {
	
	 String[] presidents = {    //总统
		        "Dwight D. Eisenhower",     
		        "John F. Kennedy",     
		        "Lyndon B. Johnson",     
		        "Richard Nixon",     
		        "Gerald Ford",     
		        "Jimmy Carter",     
		        "Ronald Reagan",     
		        "George H. W. Bush",     
		        "Bill Clinton",     
		        "George W. Bush",     
		        "Barack Obama" 
		    };  
	 
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, presidents));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list, null);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Toast.makeText(getActivity(), presidents[position], 0).show();
	}
}
