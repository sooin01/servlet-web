package com.my.app.common.dao;

import static java.lang.reflect.Proxy.newProxyInstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.my.app.common.service.ServiceCallback;

public class SqlSessionDao implements SqlSession {
	
	private final static Logger logger = LoggerFactory.getLogger(SqlSessionDao.class);
	
	private final SqlSession sqlSessionProxy;
	
	public SqlSessionDao() {
		this.sqlSessionProxy = (SqlSession) newProxyInstance(
				getClass().getClassLoader(),
		        new Class[] { SqlSession.class },
		        new sqlSessionProxy());
	}
	
	private class sqlSessionProxy implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			try {
				SqlSession sqlSession = ServiceCallback.threadLocal.get().getSqlSession();
				logger.trace("Sqlsession execute => {}", sqlSession);
				return method.invoke(sqlSession, args);
			} catch (Throwable t) {
				throw t;
			}
		}
	}
	
	@Override
	public <T> T selectOne(String statement) {
		return this.sqlSessionProxy.selectOne(statement);
	}

	@Override
	public <T> T selectOne(String statement, Object parameter) {
		return this.sqlSessionProxy.selectOne(statement, parameter);
	}

	@Override
	public <E> List<E> selectList(String statement) {
		return this.sqlSessionProxy.selectList(statement);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter) {
		return this.sqlSessionProxy.selectList(statement, parameter);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		return this.sqlSessionProxy.selectList(statement, parameter, rowBounds);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return this.sqlSessionProxy.selectMap(statement, mapKey);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return this.sqlSessionProxy.selectMap(statement, parameter, mapKey);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		return this.sqlSessionProxy.selectMap(statement, parameter, mapKey, rowBounds);
	}

	@Override
	public void select(String statement, Object parameter, ResultHandler handler) {
		this.sqlSessionProxy.select(statement, parameter, handler);
	}

	@Override
	public void select(String statement, ResultHandler handler) {
		this.sqlSessionProxy.select(statement, handler);
	}

	@Override
	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		this.sqlSessionProxy.select(statement, parameter, rowBounds, handler);
	}

	@Override
	public int insert(String statement) {
		return this.sqlSessionProxy.insert(statement);
	}

	@Override
	public int insert(String statement, Object parameter) {
		return this.sqlSessionProxy.insert(statement, parameter);
	}

	@Override
	public int update(String statement) {
		return this.sqlSessionProxy.update(statement);
	}

	@Override
	public int update(String statement, Object parameter) {
		return this.sqlSessionProxy.delete(statement, parameter);
	}

	@Override
	public int delete(String statement) {
		return this.sqlSessionProxy.delete(statement);
	}

	@Override
	public int delete(String statement, Object parameter) {
		return this.sqlSessionProxy.delete(statement, parameter);
	}

	@Override
	public void commit() {
		this.sqlSessionProxy.commit();
	}

	@Override
	public void commit(boolean force) {
		this.sqlSessionProxy.commit(force);
	}

	@Override
	public void rollback() {
		this.sqlSessionProxy.rollback();
	}

	@Override
	public void rollback(boolean force) {
		this.sqlSessionProxy.rollback(force);
	}

	@Override
	public List<BatchResult> flushStatements() {
		return this.sqlSessionProxy.flushStatements();
	}

	@Override
	public void close() {
		this.sqlSessionProxy.close();
	}

	@Override
	public void clearCache() {
		this.sqlSessionProxy.clearCache();
	}

	@Override
	public Configuration getConfiguration() {
		return this.sqlSessionProxy.getConfiguration();
	}

	@Override
	public <T> T getMapper(Class<T> type) {
		return this.sqlSessionProxy.getMapper(type);
	}

	@Override
	public Connection getConnection() {
		return this.sqlSessionProxy.getConnection();
	}

}
