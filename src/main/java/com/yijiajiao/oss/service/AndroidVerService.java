package com.yijiajiao.oss.service;


import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.Map;

public interface AndroidVerService {

    Map<String,Object> commitIsssueVer(Map<String, Object> param);

    Map<String, Object> newAndroidVer(int appType);

    PageWrapper<Map<String, Object>> pageList(Integer appType,Integer pageNo, Integer pageSize);


}
