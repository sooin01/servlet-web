package com.my.app.common.vo;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.my.app.common.listener.LoginSessionListener;

public class LoginVo implements HttpSessionBindingListener {
	
	private String userId = "test123";
	
	private String userName = "test";
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println("valueBound: " + event.getSession() + " > " + userId);
		
		LoginSessionListener.put(userId, this);
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println("valueUnbound: " + event.getSession() + " > " + userId);
		
		LoginSessionListener.remove(userId);
	}
	
}
