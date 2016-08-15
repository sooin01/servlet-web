package com.my.app.common.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.my.app.common.service.ServiceCallback;
import com.my.app.common.vo.SqlSessionVo;

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
	
	/**
	 * 처음에 호출된 메소드와 현재 메소드가 동일하면 commit
	 */
	public static void commit(String methodName) {
		SqlSessionVo sqlSessionVo = ServiceCallback.threadLocal.get();
		
		if (sqlSessionVo != null && methodName.equals(sqlSessionVo.getMethodName())) {
			logger.debug("{} commit!", sqlSessionVo.getSqlSession());
			sqlSessionVo.getSqlSession().commit();
		}
	}
	
	/**
	 * 처음에 호출된 메소드와 현재 메소드가 동일하면 rollback
	 */
	public static void rollback(String methodName) {
		SqlSessionVo sqlSessionVo = ServiceCallback.threadLocal.get();
		
		if (sqlSessionVo != null && methodName.equals(sqlSessionVo.getMethodName())) {
			logger.debug("{} rollback!", sqlSessionVo.getSqlSession());
			sqlSessionVo.getSqlSession().rollback();
		}
	}
	
	/**
	 * 처음에 호출된 메소드와 현재 메소드가 동일하면 close
	 */
	public static void close(String methodName) {
		SqlSessionVo sqlSessionVo = ServiceCallback.threadLocal.get();
		
		if (sqlSessionVo != null && methodName.equals(sqlSessionVo.getMethodName())) {
			logger.debug("{} close!", sqlSessionVo.getSqlSession());
			sqlSessionVo.getSqlSession().close();
			ServiceCallback.threadLocal.remove();
		}
	}
	
}
