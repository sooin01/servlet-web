package com.my.app.sample.service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.my.app.common.dao.SqlSessionDao;
import com.my.app.common.service.ServiceProxy;
import com.my.app.common.vo.FileVo;

public class Sample1Service {
	
	private final static Logger logger = LoggerFactory.getLogger(Sample1Service.class);

	private SqlSession session = new SqlSessionDao();
	
	private Sample2Service sample2Service = ServiceProxy.getInstance(Sample2Service.class);
	
	public void insert(String id, FileVo vo) {
		System.out.println(Thread.currentThread().getName() +  " >>>>>>>>>>>>>>>>>>>>>>>>>>>>> start");
		
		selectList2();
		
		session.insert("insert", id);
		
		session.selectList("Sample1.selectList");
		
		session.delete("delete", id);
		
		System.out.println(Thread.currentThread().getName() +  " >>>>>>>>>>>>>>>>>>>>>>>>>>>>> end");
	}
	
	public void selectList2() {
		session.selectList("Sample1.selectList2");
		
		sample2Service.selectList3();
	}
	
}
