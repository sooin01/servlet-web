package com.my.app.common.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.my.app.common.util.ExcelUtil;
import com.my.app.common.util.MultipartRequest;
import com.my.app.common.util.ResolveView;
import com.my.app.file.controller.UploadController;
import com.my.app.sample.controller.Sample1Controller;
import com.my.app.sample.controller.Sample2Controller;
import com.my.app.sample.controller.SampleController;

public class CommonServlet extends HttpServlet {

	private static final long serialVersionUID = 8126510494883231847L;
	
	private static SampleController sampleController = new SampleController();
	private static Sample1Controller sample1Controller = new Sample1Controller();
	private static Sample2Controller sample2Controller = new Sample2Controller();
	private static UploadController uploadController = new UploadController();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("MS949");
		response.setCharacterEncoding("MS949");
		
		String requestURI = request.getRequestURI();
		
		
		if (requestURI.startsWith("/sample/")) {
			if (requestURI.endsWith("/index")) {
				String result = sampleController.index(request);
				ResolveView.jspView(request, response, result);
			} else if (requestURI.endsWith("/fileupload")) {
				MultipartRequest multipartRequest = new MultipartRequest(request);
				Object result = sampleController.fileupload(multipartRequest);
				ResolveView.jsonView(response, result);
			} else if (requestURI.endsWith("/view")) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				String result = sampleController.view(request);
				ResolveView.jspView(request, response, result);
			} else if (requestURI.endsWith("/viewAjax")) {
				Map<String, String> map = sampleController.viewAjax(request);
				String json = new Gson().toJson(map);
				response.getWriter().print(json);
			} else if (requestURI.endsWith("/excel")) {
				try {
					request.setAttribute("excel", ExcelUtil.readExcel());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String result = requestURI.substring(requestURI.indexOf("/") + 1);
				ResolveView.jspView(request, response, result);
			} else if (requestURI.endsWith("/excel2")) {
				Object obj = request.getSession().getAttribute("rowList");
				System.out.println("rowList: " + obj);
				
				String result = "/jsp/excel2.jsp";
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(result);
				requestDispatcher.forward(request, response);
			} else {
				String result = requestURI.substring(requestURI.indexOf("/") + 1);
				ResolveView.jspView(request, response, result);
			}
		}
		
		if (requestURI.startsWith("/sample1/")) {
			sample1Controller.selectList();
		}
		
		if (requestURI.startsWith("/sample2/")) {
			sample2Controller.selectList3();
		}
		
		if (requestURI.startsWith("/file/")) {
			if (requestURI.endsWith("/upload") && request.getMethod().equalsIgnoreCase("GET")) {
				String result = uploadController.upload(request);
				ResolveView.jspView(request, response, result);
			} else if (requestURI.endsWith("/upload") && request.getMethod().equalsIgnoreCase("POST")) {
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				String result = uploadController.upload(request, new MultipartRequest(request));
				ResolveView.jspView(request, response, result);
			} else if (requestURI.endsWith("/download")) {
				uploadController.download(request, response);
			}
		}
	}
	
}
