package com.liyuting.drug.commons.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String formatDateTime(Date d){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
}
