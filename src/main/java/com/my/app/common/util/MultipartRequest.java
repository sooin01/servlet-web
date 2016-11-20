package com.my.app.common.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

public class MultipartRequest {
	
	private Map<String, String> parameterMap = new LinkedHashMap<String, String>();
	
	private Map<String, FileItem> fileMap = new LinkedHashMap<String, FileItem>();
	
	public MultipartRequest(HttpServletRequest request) throws FileUploadException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			parse(request);
		}
	}
	
	private void parse(HttpServletRequest request) throws FileUploadException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory(Config.SIZE_THRESHOLD, new File(Config.REPOSITORY));
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		
		List<FileItem> fileItemList = fileUpload.parseRequest(request);
		
		// name이 같은 경우 처리 보완해야 함. parameter, file value를 배열로...
		for (FileItem fileItem : fileItemList) {
			if (fileItem.isFormField()) {
				parameterMap.put(fileItem.getFieldName(), fileItem.getString(request.getCharacterEncoding()));
			} else {
				// 파일업로드
				if (StringUtils.isNotBlank(fileItem.getName()) && fileItem.getSize() > 0) {
					// 파일업르도 정보를 Map에 저장
					fileMap.put(fileItem.getFieldName(), fileItem);
				}
			}
		}
	}
	
	public String getParameter(String name) {
		return parameterMap.get(name);
	}
	
	public FileItem getFile(String name) {
		return fileMap.get(name);
	}
	
}
