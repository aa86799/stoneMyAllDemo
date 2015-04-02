package com.stone.view;

import com.stone.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

/*
 * 复合组件 
 */
public class CustomView2 extends LinearLayout implements OnCheckedChangeListener {
	EditText mText;
	ToggleButton mToggleButton;
	
	public CustomView2(Context context) {
		super(context);
		init(context);
		System.out.println("000aaa");
	}
	
	public CustomView2(Context context, AttributeSet attrs) {
//		super(context, attrs);
		/*
		 * 
		 */
		this(context, attrs, android.R.style.Theme_Black);
		System.out.println("000bbb");
//		init(context);
	}

	public CustomView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		System.out.println("000ccc");
	}



	private void init(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		//将layout inflate 到当前viewgroup下
		View view = inflater.inflate(R.layout.customview2, this);
		
		mText = (EditText) view.findViewById(R.id.et);
		mToggleButton = (ToggleButton) view.findViewById(R.id.tb);
		mToggleButton.setChecked(true);
		mToggleButton.setOnCheckedChangeListener(this);
		
		setOrientation(LinearLayout.HORIZONTAL);
	}

	@Override //OnCheckedChangeListener
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		mText.setEnabled(isChecked);
	}
	
	
}
