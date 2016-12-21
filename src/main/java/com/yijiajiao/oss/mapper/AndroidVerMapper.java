package com.yijiajiao.oss.mapper;


import java.util.List;
import java.util.Map;

public interface AndroidVerMapper {

    int commitIsssueVer(Map<String, Object> map);
    Map<String,Object> newAndroidVer(int appType);

    int  autoVersionCode(Map<String,Object> map);

    List<Map<String, Object>> pageList(Map<String,Object> map);


}
