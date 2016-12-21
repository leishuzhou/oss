package com.yijiajiao.oss.service;

import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.List;
import java.util.Map;

/**
 * @author tbc on 2016/10/19 15:33:43.
 */
public interface DemoService {

    List<Map<String, Object>> demo(String param1, Integer param2);

    PageWrapper<Map<String, Object>> demo2(String param1, Integer pageNum, Integer pageSize);


}
