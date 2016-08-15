package com.my.app.sample.service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.my.app.common.dao.CommonDao;
import com.my.app.common.dao.SqlSessionDao;

public class Sample2Service {
	
	private final static Logger logger = LoggerFactory.getLogger(Sample2Service.class);

	private SqlSession session = new SqlSessionDao();
	
	public void selectList3() {
		session.selectList("Sample1.selectList2");
	}
	
}
