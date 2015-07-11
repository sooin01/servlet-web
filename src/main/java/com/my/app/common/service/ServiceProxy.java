package com.my.app.common.service;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ServiceProxy {
	
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> clazz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new ServiceCallback());
		return (T) enhancer.create();
	}
	
	static class ServiceCallback implements MethodInterceptor {
		public Object intercept(Object paramObject, Method paramMethod,
				Object[] paramArrayOfObject, MethodProxy paramMethodProxy)
				throws Throwable {
			
			System.out.println(1);
			
			Object result = paramMethodProxy.invokeSuper(paramObject, paramArrayOfObject);
			
			System.out.println(2);
			
			return result;
		}
	}
	
}
