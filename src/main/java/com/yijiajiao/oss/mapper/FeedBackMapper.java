package com.yijiajiao.oss.mapper;
import com.eduspace.eduplatform.util.exception.MyException;

import java.util.List;
import java.util.Map;


public interface FeedBackMapper {
    Map<String, Object> getById(Integer id);

    int save(Map<String,Object> param);

    int saveOrUpdate(Map<String, Object> param);

    List<Map<String, Object>> findAll();

    List<Map<String, Object>> queryByStatus(String status);

    List<Map<String, Object>> pageList(Map<String, Object> param) ;

    Map<String,Object> countRead();

    Map<String,Object> countUnRead();


}
