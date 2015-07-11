package com.my.app.sample.service.impl;

import com.my.app.common.service.CommonServiceProxy;
import com.my.app.sample.service.SampleService;

public class SampleServiceImpl implements Cloneable, SampleService {
	
	private static SampleService sampleService = CommonServiceProxy.newInstance(SampleServiceImpl.class);
	
	public static SampleService getInstance() {
		return sampleService;
	}

	public void getSampleList(String id) {
		CommonServiceProxy.newInstance(SampleServiceImpl.class);
		System.out.println("get sample list. id : " + id);
	}
	
}
