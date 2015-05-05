package com.stone.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.stone.R;
import com.stone.request.RequestVo;

/**
 * 网络数据请求操作
 *
 */
public class NetDataUtil {
	private static final String TAG = "NetUtil";
	/**
	 * post请求
	 */
	public static Object post(RequestVo vo){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(Constants.APP_HOST//
				.concat(vo.context.getString(vo.requestUrl)));
		//服务端struts2配置的action：   action name="user_*" class=".." method={1}
		//requestUrl = "http://.../user_add.action";
		Object obj = null;
		try {
			if(vo.requestDataMap!=null){
				HashMap<String,String> map = vo.requestDataMap;
				ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
				BasicNameValuePair pair = null;
				for(Map.Entry<String,String> entry:map.entrySet()){
					pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
					pairList.add(pair);
				}
				HttpEntity entity = new UrlEncodedFormEntity(pairList,"UTF-8");
				post.setEntity(entity);
				
			}
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity(),"UTF-8");
				try {
					obj = vo.jsonParser.parseJSON(result);
				} catch (JSONException e) {
					LogUtils.printError(TAG, e.getLocalizedMessage(),e);
				}
				return obj;
			}
		} catch (Exception e) {
			LogUtils.printError(TAG, e.getLocalizedMessage(),e);
		}
		return null;
	}
	
	/**
	 * get 请求
	 * @param vo
	 * @return
	 */
	public static Object get(RequestVo vo){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(Constants.APP_HOST//
				.concat(vo.context.getString(vo.requestUrl)));
		Object obj = null;
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity(), "UTF-8");
				Log.e(TAG, result);
				try {
					obj = vo.jsonParser.parseJSON(result);
				} catch (JSONException e) {
					LogUtils.printError(TAG, e.getLocalizedMessage(), e);
				}
				return obj;
			}
		} catch (Exception e) {
			LogUtils.printError(TAG, e.getLocalizedMessage(), e);
		} 
		return null;
	}
	
	/**
	 * 获得网络连接是否可用
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context){
		ConnectivityManager con = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if(workinfo == null || !workinfo.isAvailable())
		{
			DialogUtil.showToast(context, context.getString(R.string.net_not_connect));
			return false;
		}
		return true;
	}
	/**
	 * java.net 方式 ，例子
	 */
	public static void getSth(String url, HashMap<String, String> params) {
		try {
			//无论是post还是get，http请求实际上直到HttpURLConnection的getInputStream()这个函数里面才正式发送出去。
			//get 请求
			for (Map.Entry<String, String> entry : params.entrySet()) {
				url += entry.getKey() + "=" + entry.getValue() +"&";
			}
			if (params.size() > 1) {
				url = url.substring(0, url.length() - 1);
			}
			URL connUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) connUrl.openConnection();
			connection.setFollowRedirects(false); // 设置所有的http连接是否自动处理重定向   
			connection.setInstanceFollowRedirects(true); //设置本次连接是否自动处理重定向   false：需要自行从响应中分析新的url进行处理   
			connection.setConnectTimeout(5000); // 超时间
			connection.setUseCaches(false); //post请求不能使用缓存 
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Charsert", "UTF-8");
//			connection.getResponseCode(); //响应码
			
			//post
			connection.setDoInput(true); //设置为可输入,默认为true
			connection.setRequestMethod("POST");
			BufferedReader bis = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine = null;
			String resultData = "";
			while ((inputLine = bis.readLine()) != null) {
				resultData += inputLine + "\r\n";
			} 
			bis.close(); 
			connection.disconnect();   //从服务端读取数据   响应，  如果读写共用，，那么写需要在读之前
	
			connection.setDoOutput(true);//设置为可输出
			OutputStream os = connection.getOutputStream();
			String content = "";
			for (Map.Entry<String, String> entry : params.entrySet()) {
				content += URLEncoder.encode(entry.getKey() + "=" + entry.getValue(),"UTF-8");
				// 服务端 有 相应的 取数据 规则
			}
			os.write(content.getBytes()); // 输出表单数据到  服务端    请求
			os.close();
			connection.disconnect(); 
			
			
		} catch (Exception e) {
			LogUtils.printError(TAG, e.getMessage(), e);
		}
	}
	/**
	 * org.apache    HttpClient  例子
	 */
	public static void getSthForHttpClient(String url, HashMap<String, String> params) {
		try {
			// get请求 也是 在 后面url 后拼接，上个方法有，这不写了
			HttpGet httpGet = new HttpGet(url); 
			HttpClient httpClient = new DefaultHttpClient();
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
			HttpConnectionParams.setSoTimeout(httpParams, 5000);
			HttpConnectionParams.setSocketBufferSize(httpParams, 8192); 
			
			HttpResponse httpResponse = httpClient.execute(httpGet); // 执行get
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				EntityUtils.toString(httpResponse.getEntity()/*响应的数据*/);
			}
			//如果 要得到 流数据
			InputStream is = httpResponse.getEntity().getContent();  
			//........
			
			//post
			HttpPost httpPost = new HttpPost(url);
			//BasicNameValuePair  构造 请求参数
			ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
			BasicNameValuePair pair = null;
			for(Map.Entry<String,String> entry: params.entrySet()){
				pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				pairList.add(pair);
			}
			//设置 字符集
			HttpEntity postEntity = new UrlEncodedFormEntity(pairList,"UTF-8");
			httpPost.setEntity(postEntity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse postResponse = client.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				EntityUtils.toString(httpResponse.getEntity()/*响应的数据*/);
			} 
			
//			client.getConnectionManager().shutdown();
		} catch (Exception e) {
			LogUtils.printError(TAG, e.getMessage(), e);
		}
	}
	
	private static ClientConnectionManager sClientConnectionManager = null;  
	   
	public static final ConnPerRoute sConnPerRoute = new ConnPerRoute() {  
		@Override
		public int getMaxForRoute(HttpRoute route) {  
	       return 80;   
	    }  
	};  
	
	public static synchronized ClientConnectionManager getClientConnectionManager() {  
		if (sClientConnectionManager == null) {  
	     // Create a registry for our three schemes; http and https will  
	      // use  
	       // built-in factories  
			SchemeRegistry registry = new SchemeRegistry();  
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
	 
       // And create a ccm with our registry  
	      HttpParams params = new BasicHttpParams();  
	      params.setIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 320);  
	      params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, sConnPerRoute);  
	      sClientConnectionManager = new ThreadSafeClientConnManager(params,  
            registry);  
	    }  
	   // Null is a valid return result if we get an exception  
	    return sClientConnectionManager;  
	} 

}
