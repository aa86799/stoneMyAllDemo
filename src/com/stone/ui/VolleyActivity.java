package com.stone.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.stone.R;
import com.stone.model.User;
import com.stone.model.Weather;
import com.stone.model.WeatherInfo;
import com.stone.model.JsonAllUserRequest;
import com.stone.model.JsonArrayRequest;
import com.android.volley.util.GsonObjectRequest;
import com.android.volley.util.GsonUtil;
import com.android.volley.util.XMLRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/5/5 18 23
 *
 * volley 需要min sdk 为 api8
 */
public class VolleyActivity extends Activity {

    private RequestQueue mRequestQueue;
    private ImageView iv_img1, iv_img2;
    private NetworkImageView iv_netImg;
    private TextView tvJson;

    static final String WEATHER_JSON_ADDRESS = "http://www.weather.com.cn/data/sk/101010100.html";
    static final String WEATHER_XML_ADDRESS = "http://flash.weather.com.cn/wmaps/xml/china.xml";
    static final String ALLUSER_JSON_ADDRESS = "http://123.57.138.77:8080/ssh/getAllUser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gson_volley);

        iv_img1 = (ImageView) findViewById(R.id.iv_img1);
        iv_img2 = (ImageView) findViewById(R.id.iv_img2);
        iv_netImg = (NetworkImageView) findViewById(R.id.iv_netImg);
        tvJson = (TextView) findViewById(R.id.tv_json);

        mRequestQueue =  Volley.newRequestQueue(this); //初始化volley 请求队列

        requestByImageRequest(iv_img1, "http://7xi8ex.com1.z0.glb.clouddn.com/fengjing/1.jpg");

        requestByImageLoader(iv_img2, "http://7xi8ex.com1.z0.glb.clouddn.com/fengjing/2.jpg");

        requestByNetworkImg(iv_netImg, "https://lh5.googleusercontent.com/-WVpRptWH8Yw/URqugh-QmDI/AAAAAAAAAbs/E-MgBgtlUWU/s1024/Chihuly.jpg");

    }
	/*
	 * Volley提供了JsonObjectRequest、JsonArrayRequest、StringRequest等Request形式。
		JsonObjectRequest：返回JSON对象。
		JsonArrayRequest：返回JsonArray。
		StringRequest：返回String，这样可以自己处理数据，更加灵活。
		另外可以继承Request<T>自定义Request。

		如果是post|put 请求。需要重写Request的getParams方法，put相应的参数进去。

		如果客户端的请求就是是一个json请求，
		    Map<String, String> map = new HashMap<String, String>();
            map.put("name1", "value1");
            map.put("name2", "value2");
            JSONObject jsonObject = new JSONObject(params);
             new JsonObjectRequest(Method.POST,url, jsonObject..)
	 */

    @Override
    protected void onStop() {
        super.onStop();
		/*
		 * Activity里面启动了网络请求，而在这个网络请求还没返回结果的时候，Activity被结束了，此时如果继续使用其中的Context等，
		 * 除了无辜的浪费CPU，电池，网络等资源，有可能还会导致程序crash，所以，我们需要处理这种一场情况。
		 * 使用Volley的话，我们可以在Activity停止的时候，同时取消所有或部分未完成的网络请求。
		 * Volley里所有的请求结果会返回给主进程，如果在主进程里取消了某些请求，则这些请求将不会被返回给主线程。
		 * Volley支持多种request取消方式。
		 */

        mRequestQueue.stop();

        mRequestQueue.cancelAll("requestByImageRequest");//取消该tag标识的所有请求

        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {

            @Override
            public boolean apply(Request<?> request) {
                if (request.getUrl().indexOf("xyz") != -1) {
                    return true;
                }
                return false;
            }
        });

    }

    /* ImageRequest
     *
     * Volley提供了多种Request方法，ImageRequest能够处理单张图片，返回bitmap。
     * 下面是ImageRequest的使用例子，和JsonRequest的一样。
     */
    private void requestByImageRequest(final ImageView imageView, String url) {
        ImageRequest imgRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {//从url请求一张图片
                    @Override
                    public void onResponse(Bitmap bitmap) {//下载成功后
                        imageView.setImageBitmap(bitmap);
                    }
                },
                imageView.getWidth(), imageView.getHeight(), Bitmap.Config.RGB_565,
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) { //下载出错

                    }
                });
        imgRequest.setTag("requestByImageRequest");
        mRequestQueue.add(imgRequest);
        mRequestQueue.start();
//        imgRequest.cancel();
    }
    /* ImageLoader ImageCache
     *
     *	ImageLoader这个类需要一个Request的实例以及一个ImageCache的实例。图片通过一个URL和一个ImageListener实例的get()方法就可以被加载。
     *	ImageLoader会检查ImageCache,而且如果缓存里没有图片就会从网络上获取。
           Volley的ImageCache接口允许你使用你喜欢的Lru缓存实现。不幸的是Volley没有提供默认的实现。
        在I/O的介绍中展示了BitmapLruCache的一点代码片段，但是Volley这个库本身并不包含任何相关的实现。
        ImageCache接口有两个方法，getBitmap(String url)和putBitmap(String url, Bitmap bitmap).
        这两个方法足够简单直白，他们可以添加任何的缓存实现。
     */
    private void requestByImageLoader(ImageView imageView, String url) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//app最大内存 字节数
        int mCacheSize = maxMemory / 8;  //以1/8 用作缓存
        final LruCache<String, Bitmap> mLruImageCache = new LruCache<String, Bitmap>(mCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {//计算一张图片的内存size
//                if (android.os.Build.VERSION.SDK_INT >= 12) {
//                    return value.getByteCount(); //需要api >=12 ， 总字节数
//                }
                return value.getRowBytes() * value.getHeight(); //每行字节乘以高(即行)   api1
            }
        };
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                mLruImageCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return mLruImageCache.get(key);
            }
        };
        ImageLoader mImageLoader = new ImageLoader(mRequestQueue, imageCache);
        // ImageLoader.getImageListener的第二个参数是默认的图片resource id,请求的图片为空时设置
        // 第三个参数是请求失败时候的资源id，可以指定为0
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(
                imageView, android.R.drawable.ic_menu_rotate,
                android.R.drawable.ic_delete);

        ImageLoader.ImageContainer imageContainer = mImageLoader.get(url, listener);//get方法已经开始执行了加载url-bitmap
        Bitmap bitmap = imageContainer.getBitmap();//获取bitmap
		String requestUrl = imageContainer.getRequestUrl();//获取请求url

    }

    //使用NetworkImageView ImageLoader ImageCache
    private void requestByNetworkImg(NetworkImageView imageView, String url) {
        final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(8*1024*1024) {
            @Override
            protected int sizeOf(String key, Bitmap value) {//计算一张图片的内存size
                return value.getRowBytes() * value.getHeight(); //每行字节乘以高(即行)   api1
            }
        };
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                mImageCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return mImageCache.get(key);
            }
        };
        ImageLoader mImageLoader = new ImageLoader(mRequestQueue, imageCache);
		/*
		 * seturl时，里面的实现是：有了ImageLoader，就能执行加载url-bitmap的操作
		 */
        imageView.setDefaultImageResId(0); //默认图
        imageView.setErrorImageResId(0); //错误图
        imageView.setImageUrl(url, mImageLoader);
    }


    public void requestJsonArray(View view) {
        com.android.volley.toolbox.JsonArrayRequest request = new com.android.volley.toolbox.JsonArrayRequest("url",
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mRequestQueue.add(request);
        mRequestQueue.start();
    }
    public void requestJsonObject(View view) {
        com.android.volley.toolbox.JsonObjectRequest request = new com.android.volley.toolbox.JsonObjectRequest(
                Request.Method.GET, ALLUSER_JSON_ADDRESS, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        /*
                        response 含 jsonArray
                         */
                        JsonArrayRequest jsonRequest = GsonUtil.getData(response.toString(), JsonArrayRequest.class);
                        if (jsonRequest.getStatus().equals("success")) {
                            List<User> list = GsonUtil.getList(jsonRequest.getData().toString(), User[].class);
                            //直接如下使用是可以的
//                            List<User> list = new Gson().fromJson(jsonRequest.getData().toString(),
// new TypeToken<List<User>>() {}.getType());
                            StringBuilder builder = new StringBuilder();
                            for (User user : list) {
                                builder.append("requestJsonObject:\nid is " + user.getId());
                                builder.append("; name is " + user.getName());
                                builder.append("; age is " + user.getAge());
                            }
                            tvJson.setText(builder.toString());
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("requestJsonObject - error");
                    }
                }){
            @Override  //如果是 post  有请求参数时
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name1", "value1");
                map.put("name2", "value2");
                return map;
            }
        };

        mRequestQueue.add(request);
        mRequestQueue.start();

    }

    /*
    根据json结构，定义model
     */
    public void requestGson(View view) {
        final StringBuilder builder = new StringBuilder();
        /*
        直接用gson转成object
         */
        GsonObjectRequest<Weather> requestWeather = new GsonObjectRequest<Weather>(
                WEATHER_JSON_ADDRESS, Weather.class, null,
                new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather response) {
                        WeatherInfo weatherinfo = response.getWeatherinfo();
                        builder.append("城市:" + weatherinfo.getCity());
                        builder.append("; 时间:" + weatherinfo.getTime());
                        builder.append("; 温度:" + weatherinfo.getTemp());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("requestGson - error");
                    }
                }
        );
        mRequestQueue.add(requestWeather);

        GsonObjectRequest<JsonAllUserRequest> requestAllUser = new GsonObjectRequest<JsonAllUserRequest>(
                ALLUSER_JSON_ADDRESS, JsonAllUserRequest.class, null,
                new Response.Listener<JsonAllUserRequest>() {
                    @Override
                    public void onResponse(JsonAllUserRequest response) {
                        List<User> list = response.getData();
                        for (User user : list) {
                            builder.append("requestGson:\nid is " + user.getId());
                            builder.append("; name is " + user.getName());
                            builder.append("; age is " + user.getAge());
                        }
                        tvJson.setText(builder.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("requestGson - error");
                    }
                }
        );
        mRequestQueue.add(requestAllUser);

        mRequestQueue.start();
    }

    public void requestXml(View view) {
        XMLRequest request = new XMLRequest(WEATHER_XML_ADDRESS,
                new Response.Listener<XmlPullParser>() {

                    @Override
                    public void onResponse(XmlPullParser response) {
                        try {
                            int eventType = response.getEventType();
                            StringBuilder builder = new StringBuilder();
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        String nodeName = response.getName();
                                        if ("city".equals(nodeName)) {
                                            String pName = response.getAttributeValue(0);
                                            builder.append(pName + "\n");
                                        }
                                        break;
                                }
                                eventType = response.next();
                            }
                            tvJson.setText(builder.toString());
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("test4XMLRequest - error");
                    }
                });
        mRequestQueue.add(request);
        mRequestQueue.start();
    }

}
