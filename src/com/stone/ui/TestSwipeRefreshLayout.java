package com.stone.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.stone.R;

public class TestSwipeRefreshLayout extends Activity {
	SwipeRefreshLayout swipeView;
	ListView listView;
	List<String> datas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		generateRandomNum();
		
		
		setContentView(R.layout.swipe_refresh_layout_listview);
		datas = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			datas.add("中华人民共和国" + i);
		}
		swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
		swipeView.setEnabled(false);
		listView = (ListView) findViewById(R.id.lv_listview);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, datas));
		
		
		swipeView.setColorScheme(android.R.color.holo_blue_dark,
				android.R.color.holo_blue_light,
				android.R.color.holo_green_light,
				android.R.color.holo_green_light);
		
		swipeView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				swipeView.setRefreshing(true);
				( new Handler()).postDelayed(new Runnable() {
	                @Override
	                public void run() {
	                    swipeView.setRefreshing(false);
	                }
	            }, 3000);
			}
		});
		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
 
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeView.setEnabled(true);
                else
                    swipeView.setEnabled(false);
            }
		});
	}

	private void generateRandomNum() {
		setContentView(R.layout.swipe_refresh_layout_randomnum);

		swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
		final TextView rndNum = (TextView) findViewById(R.id.rndNum);

		swipeView.setColorScheme(android.R.color.holo_blue_dark,
				android.R.color.holo_blue_light,
				android.R.color.holo_green_light,
				android.R.color.holo_green_light);

		swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						swipeView.setRefreshing(true);
						Log.d("Swipe", "Refreshing Number");
						(new Handler()).postDelayed(new Runnable() {
							@Override
							public void run() {
								swipeView.setRefreshing(false);
								double f = Math.random();
								rndNum.setText(String.valueOf(f));
							}
						}, 3000);
					}
				});
	}

}