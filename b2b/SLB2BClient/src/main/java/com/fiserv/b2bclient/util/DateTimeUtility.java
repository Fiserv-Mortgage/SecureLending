package com.fiserv.b2bclient.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtility {

	public static String getDateandTime(String string) {
		// TODO Auto-generated method stub
		Date DateNow = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyyddMMHHmmss");
		String strDate = Dateformat.format(DateNow).toString();
		return strDate;
	
	}

}
