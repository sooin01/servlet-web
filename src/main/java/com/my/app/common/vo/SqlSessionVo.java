package com.my.app.common.vo;

import org.apache.ibatis.session.SqlSession;

public class SqlSessionVo {

	private String methodName;
	
	private SqlSession sqlSession;
	
	public SqlSessionVo(String methodName, SqlSession sqlSession) {
		this.methodName = methodName;
		this.sqlSession = sqlSession;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}
