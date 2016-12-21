package com.yijiajiao.oss.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class SolutionUtil {


    /**
     * httpclient请求器  （格式1）
     *
     * @param server     服务器地址
     * @param url        资源URL
     * @param headParams 请求头参数列表
     * @param bodyParam  Body参数
     * @param method     请求方式（POST,GET,PUT,DELETE）
     * @return
     */

    @SuppressWarnings("rawtypes")
    public static String httpRest(String server, String url, Map<String, Object> headParams, Object bodyParam, String method) {
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(config);
        WebResource r = c.resource("http://" + server + url);
        log.info("\n请求其它系统路径：http://{}", server + url);

        Builder builder = r.header("Content-Type", MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
        ClientResponse response = null;
        if (headParams != null) {
            Iterator i = headParams.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry en = (Map.Entry) i.next();
                builder.header((String) en.getKey(), en.getValue());
            }
        }
        if (method.equals("POST")) {
            response = builder.post(ClientResponse.class, bodyParam);
        } else if (method.equals("GET")) {
            response = builder.get(ClientResponse.class);
        } else if (method.equals("PUT")) {
            response = builder.put(ClientResponse.class, bodyParam);
        } else if (method.equals("DELETE")) {
            response = builder.delete(ClientResponse.class);
        }
        String result = response.getEntity(String.class);
        return result;
    }


    /**
     * httpclient请求器  （格式2） //不支持请求头放参数
     *
     * @param url       服务器地址    + 资源URL
     * @param bodyParam Body参数
     * @param method    请求方式（POST,GET,PUT,DELETE）
     * @return
     */

    public static ClientResponse resource(String url, Object bodyParam, String method) {
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(config);
        WebResource r = c.resource(url);
        ClientResponse response = null;
        if ("POST".equals(method)) {
            response = r.header("Content-Type", MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class, bodyParam);
        } else if ("GET".equals(method)) {
            response = r.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        } else if ("PUT".equals(method)) {
            response = r.header("Content-Type", MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                    .put(ClientResponse.class, bodyParam);
        }
        return response;
    }


    // json转化成对象
    public static Object getTransObject(String param, Class<?> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        //Object obj = mapper.readValue(URLDecoder.decode(param, "UTF-8"), clazz);
        Object obj = mapper.readValue(param, clazz);
        return obj;
    }

    public static Date long2Date(long time) throws ParseException {
        Date result = null;
        String since = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time * 1000));
        result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(since);
        return result;
    }

    public static String getDateStr() throws ParseException {
        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return result;
    }


    public static void send2(String telephone, String msg) {
        StringBuffer sb = new StringBuffer("http://m.5c.com.cn/api/send/?"); // 创建StringBuffer对象用来操作字符串
        try {
            sb.append("apikey=2eee080d1703f1d404de7f289b94c27f");  // APIKEY
            sb.append("&username=grts"); //用户名
            sb.append("&password=123456"); // 向StringBuffer追加密码
            sb.append("&mobile=" + telephone); // 向StringBuffer追加手机号码
            sb.append("&content=" + URLEncoder.encode(msg + "【亿家教】", "GBK"));// 向StringBuffer追加消息内容转URL标准码
            URL url = new URL(sb.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开url连接
            connection.setRequestMethod("POST");// 设置url请求方式 ‘get’ 或者 ‘post’
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())); // 发送
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    public static  void main(String[] args) {
     *//*  String content="测试新短信业务发送速度";
         SMSGateway g=SMSGateway.gateway();
         String t="18210454836";*//*
       send2("18210454836","测试新短信业务发送速度");


    }*/

    public static String optString(JSONObject json, String key) {
        if (json.keySet().contains(key)) {
            return null;
        } else {
            return (String) json.put(key, null);
        }
    }


}
