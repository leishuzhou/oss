package com.yijiajiao.oss.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.*;
import com.yijiajiao.oss.domain.vo.GoodsBean;
import com.yijiajiao.oss.mapper.GoodsInfoFrontMapper;
import com.yijiajiao.oss.service.GoodsInfoFrontService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * spring管理的定时器
 * 
 * @author tbc tianbencai@e-eduspace.com
 * @version 1.0 {2016年1月29日 上午10:22:40}
 */
@Component
public class WaresAutoSoldStop {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private GoodsInfoFrontService goodsinfofrontService;

	@Autowired
	private GoodsInfoFrontMapper mapper;
	private static String goodinfo_2 = Config.getString("goodinfo_2");

	private static String redisIp = Config.getString("redis.ip");
	private static int redisPort = Config.getInt("redis.port");

	/**
	 * CRON表达式 含义 ：<br>
	 * "s m h d/m m d/w y"||"s m h d/m m d/w" "0 0 12 * * ?" 每天中午十二点触发 <br>
	 * "0 15 10 ? * *" 每天早上10：15触发 <br>
	 * "0 15 10 * * ?" 每天早上10：15触发<br>
	 * "0 15 10 * * ? *" 每天早上10：15触发<br>
	 * "0 15 10 * * ? 2005" 2005年的每天早上10：15触发 <br>
	 * "0 * 14 * * ?" 每天从下午2点开始到2点59分每分钟一次触发 <br>
	 * "0 0/5 14 * * ?" 每天从下午2点开始到2：55分结束每5分钟一次触发 <br>
	 * "0 0/5 14,18 * * ?" 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 <br>
	 * "0 0-5 14 * * ?" 每天14:00至14:05每分钟一次触发 <br>
	 * "0 10,44 14 ? 3 WED" 三月的每周三的14：10和14：44触发 <br>
	 * "0 15 10 ? * MON-FRI" 每个周一、周二、周三、周四、周五的10：15触发 <br>
	 * 
	 * @author tbc tianbencai@e-eduspace.com
	 * @throws
	 */
	@Scheduled(cron = "0 0/10 * * * ? ") // 间隔30分钟执行
	public void goodsAuto() {
		try {
			List<GoodsBean> goodsList = mapper.getShowGoods();
			List<Map<String, Object>> newList = new ArrayList<>();

			for (GoodsBean b : goodsList) {
				Map<String, Object> map = new HashMap();
				String resultJsonStr = SolutionUtil.httpRest(goodinfo_2, "/" + b.getFrontBelongs() + "/" + b.getFrontArea(), null, null, "GET");
				if (!"5".equals(b.getFrontArea())) {
					Map<String, Object> newMap = new HashMap();
					Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
						@Override
						public JsonElement serialize(Double src, java.lang.reflect.Type typeOfSrc,
													 JsonSerializationContext context) {
							if (src == src.longValue())
								return new JsonPrimitive(src.longValue());
							return new JsonPrimitive(src);
						}
					}).create();

					Map<String, Map<String, Object>> obj = JSON.parseObject(resultJsonStr, Map.class);
					map = obj.get("result");
					newMap.put("list", map.get("list"));
					newList.add(newMap);
					Jedis jd = new Jedis(redisIp, redisPort);
					jd.select(2);
					String key = b.getFrontBelongs() + ":" + b.getFrontArea();
					jd.del(key);
					jd.set(key, JSON.toJSONString(map));
					jd.close();
				} else {
					Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
						@Override
						public JsonElement serialize(Double src, java.lang.reflect.Type typeOfSrc,
													 JsonSerializationContext context) {
							if (src == src.longValue())
								return new JsonPrimitive(src.longValue());
							return new JsonPrimitive(src);
						}
					}).create();
					List<List<?>> lastList = new ArrayList<List<?>>();
					for (Map map1 : newList) {
						Map<String, Object> map2 = new HashMap();
						for (Object k : map1.keySet()) {
							lastList.addAll((Collection<? extends List<?>>) map1.get(k));
						}
					}
					List<List<?>> list = new ArrayList<>();
					if (lastList.size() > 6) {
						Random r = new Random();
						List<Integer> tempList = new ArrayList<>();
						for (int i = 0; i < 6; i++) {
							int nextInt = r.nextInt(12);
							if (!tempList.contains(nextInt)) { //判断不重复
								tempList.add(nextInt);
								list.add(lastList.get(nextInt));
							} else {
								i--;
							}
						}
					}

					Map<String, Map<String, Object>> obj = JSON.parseObject(resultJsonStr, Map.class);
					map = obj.get("result");
					map.put("list",list);
					newList.removeAll(newList);
					Jedis jd = new Jedis(redisIp, redisPort);
					jd.select(2);
					String key = b.getFrontBelongs() + ":" + 5;
					jd.del(key);
					jd.set(key, JSON.toJSONString(map));
					jd.close();
				}
			}
		} catch (Exception e) {
			log.error("异常：{}   -    {}", e.getClass(), e.getMessage());
			throw new RuntimeException(e);
		}
	}


}
