package com.my.app.sample.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.my.app.common.listener.LoginSessionListener;
import com.my.app.common.service.ServiceProxy;
import com.my.app.common.util.MultipartRequest;
import com.my.app.sample.service.SampleService;

public class SampleController {
	
	private SampleService service = ServiceProxy.getInstance(SampleService.class);
	
	public String index(HttpServletRequest request) {
		request.getSession().setAttribute("user", LoginSessionListener.getInstance());
		request.getSession().removeAttribute("user");
		service.getSampleList("test");
		return "sample/index";
	}
	
	public Object fileupload(MultipartRequest multipartRequest) {
		System.out.println("name => " + multipartRequest.getParameter("name"));
		return new Object();
	}
	
	public String view(HttpServletRequest request) {
		String text = request.getParameter("text");
		System.out.println("view => " + text);
		request.setAttribute("text", text);
		return "sample/view";
	}
	
	public Map<String, String> viewAjax(HttpServletRequest request) {
		String text = request.getParameter("text");
		System.out.println("view => " + text);
		Map<String, String> map = new HashMap<String, String>();
		map.put("text", text);
		return map;
	}
	
}
