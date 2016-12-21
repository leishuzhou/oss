package com.yijiajiao.oss.mapper;

import java.util.List;
import java.util.Map;


public interface BillboardInfoMapper{

	Map<String,Object> getById(Integer id);

	int changeOfPostion(Map<String,Object> param);
	
	List<Map<String,Object>> getBillByArea(String area);

	int saveBill(Map<String, Object> param);

	List<Map<String,Object>> findAll();


	int saveOrUpdate(Map<String, Object> param);


}
