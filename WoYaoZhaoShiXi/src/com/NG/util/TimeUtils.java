package com.NG.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
	

	public static String stringToTime(long l) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// MM/dd/yyyy HH:mm:ss
		// 前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		java.util.Date dt = new Date(l * 1000);
		String sDateTime = sdf.format(dt); // 得到精确到秒的表示：08/31/2006 21:08:00

		return sDateTime;

	}

}