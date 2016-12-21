package com.yijiajiao.oss.service;

import java.util.List;
import java.util.Map;

public interface BillboardInfoService {

    Map<String, Object> save(Map<String, Object> param);

    Map<String, Object> get(int id);

    List<Map<String, Object>> List();


    List<Map<String, Object>> getfocusarea(String area);

    int releaseBill(String area);

    int changeOfPostion(int id1, int sort1, int id2, int sort2);

    Map<String, Object> saveorupdates(Map<String, Object> param);
}
