package com.mambu.xbrl.server.util;

import java.util.Map;

public class ParamUtils {
	
	/**
	 * Returns a param if it's found or null if it's empty
	 * @param param
	 * @param params
	 * @return
	 */
	public static String get(String param, Map<String, String[]> params) {
		for (String key : params.keySet()) {
			if (key.equalsIgnoreCase(param)) { // params are case insensitive
				return params.get(key)[0];
			}
		}		
		return null;
	}
}
