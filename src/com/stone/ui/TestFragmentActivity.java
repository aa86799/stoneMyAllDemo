package com.stone.ui;

import java.lang.reflect.Method;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.stone.R;
import com.stone.fragment.Fragment01;
import com.stone.fragment.Fragment02;
import com.stone.fragment.Fragment03;
import com.stone.fragment.MyDialogFragment;
import com.stone.fragment.MyDialogFragment.MyAlertDialogFragment;
import com.stone.fragment.MyListFragment;
import com.stone.util.LogUtils;

/*
 * 警告：你只能在activity处于可保存状态的状态时，比如running中，onPause()方法和onStop()方法之前提交事务，否则会引发异常。
 * 这是因为fragment	的状态会丢失。如果要在可能丢失状态的情况下提交事务，请使用commitAllowingStateLoss()。
 * 
 * ViewPager+Fragment时，需要用到FragmentPagerAdapter或FragmentStatePagerAdapter
 * FragmentPagerAdapter 里面的fragment 当换页时会缓存起来。在view上的表现只是detached
 * FragmentStatePagerAdapter
 */
public class TestFragmentActivity extends FragmentActivity implements OnClickListener{
	private final String TAG = "TestFragmentActivity";
	Button btnTitle1, btnTitle2, btnTitle3;
	Button btnOption1, btnOption2, btnOption3, btnOption4;
	int mStackLevel;
	Fragment mFragment;
	Fragment mFragment1;
	Fragment mFragment2;
	Fragment mFragment3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		btnTitle1 = (Button) findViewById(R.id.btn_title1);
		btnTitle2 = (Button) findViewById(R.id.btn_title2);
		btnTitle3 = (Button) findViewById(R.id.btn_title3);
		btnOption1 = (Button) findViewById(R.id.btn_option1);
		btnOption2 = (Button) findViewById(R.id.btn_option2);
		btnOption3 = (Button) findViewById(R.id.btn_option3);
		btnOption4 = (Button) findViewById(R.id.btn_option4);
		btnTitle1.setOnClickListener(this);
		btnTitle2.setOnClickListener(this);
		btnTitle3.setOnClickListener(this);
		btnOption1.setOnClickListener(this);
		btnOption2.setOnClickListener(this);
		btnOption3.setOnClickListener(this);
		btnOption4.setOnClickListener(this);
		
		
		Toast.makeText(this, "is SCREENLAYOUT_SIZE_XLARGE="+isTabletDevice(), 1).show();
	}

	//判断是平板还是phone
	@SuppressWarnings("unused")
	private boolean isTabletDevice() {
		try {
			if (android.os.Build.VERSION.SDK_INT >= 11) { // honeycomb
				// test screen size, use reflection because isLayoutSizeAtLeast
				// is
				// only available since 11
				Configuration con = getResources().getConfiguration();
				Method mIsLayoutSizeAtLeast = con.getClass().getMethod(
						"isLayoutSizeAtLeast", int.class);

				Boolean isLarge = (Boolean) mIsLayoutSizeAtLeast.invoke(con,
						0x00000004); // Configuration.SCREENLAYOUT_SIZE_XLARGE
				return isLarge;
			} else {
				return false;
			}
		} catch (Exception x) {
			x.printStackTrace();
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_title1:
			addFragment1();
			break;
			
		case R.id.btn_title2:
			addFragment2();
			break;
			
		case R.id.btn_title3:
			addFragment3();
			break;
		case R.id.btn_option1:
			showDialog();
			break;
		case R.id.btn_option2:
			showDialog2();
			break;
			
		case R.id.btn_option3:
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction(); 
			Fragment fragment = getSupportFragmentManager().findFragmentByTag("frag_01");
			if (fragment == null) {
				LogUtils.printInfo(TAG, "no found frag_01");
			} else {
				fragmentTransaction.remove(fragment);
				LogUtils.printInfo(TAG, "remove fragment: frag_01");
				fragmentTransaction.commit();
				/*
				 * 当只加载了一个fragment时，remove后，它还是在前台显示着
				 * 当添加过多个fragment时，remove后，显示上一个fragment
				 */
			}
			
			break;
			
		case R.id.btn_option4:
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); 
			if (mFragment != null) {
				ft.remove(mFragment);
			}
			mFragment = new MyListFragment();
			ft.replace(R.id.ll_content, mFragment, "frag_list");
			ft.commit();
			break;
		default:
			break;
		}
	}
	
	private void addFragment1() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); 
		if (mFragment1 == null)
			mFragment1 = new Fragment01(); 
//		ft.replace(R.id.ll_content, mFragment); //将mFragment放置在ll_content中
		ft.add(R.id.ll_content, mFragment1, "frag_01");//已经add的不能再add
		
//		ft.add(mFragment, "frag_01"); //无界面的fragment
//		Toast.makeText(this, "add one 无界面的fragment", 0).show();
//		ft.addToBackStack("back01");//将被替换的加入到backstack，后退时，退出当前01，返回到前一个fragment
		//要利用后退栈：因某些操作产生多屏切换后，每次切换的动作加入到后退栈中，就ok
		
//		mFragment2 = new Fragment02(); 
//		ft.add(R.id.ll_content, mFragment2, "frag_02");//已经add的不能再add
//		ft.addToBackStack("back02");
		
		ft.commit();//执行后，才能显示fragment，才会有后退栈的处理
		
		LogUtils.printInfo(TAG, "-----addFragment1()----");
		mCurrent = mFragment1;
	}
	
	private void addFragment2() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (mFragment2 == null)
			mFragment2 = new Fragment02(); 
//		fragmentTransaction.add(R.id.ll_content, mFragment); 
//		ft.replace(R.id.ll_content, mFragment2, "frag_02"); 
		//要利用后退栈：因某些操作产生多屏切换后，每次切换的动作加入到后退栈中，就ok
//		ft.addToBackStack("frag_02");
//		ft.commit();
		
		LogUtils.printInfo(TAG, "-----addFragment2()----");
		switchContent(mCurrent, mFragment2);
	}
	
	private void addFragment3() {
//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); 
		if (mFragment3 == null) {
			mFragment3 = new Fragment03(); 
		}
//		fragmentTransaction.add(R.id.ll_content, mFragment); 
//		ft.replace(R.id.ll_content, mFragment3, "frag_03"); 
		//要利用后退栈：因某些操作产生多屏切换后，每次切换的动作加入到后退栈中，就ok
//		ft.addToBackStack("frag_03");
//		ft.commit();
		
		LogUtils.printInfo(TAG, "-----addFragment3()----");
		switchContent(mCurrent, mFragment3);
	}
	
	private Fragment mCurrent;
    public void switchContent(Fragment from, Fragment to) {
        if (from != to) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.ll_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mCurrent = to;
        }
    }
	
	public void showDialog() {
	    mStackLevel++;

	    // DialogFragment.show() will take care of adding the fragment
	    // in a transaction.  We also want to remove any currently showing
	    // dialog, so make our own transaction and take care of that here.
	    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	    Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    
	    if ((mStackLevel - 1) % 6 == 3) {
	    	ft.addToBackStack(null);//这null表示 String-tag  加入activity的后退栈。加入后 按 back键，还回到tag代表的fragment
	    }
	    
	    // Create and show the dialog.
	    DialogFragment newFragment = MyDialogFragment.newInstance(mStackLevel);
	    newFragment.show(ft, "dialog"); //show-》
	    
	}
	public void showDialog2() {
	    DialogFragment newFragment = MyAlertDialogFragment.newInstance("alert dialog");
	    newFragment.show(getSupportFragmentManager(), "dialog");
	}

	public void doPositiveClick() {
		// TODO Auto-generated method stub
		LogUtils.printInfo(TAG, "Positive click!");
	}

	public void doNegativeClick() {
		// TODO Auto-generated method stub
		LogUtils.printInfo(TAG, "Negative click!");
	}

}
