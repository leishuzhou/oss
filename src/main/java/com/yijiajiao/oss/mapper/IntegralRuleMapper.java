package com.yijiajiao.oss.mapper;


import java.util.List;
import java.util.Map;

public interface IntegralRuleMapper  {

    Map<String, Object> getById(int id);

   int  updatePrice(Map<String, Object> param);

    List<Map<String, Object>> querybystatus(String intestatus);


    List<Map<String, Object>> getIntegral(int ruleType, int ruleGroup);


    int save(Map<String, Object> param);

    int saveOrUpdate( Map<String, Object> param);

    List<Map<String, Object>> getLists(Map<String, Object> param);


}
