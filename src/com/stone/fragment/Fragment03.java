package com.stone.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stone.R;
import com.stone.util.LogUtils;

public class Fragment03 extends Fragment {
	private final String TAG = "Fragment03";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LogUtils.printInfo(TAG, "----------onCreateView----------");
		return inflater.inflate(R.layout.fragment03, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		LogUtils.printInfo(TAG, "----------onActivityCreated----------");
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		LogUtils.printInfo(TAG, "----------onActivityCreated----------");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtils.printInfo(TAG, "----------onActivityCreated----------");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtils.printInfo(TAG, "----------onActivityCreated----------");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		LogUtils.printInfo(TAG, "----------onActivityCreated----------");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		LogUtils.printInfo(TAG, "----------onActivityCreated----------");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtils.printInfo(TAG, "----------onActivityCreated----------");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtils.printInfo(TAG, "----------onActivityCreated----------");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtils.printInfo(TAG, "----------onActivityCreated----------");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogUtils.printInfo(TAG, "----------onActivityCreated----------");
	}

	
}