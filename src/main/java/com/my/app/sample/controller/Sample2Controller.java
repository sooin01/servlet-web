package com.my.app.sample.controller;

import com.my.app.common.service.ServiceProxy;
import com.my.app.sample.service.Sample2Service;

public class Sample2Controller {

	private Sample2Service sample2Service = ServiceProxy.getInstance(Sample2Service.class);
	
	public void selectList3() {
		sample2Service.selectList3();
	}
	
}
