package com.yijiajiao.oss.service;


import java.util.List;
import java.util.Map;


public interface FocusPictureService {

	Map<String,Object> saveFocus(Map<String, Object> param);

	Map<String,Object> get(Integer id)  ;

	List<Map<String,Object>> List();

	Map<String,Object> updateFocus(Map<String, Object> param) ;

	List<Map<String,Object>> getdisbale(int id);
	
	List<Map<String,Object>> getfocusarea(String belongs, String area);
	
	List<Map<String,Object>> releasefocus(String belongs, String area);
	
	int changeOfPostion(int id1, int sort1, int id2, int sort2);
	

}
