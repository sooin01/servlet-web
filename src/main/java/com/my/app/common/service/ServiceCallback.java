package com.my.app.common.service;

import java.lang.reflect.Method;

import com.my.app.common.dao.CommonDao;
import com.my.app.common.vo.SqlSessionVo;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ServiceCallback implements MethodInterceptor {
	
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
			
			CommonDao.commit(method.getName());
		} catch (Exception e) {
			e.printStackTrace();
			
			CommonDao.rollback(method.getName());
			
			throw e;
		} finally {
			CommonDao.close(method.getName());
		}
		
		return result;
	}
	
}