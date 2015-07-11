package com.my.app.common.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CommonServiceProxy implements InvocationHandler {
	
	private Object obj;
	
	public CommonServiceProxy(Object obj) {
		this.obj = obj;
	}

	/**
	 * Proxy 인스턴스 생성
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> clazz) {
		T t = null;
		
		try {
			Object obj = Proxy.newProxyInstance(clazz.getClassLoader(),
					clazz.getInterfaces(),
					new CommonServiceProxy(clazz.newInstance()));
			t = (T) clazz.getInterfaces()[0].cast(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return t;
	}
	
	/**
	 * 실제 인스턴스의 메소드가 호출될 때 수행
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		
		try {
			result = method.invoke(obj, args);
		} finally {
		}
		
		return result;
	}

}
