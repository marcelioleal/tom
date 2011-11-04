package org.tom.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

	
	public static String today(){
		Date date = new Date();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	public static String now(){
		Date date = new Date();
		Format formatter = new SimpleDateFormat("HH:mm:ss");
		return formatter.format(date);
	}
	
	public static String todayNow(){
		Date date = new Date();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}
}