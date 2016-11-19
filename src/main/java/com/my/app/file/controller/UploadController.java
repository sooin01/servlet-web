package com.my.app.file.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.app.common.service.ServiceProxy;
import com.my.app.common.util.MultipartRequest;
import com.my.app.file.service.UploadService;
import com.my.app.file.vo.FileVo;

public class UploadController {
	
	private UploadService uploadService = ServiceProxy.getInstance(UploadService.class);
	
	public String upload(HttpServletRequest request) {
		request.setAttribute("fileList", uploadService.getList());
		return "file/upload";
	}
	
	public String upload(HttpServletRequest request, MultipartRequest multipartRequest) {
		FileVo fileVo = multipartRequest.getFile("file");
		uploadService.insert(fileVo);
		request.setAttribute("fileList", uploadService.getList());
		return "file/upload";
	}
	
	public void download(HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream sos = null;
		
		try {
			FileVo fileVo = new FileVo();
			fileVo.setId(request.getParameter("id"));
			fileVo = uploadService.get(fileVo);
			
//			byte[] b = Base64.decodeBase64(fileVo.getContent()); // CLOB
			byte[] b = fileVo.getBytes(); // BLOB
			
			response.setContentType("application/octet-stream");
	        response.setContentLength(b.length);
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileVo.getName() + "\"");
			response.getOutputStream().write(b);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sos != null) try { sos.close(); } catch (IOException e) { }
		}
	}
	
}
