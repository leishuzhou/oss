package com.yijiajiao.oss.mapper;
import java.util.List;
import java.util.Map;


public interface MessageMapper {
    Map<String, Object> getById(int id);

    Map<String, Object> save(Map<String, Object> param);

    Map<String, Object> saves(Map<String, Object> param);

    List<Map<String, Object>> findAll();

    Map<String, Object> saveOrUpdate(Map<String, Object> param);

    List<Map<String, Object>> querybystatus(String status);

}
