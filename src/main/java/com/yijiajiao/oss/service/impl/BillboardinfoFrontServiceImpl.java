package com.yijiajiao.oss.service.impl;

import com.yijiajiao.oss.mapper.BillboardInfoFrontMapper;
import com.yijiajiao.oss.service.BillboardInfoFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BillboardinfoFrontServiceImpl implements BillboardInfoFrontService {
  
  @Autowired
  BillboardInfoFrontMapper mapper;

 
	/**
	 * 首页展示焦点图的位置
	 */
	@Override
	public List<Map<String,Object>>  showbill(String area) {
		return this.mapper.showbill(area);
	}
	
	@Override
	public List<Map<String,Object>> getid(String area) {
			return mapper.billById(area);
	}
	
}
