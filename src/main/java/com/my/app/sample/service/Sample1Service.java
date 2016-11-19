package com.my.app.sample.service;

import org.apache.ibatis.session.SqlSession;

import com.my.app.common.dao.SqlSessionDao;
import com.my.app.common.service.ServiceProxy;

public class Sample1Service {
	
	private SqlSession session = new SqlSessionDao();
	
	private Sample2Service sample2Service = ServiceProxy.getInstance(Sample2Service.class);
	
	public void insert(String id) {
		selectList2();
		
		session.insert("insert", id);
		
		session.selectList("Sample1.selectList");
		
		selectList2();
		
		session.delete("delete", id);
	}
	
	public void selectList2() {
		session.selectList("Sample1.selectList2");
		
		sample2Service.selectList3();
	}
	
}
