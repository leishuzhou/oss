package com.yijiajiao.oss.service.impl;

import com.google.common.collect.ImmutableMap;
import com.yijiajiao.oss.mapper.FocusPictureFrontMapper;
import com.yijiajiao.oss.service.FocusPictureFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FocusPictureFrontServiceImpl implements FocusPictureFrontService {
  
  

  @Autowired
  FocusPictureFrontMapper mapper;

	/**
	 * 首页展示焦点图的位置
	 */
	@Override
	public List<Map<String,Object>> showfocus(String belongs, String area) {
			return  mapper.showFrontFocus(ImmutableMap.of("belongs", (Object) belongs, "area", area));

	}
	
}
