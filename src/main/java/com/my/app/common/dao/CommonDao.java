package com.my.app.common.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDao {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonDao.class);
	
	private static SqlSessionFactory sqlSessionFactory;

	public static void init() throws IOException {
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 디비 세션을 얻음
	 */
	public static SqlSession getSqlSession() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		logger.debug("SqlSession get =>  {}", sqlSession);
		return sqlSession;
	}
	
}
