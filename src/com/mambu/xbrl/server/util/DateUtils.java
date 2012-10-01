package com.mambu.xbrl.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Formats dates
 * 
 * @author edanilkis
 * 
 */
public class DateUtils {
	
	public static String DATE_FORMAT = "yyyy-MM-dd";

	public static SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT);

	public static String format(Date date) {
		return FORMAT.format(date);
	}
}
