package com.yijiajiao.oss.service;

import com.eduspace.eduplatform.util.exception.MyException;
import com.yijiajiao.oss.util.ResultWrapper;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.List;
import java.util.Map;

public interface FeedBackService {

    ResultWrapper save(Map<String, Object> param);

    ResultWrapper get(Integer id);

    List<Map<String, Object>> List();

   Map<String, Object> saveorupdate(Map<String, Object> param);


    Map<String, Object> save(String content, String objecter);


    PageWrapper<Map<String, Object>> getPageper(Integer pageNo, Integer pageSize, String status) throws MyException;




}
