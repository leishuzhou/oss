package com.yijiajiao.oss.util;

import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {


	/**
	 * 获取当前时间 string
	 * 
	 * @return
	 */
	public static String dateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
		String time = dateFormat.format(new Date());
		return time;
	}

	/**
	 * 获取当前时间 date
	 * 
	 * @return
	 */
	public static Date getdateTime() {
		Date date = new Date();
		new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
		return date;
	}

	public static long date() {
		long ld = 0;
		try {
			URL url = new URL("http://www.bjtime.cn");// 取得资源对象
			URLConnection uc = url.openConnection();// 生成连接对象
			uc.connect(); // 发出连接
			ld = uc.getDate(); // 取得网站日期时间
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ld;

	}

	public static Date timestampToDate(Timestamp tt) {
		return new Date(tt.getTime());
	}

	/**
	 * 计算时间差
	 * 
	 * @param
	 */
	public static String timeDiffer(String d1, String d2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = new Date();
		Date date2 = new Date();
		String timediffer = "";
		try {
			date1 = df.parse(d1);
			date2 = df.parse(d2);
			long diff = date1.getTime() - date2.getTime();
			long days = diff / (1000 * 60);
			timediffer = String.valueOf(days);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timediffer;
	}

	
	public static String solNo() {
		StringBuffer buffer = new StringBuffer();
		int a[] = new int[6];
		int s = 0;
		Date date = new Date();
		StringBuffer ss = buffer;
		ss.append(date.getTime());
		for (int i = 0; i < a.length; i++) {
			a[i] = (int) (10 * (Math.random()));
			s = a[i];
			buffer.append(String.valueOf(s));
		}
		String bu = String.valueOf(buffer);
		bu = buffer.substring(1, 19);
		return bu;
	}


	/**
	 * 获取当前时间 string
	 *
	 * @return
	 */
	public static Date transByString(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(time);
		return date;
	}

	

}
