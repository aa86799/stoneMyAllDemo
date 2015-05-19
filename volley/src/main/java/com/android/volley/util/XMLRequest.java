package com.android.volley.util;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class XMLRequest extends Request<XmlPullParser> {  
	/** 响应监听  */
    private final Listener<XmlPullParser> mListener;  //Request默认只有error的监听器
  
    public XMLRequest(int method, String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {  
        super(method, url, errorListener);  
        mListener = listener;  
    }  
  
    public XMLRequest(String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {  
        this(Method.GET, url, listener, errorListener);  
    }  
  
    @Override  
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {  
        try {  
            String xmlString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));  
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  
            XmlPullParser xmlPullParser = factory.newPullParser();  
            xmlPullParser.setInput(new StringReader(xmlString));  
            return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));  
        } catch (UnsupportedEncodingException e) {  
            return Response.error(new ParseError(e));  
        } catch (XmlPullParserException e) {  
            return Response.error(new ParseError(e));  
        }  
    }  
  
    @Override  
    protected void deliverResponse(XmlPullParser response) {  
        mListener.onResponse(response);  
    }  
  
}  