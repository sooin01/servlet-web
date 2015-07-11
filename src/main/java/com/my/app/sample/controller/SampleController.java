package com.my.app.sample.controller;

import com.my.app.common.service.ServiceProxy;
import com.my.app.common.util.MultipartRequest;
import com.my.app.sample.service.impl.SampleService;

public class SampleController {
	
	private SampleService service = new ServiceProxy(SampleService.class).newInstance();
	
	public String index() {
		service.getSampleList("test");
		return "sample/index";
	}
	
	public Object fileupload(MultipartRequest multipartRequest) {
		System.out.println("name => " + multipartRequest.getParameter("name"));
		return new Object();
	}
	
}
