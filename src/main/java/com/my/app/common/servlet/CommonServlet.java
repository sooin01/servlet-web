package com.my.app.common.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.my.app.common.util.MultipartRequest;
import com.my.app.common.util.ResolveView;
import com.my.app.sample.controller.SampleController;

public class CommonServlet extends HttpServlet {

	private static final long serialVersionUID = 8126510494883231847L;
	
	private static SampleController sampleController = new SampleController();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding("EUC-KR");
		}
		response.setCharacterEncoding("UTF-8");
		
		String requestURI = request.getRequestURI();
		
		if (requestURI.startsWith("/sample")) {
			if (requestURI.endsWith("/index")) {
				String result = sampleController.index();
				ResolveView.jspView(request, response, result);
			} else if (requestURI.endsWith("/fileupload")) {
				MultipartRequest multipartRequest = new MultipartRequest(request);
				Object result = sampleController.fileupload(multipartRequest);
				ResolveView.jsonView(response, result);
			} else if (requestURI.endsWith("/view")) {
				String result = sampleController.view(request);
				ResolveView.jspView(request, response, result);
			} else if (requestURI.endsWith("/viewAjax")) {
				Map<String, String> map = sampleController.viewAjax(request);
				String json = new Gson().toJson(map);
				response.getWriter().print(json);
			}
		}
	}
	
}
