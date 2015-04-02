package com.stone.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 线程池 单例
 */
public class ThreadPoolManager {
	private ExecutorService service;

	private ThreadPoolManager() {
		int nThreads = Runtime.getRuntime().availableProcessors();// 核心数
		service = Executors.newFixedThreadPool(nThreads * 2);
	}

	private static final ThreadPoolManager manager = new ThreadPoolManager();

	public static ThreadPoolManager getInstance() {
		return manager;
	}
	
	public void addTask(Runnable runnable) {
		service.execute(runnable);
	}
}