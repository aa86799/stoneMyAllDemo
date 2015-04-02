package com.stone.util;


public class Note {
	/*
	 * 1.线程 构造时要一个Handler 一个请求对象,
	 * 		网络操作(NetUtil--对应的JsonParser 返回具体的对象 )
	 * 		将对象封装在Message.obj handler发送 
	 * 2.Handler 构造时接收一个 DataCallback 数据回调 处理message
	 * 		消息时 success时 调用 I-DataCallback 的回调 方法：将对象数据显示等操作 
	 * 3.组合成一个方法 传入request对象,
	 * 		和I-DataCallback 对象 new Handler(datacallback); 
	 * 		new Runnable(handler,request); 
	 * 		调用线程池 添加runnable
	 */
}
