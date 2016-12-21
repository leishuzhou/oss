package com.yijiajiao.oss.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@Slf4j
@SuppressWarnings("ALL")
public class HttpClientUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * httpPost请求
     *
     * @param url
     * @param bodyParam
     * @return
     * @throws IOException
     */
    public static String httpPost(String url, Map<String, Object> bodyParam) throws IOException {
        Object obj = JSON.toJSON(bodyParam);
        String json = Request.Post(url)
                .addHeader("Content-Type", "application/json")
                .bodyString(obj.toString(), ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();
        return json;
    }

    /**
     * httpGet请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpGet(String url) throws IOException {

        //Object obj= JSON.toJSON(param);
        String json = Request.Get(url)
                .addHeader("Content-Type", "application/json")
                .execute()
                .returnContent()
                .asString();
        return json;
    }

    /**
     * @param url
     * @param bodyParam
     * @return
     * @throws IOException
     */
    public static String httpPut(String url, Map<String, Object> bodyParam) throws IOException {

        Object obj = JSON.toJSON(bodyParam);
        String json = Request.Put(url)
                .addHeader("Content-Type", "application/json")
                .bodyString(obj.toString(), ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();
        return json;
    }

    public static String httpDelete(String url) throws IOException {
        String json = Request.Delete(url)
                .addHeader("Content-Type", "application/json")
                .execute()
                .returnContent()
                .asString();
        return json;
    }
/*
    public static void main(String[] args) throws IOException {
        String url = "http://211.157.179.221:22020/yijiajiao-oss/feedback/add";
        Map<String, Object> bodyParam = new HashMap();
        *//*bodyParam.put("content","123");
        bodyParam.put("openId","c6baf9b5-5f32-47c6-96ed-6f43861cf760");
        bodyParam.put("contactWay","dfs");*//*
        // httpPost(url,bodyParam);
        String getUrl = "http://211.157.179.221:22020/yijiajiao-oss/frontfocus/showfocus/1/1";
        String json = httpGet(getUrl);
        log.info("返回结果{}", json);
    }*/


}
