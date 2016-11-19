package com.my.app.common.dao;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

public class CommonDataSourceFactory implements DataSourceFactory {

	private final String prefix = "java:/comp/env/";
	
	private DataSource dataSource;

	@Override
	public void setProperties(Properties props) {
		Context initContext;
		try {
			initContext = new InitialContext();
			DataSource realDataSource = (DataSource) initContext.lookup(prefix + "jdbc/orcl");
			
			Log4JdbcCustomFormatter logFormatter = new Log4JdbcCustomFormatter();
			logFormatter.setLoggingType(LoggingType.MULTI_LINE);
			logFormatter.setMargin(19);
			logFormatter.setSqlPrefix("SQL:::");
			
			Log4jdbcProxyDataSource dataSourceSpied = new Log4jdbcProxyDataSource(realDataSource);
			dataSourceSpied.setLogFormatter(logFormatter);
			
			dataSource = dataSourceSpied;
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}
	
	
}
