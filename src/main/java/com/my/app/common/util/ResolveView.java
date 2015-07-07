package com.my.app.common.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ResolveView {
	
private final static String PREFIX = "/WEB-INF/views/";
	
	private final static String POSTFIX = ".jsp";

	public static void jspView(HttpServletRequest request, HttpServletResponse response, String result) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PREFIX + result + POSTFIX);
		requestDispatcher.forward(request, response);
	}
	
	public static void jsonView(HttpServletResponse response, Object result) throws IOException {
		response.getWriter().println(new Gson().toJson(result));
	}
	
}
