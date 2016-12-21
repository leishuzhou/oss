package com.yijiajiao.oss.service;
import pub.tbc.mybatis.plugin.PageWrapper;
import java.util.List;
import java.util.Map;


public interface MessageService {

    Map<String, Object> save(Map<String, Object> param);

    Map<String, Object> get(int id);

    List<Map<String, Object>> List();

    Map<String, Object> saveorupdate(Map<String, Object> param);

    List<Map<String, Object>> getdisbale(int id);

    List<Map<String, Object>> save(String objecter, String title, String content, int marks);

    PageWrapper<Map<String, Object>> pageList( Integer pageNum, Integer pageSize,String marks);

    Map<String, Object> saves(Map<String, Object> param);

    PageWrapper<Map<String, Object>> query( Integer pageNum,  Integer pageSize,String objecter,String status);

    List<Map<String, Object>> querybystatus(String status);
}
