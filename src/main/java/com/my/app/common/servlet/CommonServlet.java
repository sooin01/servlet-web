package com.my.app.common.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.app.common.util.MultipartRequest;
import com.my.app.common.util.ResolveView;
import com.my.app.sample.controller.SampleController;

public class CommonServlet extends HttpServlet {

	private static final long serialVersionUID = 8126510494883231847L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		
		if (requestURI.startsWith("/sample")) {
			SampleController sampleController = SampleController.getInstance();
			
			if (requestURI.contains("/index")) {
				String result = sampleController.index();
				ResolveView.jspView(request, response, result);
			} else if (requestURI.contains("/fileupload")) {
				MultipartRequest multipartRequest = new MultipartRequest(request);
				Object result = sampleController.fileupload(multipartRequest);
				ResolveView.jsonView(response, result);
			}
		}
	}
	
}
