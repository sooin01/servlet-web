package com.my.app.common.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class CommonUtil {

	public static String getParameter(InputStream input) throws IOException {
		String result = IOUtils.toString(input);
		input.close();
		return result;
	}
	
}
