package com.stone.ui;

import com.stone.R;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

public class PopupWindowActivity extends Activity {
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_activity);
        LayoutInflater inflater = LayoutInflater.from(this);
        
        // 引入窗口配置文件
        View view = inflater.inflate(R.layout.popupwindow, null);
        // 创建PopupWindow对象
        final PopupWindow pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
        final Button btn = (Button) findViewById(R.id.btn);
        // 需要设置一下此参数，点击 外边可消失
        Drawable d = getResources().getDrawable(R.drawable.a2);
        pop.setBackgroundDrawable(d);
        //设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(pop.isShowing()) {
					// 隐藏窗口，如果设置了点击窗口外时隐藏,该方式可以不需要
					pop.dismiss();
				} else {
//					pop.showAsDropDown(v);// 显示窗口 在v下面
					/*
					 * show at parentView,  gravity 右上， x(左|右)偏移，y(上|下)偏移
					 */
					pop.showAtLocation(getWindow().getDecorView(), 
							Gravity.RIGHT|Gravity.TOP, btn.getWidth()/2, btn.getHeight()/2);
				}
				
			}
		});
    }
}