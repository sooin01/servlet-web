package com.my.app.common.service;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.my.app.common.dao.CommonDao;
import com.my.app.common.vo.SqlSessionVo;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ServiceCallback implements MethodInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceCallback.class);
	
	//request오면 요청마다 각각 독립적으로 thread 생김. 
	public static ThreadLocal<SqlSessionVo> threadLocal = new ThreadLocal<SqlSessionVo>();
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object result = null;
		
		try {
			if (threadLocal.get() == null) {
				//thread에 내 sql 정보를 담아.. 나만 쓸거야. 처음에 호출된 메소드 : method.getName()
				threadLocal.set(new SqlSessionVo(method.getName(), CommonDao.getSqlSession()));
			}
			
			result = proxy.invokeSuper(obj, args);
			
			// 처음에 호출된 메소드와 현재 메소드가 동일하면 commit
			SqlSessionVo sqlSessionVo = ServiceCallback.threadLocal.get();
			if (sqlSessionVo != null
					&& method.getName().equals(sqlSessionVo.getMethodName())
					&& (method.getName().startsWith("insert")
							|| method.getName().startsWith("update")
							|| method.getName().startsWith("delete"))) {
				logger.debug("{} commit!", sqlSessionVo.getSqlSession());
				sqlSessionVo.getSqlSession().commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			// 처음에 호출된 메소드와 현재 메소드가 동일하면 rollback
			SqlSessionVo sqlSessionVo = ServiceCallback.threadLocal.get();
			if (sqlSessionVo != null && method.getName().equals(sqlSessionVo.getMethodName())) {
				logger.debug("{} rollback!", sqlSessionVo.getSqlSession());
				sqlSessionVo.getSqlSession().rollback();
			}
			
			throw e;
		} finally {
			// 처음에 호출된 메소드와 현재 메소드가 동일하면 close
			SqlSessionVo sqlSessionVo = ServiceCallback.threadLocal.get();
			if (sqlSessionVo != null && method.getName().equals(sqlSessionVo.getMethodName())) {
				logger.debug("{} close!", sqlSessionVo.getSqlSession());
				sqlSessionVo.getSqlSession().close();
				ServiceCallback.threadLocal.remove();
			}
		}
		
		return result;
	}
	
}