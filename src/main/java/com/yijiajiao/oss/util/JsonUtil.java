package com.yijiajiao.oss.util;

import com.alibaba.fastjson.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;

import java.net.URLDecoder;

public class JsonUtil {
	// json转化成对象
	public static Object getTransObject(String param, Class<?> clazz)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Object obj = mapper.readValue(URLDecoder.decode(param, "UTF-8"), clazz);
		return obj;
	}
	//对象转换成json
	public static <T> String objectToJson(T t){
		JSONObject object = JSONObject.parseObject(t.toString());
		return object.toString();
	}
	
/*	//json转换成list集合
	public static <T> List<T> jsonToList(String json,Class<T> clazz){
        JSONArray arry=JSONArrayfromObject(json);
        return JSONArray..toList(arry,clazz);
	}
	//lis转成json
    public static String listToJson(List<?> list)
    {
        return JSONSerializer.toJSON(list).toString();
    }*/
}
