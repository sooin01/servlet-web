package com.my.app.common.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.my.app.file.vo.FileVo;

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
		MultiMap parameterMap = new MultiValueMap();
		
		String pathname = "C:/dev/neon/workspace/servlet-web/src/main/webapp/upload";
		DiskFileItemFactory factory = new DiskFileItemFactory(10 * 1024, new File(pathname));
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		
		try {
			List<FileItem> fileItemList = fileUpload.parseRequest(request);
			
			// name이 같은 경우 처리 보완해야 함. parameter, file value를 배열로...
			for (FileItem fileItem : fileItemList) {
				if (fileItem.isFormField()) {
					parameterMap.put(fileItem.getFieldName(), fileItem.getString(request.getCharacterEncoding()));
				} else {
					// 파일업로드
					if (StringUtils.isNotBlank(fileItem.getName()) && fileItem.getSize() > 0) {
						FileVo fileVo = new FileVo();
						fileVo.setId(UUID.randomUUID().toString());
						fileVo.setName(fileItem.getName());
//						fileVo.setPath(null); // DB 저장되었기 때문에 제외
						fileVo.setContent(Base64.encodeBase64String(fileItem.get()));
						fileVo.setBytes(fileItem.get());
						
						// 파일업르도 정보를 Map에 저장
						fileMap.put(fileItem.getFieldName(), fileVo);
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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
