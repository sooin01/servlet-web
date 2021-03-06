package com.my.app.file.service;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.my.app.common.dao.SqlSessionDao;
import com.my.app.common.util.MultipartRequest;
import com.my.app.file.vo.FileVo;

public class UploadService {

	private SqlSession session = new SqlSessionDao();
	
	public List<FileVo> getList() {
		return session.selectList("FileMapper.getList");
	}
	
	public FileVo get(FileVo fileVo) {
		return session.selectOne("FileMapper.get", fileVo);
	}
	
	public int insertUpload(HttpServletRequest request) throws FileUploadException, IOException {
		MultipartRequest multipartRequest = new MultipartRequest(request);
		int result = 0;
		
		for (Entry<String, FileItem> entry : multipartRequest.getFileMap().entrySet()) {
			FileItem fileItem = entry.getValue();
			
			if (StringUtils.isNotBlank(fileItem.getName()) && fileItem.getSize() > 0) {
				FileVo fileVo = new FileVo();
				fileVo.setId(UUID.randomUUID().toString());
				fileVo.setName(fileItem.getName());
				fileVo.setContent(Base64.encodeBase64String(fileItem.get()));
				fileVo.setBytes(fileItem.get());
				result += session.insert("FileMapper.insert", fileVo);
			}
		}
		
		return result;
	}
	
}
