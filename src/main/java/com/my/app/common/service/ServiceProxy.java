package com.my.app.common.service;

import net.sf.cglib.proxy.Enhancer;

public class ServiceProxy {
	
	private Class<?> clazz;
	
	public ServiceProxy(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T newInstance() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new ServiceCallback());
		return (T) enhancer.create();
	}
	
}
