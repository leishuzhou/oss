package com.yijiajiao.oss.mapper;

import java.util.List;
import java.util.Map;

public interface FocusPictureMapper {

    Map<String, Object> getFocusById(int id);

    int changeOfPostion(Map<String,Object> param);

    List<Map<String, Object>> getFocusByBlongsAndArea(Map<String,Object> param);

    int saveFocus(Map<String, Object> param);

    List<Map<String, Object>> findAll();

    int updateFocus(Map<String, Object> param);


}
