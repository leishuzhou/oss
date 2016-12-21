package com.yijiajiao.oss.service;


import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.List;
import java.util.Map;

public interface IntegralRuleService {

    int save(Map<String, Object> param);

    Map<String, Object> get(int id, Double price);

    List<Map<String, Object>> getIntegral(int ruleType, int ruleGroup);

    Map<String, Object> saveorupdate(Map<String, Object> param);

    List<Map<String, Object>> getdisbale(int id);


    List<Map<String, Object>> querybystatus(String status);

    PageWrapper<Map<String, Object>> getList(Integer pageNo, Integer pageSize);

    int saveOrUpdate(Map<String, Object> param);


}
