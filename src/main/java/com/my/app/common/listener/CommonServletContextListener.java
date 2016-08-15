package com.my.app.common.listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.my.app.common.dao.CommonDao;

public class CommonServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			String url = sce.getServletContext().getRealPath("/WEB-INF/classes/properties/config.properties");
			Properties props = new Properties();
			props.load(new FileInputStream(url));
			sce.getServletContext().setAttribute("config", props);
			
			CommonDao.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
