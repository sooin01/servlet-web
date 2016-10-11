package com.my.app.common.dao;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

public class DBAppender extends AppenderSkeleton implements Appender {

	@Override
	public void close() {
		
	}

	@Override
	public boolean requiresLayout() {
		return true;
	}

	@Override
	protected void append(LoggingEvent event) {
		// 로그
		event.getMessage();
		
		// 예외 로그
		ThrowableInformation throwableInformation = event.getThrowableInformation();
		if (throwableInformation != null) {
			throwableInformation.getThrowable().getMessage();
		}
	}

}
