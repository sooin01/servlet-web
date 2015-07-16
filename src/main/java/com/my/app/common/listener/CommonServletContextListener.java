package com.my.app.common.listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CommonServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		try {
			String url = sce.getServletContext().getRealPath("/WEB-INF/classes/properties/config.properties");
			Properties props = new Properties();
			props.load(new FileInputStream(url));
			sce.getServletContext().setAttribute("config", props);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
