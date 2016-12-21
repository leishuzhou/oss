package com.yijiajiao.oss.util;

import com.sun.jersey.api.client.ClientResponse;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 短信发送工具
 * 
 * @author
 * 
 */
public class SMSUtil {
	
	/** 短信服务商提供的用户名 **/
	private static final String smsSpUserName = "grts";
	/** 短信服务商提供的密码 **/
	private static final String smsSpPassword = "dxfs20151209";
	/** 短信服务商提供的apiKey **/
	private static final String apikey = "2eee080d1703f1d404de7f289b94c27f";
	/** 0表示GBK格式编码，1表示UTF-8格式编码；默认为0（GBK格式编码）。 **/
	private static final int encode = 1;
	
	/**
	 * @param phoneNum
	 * @param content
	 * @return
	 */
	public static String sendSms(String phoneNum , String content) {
		StringBuffer url = new StringBuffer("http://m.5c.com.cn/api/send/?");
		try {
			url.append("apikey=" + apikey);
			url.append("&username=" + smsSpUserName);
			url.append("&password=" + smsSpPassword);
			url.append("&mobiles=" + phoneNum);
			url.append("&encode=" + encode);
			url.append("&message=" + content + "【好学生】");
			ClientResponse response = SolutionUtil.resource(url.toString() , null , RequestMethod.POST+"");
			InputStream is = response.getEntityInputStream();
			Reader reader = new InputStreamReader(is);
			BufferedReader sr = new BufferedReader(reader);
			StringBuffer resultStr = new StringBuffer();
			String lineStr = "";
			while ((lineStr = sr.readLine()) != null) {
				resultStr.append(lineStr);
			}
			return resultStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/*
	public static void main(String [] args) {
		SMSUtil.sendSms("13520085712" , "欢迎使用好学生，您的验证码为：" + 123456);
	}*/
}
