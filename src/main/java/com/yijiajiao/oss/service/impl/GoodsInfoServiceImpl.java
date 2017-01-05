package com.yijiajiao.oss.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.gson.*;
import com.yijiajiao.oss.controller.GoodsInfoController;
import com.yijiajiao.oss.domain.vo.FrontListBean;
import com.yijiajiao.oss.domain.vo.GoodsInfoBean;
import com.yijiajiao.oss.domain.vo.IdsBean;
import com.yijiajiao.oss.mapper.GoodsInfoFrontMapper;
import com.yijiajiao.oss.mapper.GoodsInfoMapper;
import com.yijiajiao.oss.service.GoodsInfoFrontService;
import com.yijiajiao.oss.service.GoodsInfoService;
import com.yijiajiao.oss.util.BegUtils;
import com.yijiajiao.oss.util.Config;
import com.yijiajiao.oss.util.ResultWrapper;
import com.yijiajiao.oss.util.SolutionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.tbc.mybatis.plugin.CastUtil;
import pub.tbc.mybatis.plugin.Objs;
import redis.clients.jedis.Jedis;

import java.util.*;

@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Autowired
    GoodsInfoMapper mapper;
    @Autowired
    GoodsInfoFrontMapper frontMapper;
    @Autowired
    GoodsInfoFrontService service;


    private static String redisIp = Config.getString("redis.ip");
    private static int redisPort = Config.getInt("redis.port");
    private Logger log = LoggerFactory.getLogger(GoodsInfoServiceImpl.class);
    static int maxSize = 1000;

    @Override
    public GoodsInfoBean get(int id) {
        return mapper.getByGoodsId(id);
    }

    @Transactional
    @Override
    public Map<String, Object> save(Map<String, Object> param) {
        mapper.save(param);
        frontMapper.save(param);
        return param;
    }

    @Override
    public List<Map<String, Object>> List() {
        List<Map<String, Object>> entity = mapper.findAll();
        return entity;
    }

    @Transactional
    @Override
    public Map<String, Object> saveorupdate(Map<String, Object> param) {
        mapper.updateGoods(param);
        return param;
    }

    @Transactional
    @Override
    public int changeOfPostion(int id1, int sort2, int id2, int sort1) {
        return mapper.changeOfPostion(ImmutableMap.of("id1", (Object) id1, "sort2", sort2, "id2", id2, "sort1", sort1));
    }

    @Override
    public List<Map<String, Object>> getfocusarea(String belongs, String area) {
        ResultWrapper resultbean = new ResultWrapper();
        long start = System.currentTimeMillis();
        List<Map<String, Object>> entity = mapper.getGoodsByBelongsAndArea(ImmutableMap.of("belongs", (Object) belongs, "area", area));

        if (belongs.equals("7") && area.equals("2") || area.equals("4")) {
            return entity;
        } else {
            // 调用课程接口
            StringBuffer sb = new StringBuffer();
            IdsBean ids = new IdsBean();

            for (int i = 0; i < entity.size(); i++) {
                //获取openId
                sb.append(entity.get(i).get("openId") + ",");
                ids.setIds(sb.substring(0, sb.toString().length() - 1));
            }
            List<GoodsInfoBean> list1 = new ArrayList<>();
            //获取课程信息
            List<Map> _list = BegUtils.queryWaresByIds(ids);
            for (Map o : _list) {
                GoodsInfoBean b = new Gson().fromJson(new Gson().toJson(o), GoodsInfoBean.class);
                list1.add(b);
            }

            List<Map<String, Object>> mapList2 = new ArrayList<>();
            for (int i = 0; i < entity.size(); i++) {
                Map<String, Object> map2 = new HashMap();
                map2.put("stageCode", CastUtil.castString(list1.get(i).getStageCode()));
                map2.put("waresType", list1.get(i).getWaresType());
                map2.put("area", entity.get(i).get("area"));
                map2.put("belongs", entity.get(i).get("belongs"));
                map2.put("sort", entity.get(i).get("sort"));
                map2.put("id", entity.get(i).get("id"));
                map2.put("name", entity.get(i).get("name"));
                map2.put("openId", entity.get(i).get("open_id"));
                map2.put("img", entity.get(i).get("img"));
                map2.put("url", entity.get(i).get("url"));
                mapList2.add(map2);
            }
            return mapList2;
        }
    }

    @Transactional
    @Override
    public int releasefocus(String belongs, String area) {
        frontMapper.deleteFrontGoods(ImmutableMap.of("belongs", (Object) belongs, "area", area));
        int a = frontMapper.releaseFrontGoods(ImmutableMap.of("belongs", (Object) belongs, "area", area));
        FrontListBean result = service.getGoods(belongs, area);
        //String resultJsonStr = SolutionUtil.httpRest(goodinfo_2, "/" + belongs + "/" + area, null, null, "GET");
        Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
            @Override
            public JsonElement serialize(Double src, java.lang.reflect.Type typeOfSrc,
                                         JsonSerializationContext context) {
                if (src == src.longValue())
                    return new JsonPrimitive(src.longValue());
                return new JsonPrimitive(src);
            }
        }).create();

        /*Map<String, Map<String, Object>> obj = JSON.parseObject(resultJsonStr, Map.class);
        Map<String, Object> map = obj.get("result");*/

        Jedis jd = new Jedis(redisIp, redisPort);
        jd.select(2);
        String key = belongs + ":" + area;
        jd.del(key);

        jd.set(key, JSON.toJSONString(result));
        log.debug("{}", jd.get(key));
        jd.close();
        return a;
    }

    @Override
    public List<Map<String, Object>> getdisbale(int id) {
        return null;
    }

    @Override
    public Map<String, Object> saveorupdates(Map<String, Object> param) {
        ResultWrapper resultbean = new ResultWrapper();
        long start = System.currentTimeMillis();
        String resultJsonStr = "";
        JSONObject json = null;
        GoodsInfoBean entity = mapper.getByGoodsId(CastUtil.castInt(param.get("id")));
        Map<String, Object> map = new HashMap();
        if (entity.getBelongs().equals("7") && (entity.getArea().equals("2"))) {
            // 教师接口
            resultJsonStr = BegUtils.getUserByOPenId(param.get("openId").toString());
            if (!resultJsonStr.equals("查询结果为空")) {
                json = JSONObject.parseObject(resultJsonStr);
                entity.setName(json.getString("storeName"));
                entity.setOpenId(json.getString("userOpenId"));
                entity.setImg(json.getString("teachPic"));
            }
            mapper.updateGoods(entity);

        } else if (entity.getBelongs().equals("7") && entity.getArea().equals("4")) {
            // 教师接口
            resultJsonStr = BegUtils.getUserByOPenId(param.get("openId").toString());
            if (!resultJsonStr.equals("查询结果为空")) {
                json = JSONObject.parseObject(resultJsonStr);
                entity.setName(json.getString("storeName"));
                entity.setOpenId(json.getString("userOpenId"));
                entity.setImg(json.getString("teachPic"));
            }
            mapper.updateGoods(entity);

        } else {
            // 商品接口
            resultJsonStr = BegUtils.getGoodsById(param.get("openId").toString());
            if (!resultJsonStr.equals("没数据")) {
                json = JSONObject.parseObject(resultJsonStr);
                entity.setName(json.get("curriculumName").toString());
                entity.setOpenId(json.get("id").toString());
                entity.setImg(json.get("cover").toString());
            }
            mapper.updateGoods(entity);
        }
        return map;
    }

    @Override
    public ResultWrapper hotSearch(String searchName, int type) {
        ResultWrapper result = new ResultWrapper();
        Jedis jd = new Jedis(redisIp, redisPort);
        jd.select(2);
        //课程
        if (2 == type) {
            String hotCurriculum = jd.get("hotCurriculum");
            Map<String, Object> obj = JSON.parseObject(hotCurriculum);
            final Map<String, Integer> countMap = new LinkedHashMap<String, Integer>(){
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                    // 当前记录数大于设置的最大的记录数，删除最旧记录（即最近访问最少的记录）
                    return size() > maxSize;
                }
            };
            final Map<String, Integer> countMaps = hotSearch(obj, searchName);
            countMap.putAll(countMaps);
            Map<String, Integer> topMap = hotTopSearch(countMap);
            jd.set("hotCurriculum", JSON.toJSONString(countMap));
            jd.set("hotTopCurriculum", JSON.toJSONString(topMap));
            //热搜排行前4
            System.out.println("hotCurriculum:"+jd.get("hotCurriculum"));
            System.out.println("hotTopCurriculum:"+jd.get("hotTopCurriculum"));
            jd.close();
        } else {
            //
            String hotStore = jd.get("hotStore");
            Map<String, Object> obj = JSON.parseObject(hotStore);
            final Map<String, Integer> countMap = new LinkedHashMap<String, Integer>(){
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                    // 当前记录数大于设置的最大的记录数，删除最旧记录（即最近访问最少的记录）
                    return size() > maxSize;
                }
            };
            final Map<String, Integer> countMaps = hotSearch(obj, searchName);
            countMap.putAll(countMaps);
            Map<String, Integer> topMap = hotTopSearch(countMap);

            jd.set("hotStore", JSON.toJSONString(countMap));
            //热搜排行前4
            jd.set("hotTopStore", JSON.toJSONString(topMap));

            jd.close();
        }
        return result;
    }

    public static Map<String, Integer> hotSearch(Map<String, Object> obj, String searchName) {
        final Map<String, Integer> countMap = Maps.newHashMap();
        Integer value = searchName.isEmpty() ? 1 : CastUtil.castInt(searchName) + 1;
        countMap.put(searchName, value);
        if (obj != null) {
            for (String k : obj.keySet()) {
                Integer objValue;
                if (searchName.contains(k)){
                    objValue  = Objs.isEmpty(obj.get(k)) ? 1 : CastUtil.castInt(obj.get(k)) + 1;
                    countMap.put(k, objValue);
                }else {
                    countMap.put(k, CastUtil.castInt(obj.get(k)));
                }
            }
        }

        //这里将map.entrySet()转换成list
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(countMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //升序排序
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        Map<String, Integer> topMap = new LinkedHashMap<>();
        for(Map.Entry<String,Integer> mapping:list){
            topMap.put(mapping.getKey(),mapping.getValue());
        }
        return topMap;

    }

    public static Map<String, Integer> hotTopSearch(final Map<String, Integer> countMap) {
        List<String> keyList = Ordering.from(new Comparator<String>() {
            @Override
            public int compare(String k1, String k2) {
                return countMap.get(k1) - countMap.get(k2);
            }
        }).greatestOf(countMap.keySet(), 4);
        Map<String, Integer> topMap = new HashMap<>();
        for (int i = 0; i < keyList.size(); i++) {
            topMap.put(keyList.get(i), countMap.get(keyList.get(i)));
        }
        return topMap;
    }



}
