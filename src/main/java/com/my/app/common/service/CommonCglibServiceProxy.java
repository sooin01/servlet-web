package com.my.app.common.service;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.my.app.sample.service.impl.SampleServiceImpl;

public class CommonCglibServiceProxy {
	
	public Object newInstance(Class<?> clazz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new ServiceCallback());
		return enhancer.create();
	}
	
	class ServiceCallback implements MethodInterceptor {
		public Object intercept(Object paramObject, Method paramMethod,
				Object[] paramArrayOfObject, MethodProxy paramMethodProxy)
				throws Throwable {
			
			System.out.println(1);
			
			Object result = paramMethodProxy.invokeSuper(paramObject, paramArrayOfObject);
			
			System.out.println(2);
			
			return result;
		}
		
		
		
	}

	public static void main(String[] args) {
		SampleServiceImpl service = (SampleServiceImpl) new CommonCglibServiceProxy().newInstance(SampleServiceImpl.class);
		service.getSampleList("test");
	}
	
}
