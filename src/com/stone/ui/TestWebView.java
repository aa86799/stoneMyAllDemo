package com.stone.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.stone.R;
/*
 * 	1、在布局文件中声明WebView
	2、在Activity中实例化WebView
	3、调用WebView的loadUrl()方法，加载指定的URL地址网页
	4、为了让WebView能够响应超链接功能，调用setWebViewClient()方法，设置WebView客户端
	5、为了让WebView支持回退功能，覆盖onKeyDown()方法
	6、一定要注意：在AndroidManifest.xml文件中添加访问互联网的权限，否则不能显示
	<uses-permission android:name=”android.permission.INTERNET”/>
	
	URLUtil.isNetworkUrl("url"); 判断是否为URL
	
	启用webview两种方法：1. 直接 setContentView(webview);
					2. setContentView(layout); layout 中 有<WebView../>
	
	问题:在做webview的应用的时候 :1.发现连接与输入框点击无点击效果  2.输入框无法输入内容.
 	解决办法: mywebview.requestFocusFromTouch();

	问题：有onmouseover，onmouseout的页面无效果时：使用ontouchstart，ontouchend替代
	
	问题：setInitialScale(scale) scale<100时， 当文本框获取焦点后自动放大，但我不想放大
	解决方法：通过反射，拿到WebView的一个私有属性mDefaultScale，并在WebView失去焦点的时候，修改它的值，已达到网页不放大的效果.
	Field mDefaultScale = WebView.class.getDeclaredField("mDefaultScale");
		mDefaultScale.setAccessible(true);
		mDefaultScale.setFloat(webView, scale);
	这个float值是你初始化的时候的scale值。跟你的CSS body值有一定的关系。假如body值 是1920*1080 而你现在是显示在1280*720的屏幕上，
	那么这个scale 的float值就是0.666667(宽度比)。就是你的现在的缩放页面与正常情况先显示的大小的比（缩放比），
	可以用页面第一次加载的时候通过 webview.getScale()值获取。

 */
public class TestWebView extends Activity {
	private WebView webView;
	private PersonalData personalData;
	private Handler handler = new Handler();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.webview);
		
		this.webView = (WebView) findViewById(R.id.webview);
		//WebView的 设置
		WebSettings settings = this.webView.getSettings();
		settings.setJavaScriptEnabled(true);//设置WebView属性，能够执行JavaScript脚本
		settings.setAllowFileAccess(true); //启用或禁止webview访问文件数据, 启用能访问本地文件
		settings.setBlockNetworkImage(true);//是否显示网络图像； The default is false.
		//上下滑动时出现缩放按钮，双击一次变大/变小, 
		settings.setBuiltInZoomControls(true);//是否支持缩放 ；The default is false	
		settings.supportZoom();//是否支持变焦 返回boolean
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//缓冲的模式
		settings.setDefaultFontSize(30);// 默认字体大小
		settings.setDefaultTextEncodingName("utf-8");//默认编码
//		settings.setTextSize(t);//页面文字大小 已过时，用setTextZoom代替
		settings.setTextZoom(110); //设置文本页面的缩放比例。默认值是100。
		settings.setDatabaseEnabled(true);//设置是否可以使用数据库的相关api
		settings.setDatabasePath("");//数据库的路径，已过时，无效
		//默认提供三种字体，只对英文有效， 中文需要使用字符ttf文件
		settings.setFixedFontFamily("sans");//固定使用字体;The default is "monospace", serif
		settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL); //布局方式 
		settings.setLightTouchEnabled(true); //用鼠标 激活被选项
		settings.setUseWideViewPort(true);//网页是否能显示在横向屏幕之外,true时 可以横向查看 
		settings.setSavePassword(true);//存储密码
		
		 //加载URL内容
//		this.webView.loadUrl("http://www.baidu.com");
//		this.webView.loadUrl("file:///android_asset/android.txt");
//		this.webView.loadUrl("file:///android_asset/dialog.html");

		personalData = new PersonalData();
		//把本类的一个实例添加到js的全局对象window中，这样就可以使用window.PersonalData来调用它的方法
		this.webView.addJavascriptInterface(this, "PersonalData");
		//构造 自定义对象
		this.webView.addJavascriptInterface(new Object() {
			@JavascriptInterface
			public void click() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						webView.loadUrl("http://www.163.com");
						System.out.println("addJavascriptInterface:custom object");
					}
				});
			}
		}, "obj");//js端： window.MyObject.click()
		//js 调用java对象, sdk2.3有bug，会将Java的String对象当数组访问，导致dalvik崩溃
		//模拟器测了2.2正常，2.3崩溃,  3.0和4.1 不崩溃，页面获取不到数据，空白
		this.webView.loadUrl("file:///android_asset/PersonalData.html");
//		this.webView.loadData("http://www.baidu.com", "text/html", "utf8");
		
		 //设置web视图客户端
		this.webView.setWebViewClient(new MyWebViewClient());
		this.webView.setWebChromeClient(new MyWebChromeClient());
		this.webView.setInitialScale(0);//网页初始百分比
		
		
		
//		System.out.println(Looper.myLooper());
//		new Thread() {
//			public void run() {
//				Looper.prepare();
//				System.out.println(Looper.myLooper());
//			};
//		}.start();
//		new Thread() {
//			public void run() {
//				Looper.prepare();
//				System.out.println(Looper.myLooper());
//			};
//		}.start();
	}
	//设置回退
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && this.webView.canGoBack()) {
			this.webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 主要帮助WebView处理各种通知、请求事件的
	 */
	class MyWebViewClient extends WebViewClient {
		@Override //控制加载网页
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		@Override // 更新历史记录
		public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
			super.doUpdateVisitedHistory(view, url, isReload);
		}
		@Override //应用程序重新请求网页数据
		public void onFormResubmission(WebView view, Message dontResend, Message resend) {
			super.onFormResubmission(view, dontResend, resend);
		}
		@Override //加载指定url提供的资源
		public void onLoadResource(WebView view, String url) {
			super.onLoadResource(view, url);
		}
		@Override //网页加载完毕
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
		@Override //网页开始加载
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}
		@Override //收到不可恢复的错误信息
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
		@Override //WebView 缩放比例发生改变 
		public void onScaleChanged(WebView view, float oldScale, float newScale) {
			super.onScaleChanged(view, oldScale, newScale);
		}
		
		@Override //拦截url请求
		public WebResourceResponse shouldInterceptRequest(WebView view,
				String url) {
			return super.shouldInterceptRequest(view, url);
		}
		@Override
		@Deprecated 
		public void onTooManyRedirects(WebView view, Message cancelMsg,
				Message continueMsg) {
			// TODO Auto-generated method stub
			super.onTooManyRedirects(view, cancelMsg, continueMsg);
		}
		@Override //ssl error
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			// TODO Auto-generated method stub
			super.onReceivedSslError(view, handler, error);
		}
		@Override //http auth  http授权请求
		public void onReceivedHttpAuthRequest(WebView view,
				HttpAuthHandler handler, String host, String realm) {
			// TODO Auto-generated method stub
			super.onReceivedHttpAuthRequest(view, handler, host, realm);
		}
		@Override //默认返回false，表示webview总是处理按键事件
		public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
			return super.shouldOverrideKeyEvent(view, event);
		}
		@Override //当上面的方法返回true，本方法来处理key event
		public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
			// TODO Auto-generated method stub
			super.onUnhandledKeyEvent(view, event);
		}
		@Override //接收到一个自动登录请求
		public void onReceivedLoginRequest(WebView view, String realm,
				String account, String args) {
			// TODO Auto-generated method stub
			super.onReceivedLoginRequest(view, realm, account, args);
		}
	}
	/**
	 * 主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
	 */
	class MyWebChromeClient extends WebChromeClient {
		@Override //关闭webview
		public void onCloseWindow(WebView window) {
			super.onCloseWindow(window);
		}
		@Override //创建webview
		public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
			return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
		}
		@Override //处理JS中的alert对话框
		public boolean onJsAlert(WebView view, String url, String message, final JsResult result) { 
			new AlertDialog.Builder(TestWebView.this)//
				.setTitle("Alert对话框")//
				.setMessage(message)//
				.setCancelable(false)//
				.setPositiveButton(android.R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				})//
				.create().show();
			return true;
		}
		@Override //处理JS中的confirm对话框
		public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
			new AlertDialog.Builder(TestWebView.this)//
				.setTitle("Confirm对话框")//
				.setMessage(message)//
				.setCancelable(true)//
				.setPositiveButton(android.R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				})//
				.setNegativeButton(android.R.string.cancel, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}
				})//
				.create().show();
			return true;
		}
		@Override //处理JS中的prompt对话框; var str = window.prompt("提示","输入框中的默认值"); alert(str); 
		public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
			View layout = LayoutInflater.from(TestWebView.this).inflate(R.layout.jsprompt, null);
			TextView tv_prompt = (TextView) layout.findViewById(R.id.tv_prompt);
			tv_prompt.setText(message);//prompt提示信息
			final EditText et_prompt = (EditText) layout.findViewById(R.id.et_prompt);
			et_prompt.setText(defaultValue);//prompt et默认值
			et_prompt.setFocusable(false);
			et_prompt.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (v.isFocusable()) {
						return false;
					} else {
						System.out.println("touch edittext");
						et_prompt.setFocusableInTouchMode(true);
						return true;
					}
					
				}
			});
			et_prompt.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (hasFocus) {
						et_prompt.setText("");
					}
				}
			});
			new AlertDialog.Builder(TestWebView.this)//
				.setTitle("Prompt对话框")//
				.setCancelable(true)// 默认为true ，即back键可以取消 对话框 
				.setPositiveButton(android.R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm(et_prompt.getText().toString());
					}
				})//
				.setNegativeButton(android.R.string.cancel, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}
					
				})//
				.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						result.cancel();
					}
				})//
				.setView(layout).show();
			return true;
		}
		@Override //加载进度条改变
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			TestWebView.this.getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress);
		}
		@Override //网页图标更改
		public void onReceivedIcon(WebView view, Bitmap icon) {
			super.onReceivedIcon(view, icon);
		}
		@Override //网页title更改
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			TestWebView.this.setTitle(title); 
		}
		@Override //Webview 显示焦点
		public void onRequestFocus(WebView view) {
			super.onRequestFocus(view);
		}
		
		
		@Override
		public void onHideCustomView() {
			// TODO Auto-generated method stub
			super.onHideCustomView();
		}
		@Override
		public boolean onJsBeforeUnload(WebView view, String url,
				String message, JsResult result) {
			// TODO Auto-generated method stub
			return super.onJsBeforeUnload(view, url, message, result);
		}
		@Override
		public void onGeolocationPermissionsShowPrompt(String origin,
				Callback callback) {
			// TODO Auto-generated method stub
			super.onGeolocationPermissionsShowPrompt(origin, callback);
		}
		@Override
		public boolean onJsTimeout() {
			// TODO Auto-generated method stub
			return super.onJsTimeout();
		}
		@Override
		@Deprecated
		public void onConsoleMessage(String message, int lineNumber,
				String sourceID) {
			// TODO Auto-generated method stub
			super.onConsoleMessage(message, lineNumber, sourceID);
		}
		@Override
		public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
			// TODO Auto-generated method stub
			return super.onConsoleMessage(consoleMessage);
		}
		@Override
		public Bitmap getDefaultVideoPoster() {
			// TODO Auto-generated method stub
			return super.getDefaultVideoPoster();
		}
		@Override
		public View getVideoLoadingProgressView() {
			// TODO Auto-generated method stub
			return super.getVideoLoadingProgressView();
		}
		@Override
		public void getVisitedHistory(ValueCallback<String[]> callback) {
			// TODO Auto-generated method stub
			super.getVisitedHistory(callback);
		}
		@Override
		@Deprecated
		public void onExceededDatabaseQuota(String url,
				String databaseIdentifier, long quota,
				long estimatedDatabaseSize, long totalQuota,
				QuotaUpdater quotaUpdater) {
			// TODO Auto-generated method stub
			super.onExceededDatabaseQuota(url, databaseIdentifier, quota,
					estimatedDatabaseSize, totalQuota, quotaUpdater);
		}
		@Override
		public void onGeolocationPermissionsHidePrompt() {
			// TODO Auto-generated method stub
			super.onGeolocationPermissionsHidePrompt();
		}
		@Override
		@Deprecated
		public void onReachedMaxAppCacheSize(long requiredStorage, long quota,
				QuotaUpdater quotaUpdater) {
			// TODO Auto-generated method stub
			super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
		}
		@Override
		public void onReceivedTouchIconUrl(WebView view, String url,
				boolean precomposed) {
			// TODO Auto-generated method stub
			super.onReceivedTouchIconUrl(view, url, precomposed);
		}
		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			// TODO Auto-generated method stub
			super.onShowCustomView(view, callback);
		}
		@Override
		@Deprecated
		public void onShowCustomView(View view, int requestedOrientation,
				CustomViewCallback callback) {
			// TODO Auto-generated method stub
			super.onShowCustomView(view, requestedOrientation, callback);
		}
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("前进");
		menu.add("后退");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("前进") && webView.canGoForward()) {
			webView.goForward();
		}
		if (item.getTitle().equals("后退") && webView.canGoBack()) {
			webView.goBack();
		}
		return true;
	}
	@JavascriptInterface
	public PersonalData getPersonalData() {
		return this.personalData;
	}
	//js脚本中调用显示的资料
	class PersonalData
	{
		String  mID;
		String  mName;
		String  mAge;
		String  mBlog;	
		public PersonalData()
		{
			this.mID="123456789";
			this.mName="Android";
			this.mAge="100";
			this.mBlog="http://www.baidu.com";
		}
		@JavascriptInterface
		public String getID()
		{
			return mID;
		}
		@JavascriptInterface
		public String getName()
		{
			return mName;
		}
		@JavascriptInterface
		public String getAge()
		{
			return mAge;
		}
		@JavascriptInterface
		public String getBlog()
		{
			return mBlog;
		}
	}
	@JavascriptInterface
	public void cc() {
		System.out.println("asdminiadsfasdjf");
	}
}
