package com.yijiajiao.oss.util;

import com.yijiajiao.oss.mapper.GoodsInfoFrontMapper;
import com.yijiajiao.oss.service.GoodsInfoFrontService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduledUtil {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private GoodsInfoFrontService goodsinfofrontService;

	@Autowired
	private GoodsInfoFrontMapper mapper;
	private static String goodinfo_2 = Config.getString("goodinfo_2");

	private static String redisIp = Config.getString("redis.ip");
	private static int redisPort = Config.getInt("redis.port");






}
