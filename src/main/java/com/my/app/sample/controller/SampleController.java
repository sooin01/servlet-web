package com.my.app.sample.controller;

import com.my.app.common.util.MultipartRequest;
import com.my.app.sample.service.impl.SampleServiceImpl;

public class SampleController {

	private static SampleController sampleController = new SampleController();
	
	public static SampleController getInstance() {
		return sampleController;
	}
	
	public String index() {
		SampleServiceImpl.getInstance().getSampleList("test");
		return "sample/index";
	}
	
	public Object fileupload(MultipartRequest multipartRequest) {
		System.out.println("name => " + multipartRequest.getParameter("name"));
		return new Object();
	}
	
}
