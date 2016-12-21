package com.yijiajiao.oss.mapper;

import java.util.List;
import java.util.Map;

public interface BillboardInfoFrontMapper  {
	int releaseFrontBill(Map<String,Object> paramarea);

	List<Map<String,Object>> showbill(String area);

	List<Map<String,Object>> billById(String area);

	int  saveFrontBill(Map<String, Object> param);

	int  deleteFrontBill(Map<String,Object> param);
}
