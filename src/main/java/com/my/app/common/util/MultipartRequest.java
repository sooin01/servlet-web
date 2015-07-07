package com.my.app.common.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.my.app.common.vo.FileVo;

public class MultipartRequest {
	
	private Map<String, String> parameterMap = new HashMap<String, String>();
	
	private Map<String, FileVo> fileMap = new HashMap<String, FileVo>();
	
	public MultipartRequest(HttpServletRequest request) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			parse(request);
		}
	}
	
	private void parse(HttpServletRequest request) {
		DiskFileItemFactory factory = new DiskFileItemFactory(10 * 1024 * 1024, new File("D:/upload"));
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		
		try {
			List<FileItem> fileItemList = fileUpload.parseRequest(request);
			
			// name이 같은 경우 처리 보완해야 함. parameter, file value를 배열로...
			for (FileItem fileItem : fileItemList) {
				if (fileItem.isFormField()) {
					parameterMap.put(fileItem.getFieldName(), CommonUtil.getParameter(fileItem.getInputStream()));
				} else {
					// 파일업로드
					if (StringUtils.isNotBlank(fileItem.getName()) && fileItem.getSize() > 0) {
						// 파일업르도 정보를 Map에 저장
						FileVo fileVo = new FileVo();
						fileMap.put(fileItem.getFieldName(), fileVo);
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getParameter(String name) {
		return parameterMap.get(name);
	}
	
	public FileVo getFile(String name) {
		return fileMap.get(name);
	}
	
}
