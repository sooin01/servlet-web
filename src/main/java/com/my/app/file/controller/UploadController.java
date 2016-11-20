package com.my.app.file.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.my.app.common.service.ServiceProxy;
import com.my.app.file.service.UploadService;
import com.my.app.file.vo.FileVo;

public class UploadController {
	
	private UploadService uploadService = ServiceProxy.getInstance(UploadService.class);
	
	/**
	 * 업로드 화면
	 * 
	 * @param request
	 * @return
	 */
	public String uploadView(HttpServletRequest request) {
		request.setAttribute("fileList", uploadService.getList());
		return "file/upload";
	}
	
	/**
	 * 파일 업로드
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws FileUploadException 
	 */
	public String upload(HttpServletRequest request) throws FileUploadException, IOException {
		// DB CLOB, BLOB 둘 다 1건 저장(6~7초 걸림)
		uploadService.insertUpload(request);
		request.setAttribute("fileList", uploadService.getList());
		return "file/upload";
	}
	
	/**
	 * 파일 다운로드
	 * 
	 * @param request
	 * @param response
	 */
	public void download(HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream sos = null;
		
		try {
			FileVo fileVo = new FileVo();
			fileVo.setId(request.getParameter("id"));
			// 1건 조회(CLOB 8~9초, BLOB 3~4초)
			fileVo = uploadService.get(fileVo);
			
			// CLOB (Base64 인코딩/디코딩 때문에 느림)
//			byte[] b = Base64.decodeBase64(fileVo.getContent());
			// BLOB
			byte[] b = fileVo.getBytes();
			
			response.setContentType("application/octet-stream");
	        response.setContentLength(b.length);
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileVo.getName(), "UTF-8") + "\"");
			response.getOutputStream().write(b);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sos != null) try { sos.close(); } catch (IOException e) { }
		}
	}
	
}
