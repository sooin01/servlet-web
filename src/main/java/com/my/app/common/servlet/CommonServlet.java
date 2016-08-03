package com.my.app.common.servlet;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.my.app.common.util.ExcelUtil;
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
		
		try {
			Context initContext = new InitialContext();
			Context webContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) webContext.lookup("jdbc/test");
			Connection dbCon = ds.getConnection();
			
			CallableStatement cs = dbCon.prepareCall("{call sp_test(?, ?, ?, ?)}");
			cs.setString(1, "1");
			cs.setString(2, "name");
			cs.registerOutParameter(3, Types.CHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			boolean execute = cs.execute();
			System.out.println("execute: " + execute);
			System.out.println(cs.getString(3));
			System.out.println(cs.getString(4));
			
			if (execute) {
				ResultSet rs = cs.getResultSet();
				
				while (rs.next()) {
					System.out.println(rs.getString(1));
					System.out.println(rs.getString(2));
				}
				
				rs.close();
			}
			
			dbCon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (requestURI.startsWith("/sample")) {
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
	}
	
}
