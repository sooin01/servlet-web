package com.my.app.common.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.my.app.common.vo.LoginVo;

public class LoginSessionListener implements HttpSessionListener {
	
	private static Map<String, LoginVo> loginMap = Collections.synchronizedMap(new HashMap<String, LoginVo>());
	
	public static LoginVo get(String userId) {
		return loginMap.get(userId);
	}
	
	public static LoginVo put(String userId, LoginVo loginVo) {
		return loginMap.put(userId, loginVo);
	}
	
	public static LoginVo remove(String userId) {
		return loginMap.remove(userId);
	}
	
	public static synchronized boolean isDuplicate(String userId) {
		return loginMap.containsKey(userId);
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("Session created: " + se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("Session destroyed: " + se.getSession());
	}

}
