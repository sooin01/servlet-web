package com.my.app.sample.controller;

import java.util.UUID;

import com.my.app.common.service.ServiceProxy;
import com.my.app.common.vo.FileVo;
import com.my.app.sample.service.Sample1Service;

public class Sample1Controller {

	private Sample1Service sample1Service = ServiceProxy.getInstance(Sample1Service.class);
	
	public void selectList() {
		String uuid = UUID.randomUUID().toString();
		sample1Service.insert(uuid, new FileVo());
	}
	
}
