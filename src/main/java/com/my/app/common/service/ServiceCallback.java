package com.my.app.common.service;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ServiceCallback implements MethodInterceptor {
	
	@Override
	public Object intercept(Object paramObject, Method paramMethod,
			Object[] paramArrayOfObject, MethodProxy paramMethodProxy)
			throws Throwable {
		
		Object result = paramMethodProxy.invokeSuper(paramObject, paramArrayOfObject);
		
		return result;
	}
	
}