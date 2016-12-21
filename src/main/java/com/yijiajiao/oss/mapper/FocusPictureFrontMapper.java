package com.yijiajiao.oss.mapper;

import java.util.List;
import java.util.Map;


public interface FocusPictureFrontMapper {
	 
	int releasefocus(Map<String,Object> param);
	
	List<Map<String,Object>> showFrontFocus(Map<String,Object> param);
	
	List<Map<String,Object>> getByBelongs(String belongs);

	int saveFrontFocus(Map<String,Object> param);

	int deleteFocus(Map<String,Object> param);
}
