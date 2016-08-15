package com.my.app.common.service;

import net.sf.cglib.proxy.Enhancer;

public class ServiceProxy {
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<?> clazz) {
		return (T) Enhancer.create(clazz, new ServiceCallback());
	}
	
}
