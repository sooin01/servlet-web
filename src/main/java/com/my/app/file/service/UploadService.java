package com.my.app.file.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.my.app.common.dao.SqlSessionDao;
import com.my.app.file.vo.FileVo;

public class UploadService {

	private SqlSession session = new SqlSessionDao();
	
	public List<FileVo> getList() {
		return session.selectList("FileMapper.getList");
	}
	
	public FileVo get(FileVo fileVo) {
		return session.selectOne("FileMapper.get", fileVo);
	}
	
	public int insert(FileVo fileVo) {
		return session.insert("FileMapper.insert", fileVo);
	}
	
}
