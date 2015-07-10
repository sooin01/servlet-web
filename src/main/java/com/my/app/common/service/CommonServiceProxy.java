package com.my.app.common.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CommonServiceProxy implements InvocationHandler {
	
	private Object obj;
	
	public CommonServiceProxy(Object obj) {
		this.obj = obj;
	}

	public static Object newInstance(Class<?> clazz) {
		try {
			return Proxy.newProxyInstance(clazz.getClassLoader(),
					clazz.getInterfaces(),
					new CommonServiceProxy(clazz.newInstance()));
		} catch (Exception e) {
		}
	
		return null;
	}
	
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
