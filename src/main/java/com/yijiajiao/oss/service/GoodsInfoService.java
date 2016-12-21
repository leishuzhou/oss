package com.yijiajiao.oss.service;

import com.yijiajiao.oss.domain.vo.GoodsInfoBean;
import com.yijiajiao.oss.util.ResultWrapper;

import java.util.List;
import java.util.Map;

public interface GoodsInfoService {

	Map<String,Object> save(Map<String, Object> param);

	GoodsInfoBean get(int id);

	List<Map<String,Object>> List();

	Map<String,Object> saveorupdate(Map<String, Object> param);

	List<Map<String,Object>> getdisbale(int id);

	List<Map<String,Object>> getfocusarea(String belongs, String area);
	
	int releasefocus(String belongs, String area);
	
	int changeOfPostion(int id1, int sort1, int id2, int sort2);
	
	Map<String,Object> saveorupdates(Map<String, Object> param);

	ResultWrapper hotSearch(String searchName, int type);



}
