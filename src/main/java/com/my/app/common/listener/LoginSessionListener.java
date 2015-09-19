package com.my.app.common.listener;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class LoginSessionListener implements HttpSessionBindingListener {
	
	private static LoginSessionListener instance = new LoginSessionListener();
	
	public static LoginSessionListener getInstance() {
		return instance;
	}
	
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println("valueBound");
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println("valueUnbound");
	}

}
