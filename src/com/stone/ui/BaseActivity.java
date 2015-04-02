package com.stone.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.stone.R;
import com.stone.reqvo.RequestVo;
import com.stone.util.Constants;
import com.stone.util.DialogUtil;
import com.stone.util.NetDataUtil;
import com.stone.util.ThreadPoolManager;

/**
 * Activity 基类：
 * app 使用网络加载json数据
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
	private ThreadPoolManager threadPoolManager;
	protected ProgressDialog progressDialog;
	
	public BaseActivity() {
		this.threadPoolManager = ThreadPoolManager.getInstance();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
		/*
		 * 模板模式
		 */
		globeView();
		initView();
		addListener();
		processLogic();
	}
	/**
	 * 全局view
	 */
	private void globeView() {
		this.progressDialog = DialogUtil.getProgressDialog(this);
	}
	/**
	 * 初始化组件
	 */
	protected abstract void initView();
	/**
	 * 添加监听器
	 */
	protected abstract void addListener();
	/**
	 *  业务逻辑处理
	 */
	protected abstract void processLogic();
	
	@Override
	public void onClick(View v) {
		//view click
	}
	/**
	 * Handler 基类，
	 * 将task中得到的message对象进行处理:不为null 调用 回调DataCallback
	 */
	@SuppressWarnings("unchecked")
	class BaseHandler extends Handler {
		private Context context;
		private IDataCallback callBack;

		public BaseHandler(Context context, IDataCallback callBack) {
			this.context = context;
			this.callBack = callBack;
		}
		
		public void handleMessage(Message msg){ 
			DialogUtil.closeProgressDialog(BaseActivity.this.progressDialog);
			switch (msg.what) {
			case Constants.SUCCESS:
				if (msg.obj == null) {//提示  是网络有问题 没数据
					DialogUtil.showInfoDialog(context, getString(R.string.net_erroe));
				} else {
					callBack.processData(msg.obj, true);//通过构造器传入的回调对象调用回调方法
				}
				break;
				
			case Constants.NET_FAILED://网络真的有问题
				DialogUtil.showInfoDialog(context, getString(R.string.net_erroe), //
						getString(R.string.progress_title), "检查", //
						new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent mIntent = new Intent(Intent.ACTION_VIEW);
								ComponentName comp = new ComponentName("com.android.settings",//
									  "com.android.settings.WirelessSettings");      
								mIntent.setComponent(comp);                    
								startActivity(mIntent);
							}
						},"不检查");
			
				break;

			default:
				break;
			}
		}
	}
	
	/**
	 * 异步线程基类，子类里所有和后台的数据交互都通过此类
	 */
	class BaseTask implements Runnable {
		private Context context;
		private RequestVo reqVo;
		private Handler handler;

		public BaseTask(Context context, RequestVo reqVo, Handler handler) {
			this.context = context;
			this.reqVo = reqVo;
			this.handler = handler;
		}
		
		public void run() {
			Object obj = null;
			Message msg = handler.obtainMessage();
			if (NetDataUtil.hasNetwork(context) && reqVo.requestDataMap == null) {
				obj = NetDataUtil.get(reqVo);
				msg.what = Constants.SUCCESS;
			} else if (NetDataUtil.hasNetwork(context) && reqVo.requestDataMap != null) {
				obj = NetDataUtil.post(reqVo);
				msg.what = Constants.SUCCESS;
			} else {
				msg.what = Constants.NET_FAILED;
			}
			msg.obj = obj;
			handler.sendMessage(msg);
			
		}
	}
	
	/**
	 * activity 子类，调用
	 * 从服务器上获取数据，并回调处理
	 * @param reqVo	
	 * @param callBack
	 */
	protected void getDataFromServer(RequestVo reqVo, IDataCallback callBack) {
		BaseHandler handler = new BaseHandler(this, callBack);
		BaseTask taskThread = new BaseTask(this, reqVo, handler);
		this.threadPoolManager.addTask(taskThread);
	}
	
	/**
	 * 定义数据回调类
	 * @param <T>
	 */
	public interface IDataCallback<T> {
		public abstract void processData(T paramObject, boolean paramBoolean);
	}
}
