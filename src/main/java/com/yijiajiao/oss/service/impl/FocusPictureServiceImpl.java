package com.yijiajiao.oss.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.yijiajiao.oss.domain.vo.FrontListBean;
import com.yijiajiao.oss.mapper.FocusPictureFrontMapper;
import com.yijiajiao.oss.mapper.FocusPictureMapper;
import com.yijiajiao.oss.mapper.GoodsInfoFrontMapper;
import com.yijiajiao.oss.service.FocusPictureService;
import com.yijiajiao.oss.service.GoodsInfoFrontService;
import com.yijiajiao.oss.util.Config;
import com.yijiajiao.oss.util.ResultWrapper;
import com.yijiajiao.oss.util.SolutionUtil;
import com.yijiajiao.oss.util.SystemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.tbc.mybatis.plugin.Objs;
import redis.clients.jedis.Jedis;

import java.util.*;

@Service
public class FocusPictureServiceImpl implements FocusPictureService {


    @Autowired
    FocusPictureMapper mapper;
    @Autowired
    FocusPictureFrontMapper frontMapper;
    @Autowired
    GoodsInfoFrontMapper goodsMapper;
    @Autowired
    GoodsInfoFrontService service;


    private static String redisIp = Config.getString("redis.ip");
    private static int redisPort = Config.getInt("redis.port");
    private static String goodinfo_2=Config.getString("goodinfo_2");

    /**
     * id查询
     */
    @Override
    public Map<String, Object> get(Integer id) {
        return mapper.getFocusById(id);
    }

    /**
     * 保存添加数据
     */
    @Transactional
    @Override
    public Map<String, Object> saveFocus(Map<String, Object> param) {
        mapper.saveFocus(param);
        frontMapper.saveFrontFocus(param);
        return param;
    }

    @Override
    public List<Map<String, Object>> List() {
      /*  ResultBean resultbean = new ResultBean();
        try {
            List<FocusPictureEntity> entity = focuspictureDao.findAll();
            if (entity == null || entity.size() == 0) {
                resultbean.setFailMsg(SystemStatus.UNAUTHORIZED);
                return resultbean;
            }
            resultbean.setSucResult(entity);
        } catch (Exception e) {
            // TODO: handle exception
            resultbean.setFailMsg(SystemStatus.SERVER_ERROR);
            e.printStackTrace();
        }*/
        return mapper.findAll();
    }

    /**
     * 更新操作
     */
    @Transactional
    @Override
    public Map<String, Object> updateFocus(Map<String, Object> param) {

        int a = mapper.updateFocus(param);
        return param;
    }

    /**
     * 上移下移改变焦点图位置
     */
    @Transactional
    @Override
    public int changeOfPostion(int id1, int sort2, int id2, int sort1) {
        /*ResultBean resultbean = new ResultBean();
        try {
            focuspictureDao.changeOfPostion(id1, sort2, id2, sort1);
            resultbean.setSucResult("操作成功");
            return resultbean;
        } catch (Exception e) {
            resultbean.setFailMsg(SystemStatus.SERVER_ERROR);
            e.printStackTrace();
        }

        return resultbean;*/

        return mapper.changeOfPostion(ImmutableMap.of("id1", (Object) id1, "sort2", sort2, "id2", id2, "sort1", sort1));
    }

    @Override
    public List<Map<String, Object>> getdisbale(int id) {
      /*  ResultBean resultBean = new ResultBean();
        try {
            FocusPictureEntity entity = focuspictureDao.getById(id);
            if (entity == null) {
                resultBean.setFailMsg(SystemStatus.UNAUTHORIZED);
                return resultBean;
            }
            resultBean.setSucResult(entity);
        } catch (Exception e) {
            // TODO: handle exception
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
            e.printStackTrace();
        }

        return resultBean;*/

        return (List<Map<String, Object>>) mapper.getFocusById(id);
    }

    /**
     * 得到焦点图
     */
    @Override
    public List<Map<String, Object>> getfocusarea(String belongs, String area) {
        return mapper.getFocusByBlongsAndArea(ImmutableMap.of("belongs", (Object) belongs, "area", area));
    }

    @Override
    public List<Map<String, Object>> releasefocus(String belongs, String area) {
        ResultWrapper resultbean = new ResultWrapper();

        Jedis jd = new Jedis(redisIp, redisPort);

        frontMapper.deleteFocus(ImmutableMap.of("belongs", (Object) belongs, "area", area));
        int b = frontMapper.releasefocus(ImmutableMap.of("belongs", (Object) belongs, "area", area));
        List<Map<String, Object>> result = mapper.getFocusByBlongsAndArea(ImmutableMap.of("belongs", (Object) belongs, "area", area));
        if (Objs.isEmpty(result)) {
            resultbean.bad(SystemStatus.UNAUTHORIZED.toString());
        } else {
            if (belongs.endsWith("2") || belongs.equals("3") || belongs.endsWith("4") || belongs.equals("5") || belongs.equals("6")) {
                List<Map<String, Object>> goodsinfo = goodsMapper.getBelongs(belongs);
                List<Map<String, Object>> newList = new ArrayList<>();
                Map<String, Object> lastMap = new HashMap();
                String resultJsonStr="";
                for (int i=0;i<goodsinfo.size();i++){
                    Map<String, Object> map = new HashMap();
                    resultJsonStr = SolutionUtil.httpRest(goodinfo_2, "/" + belongs + "/" +goodsinfo.get(i).get("front_area"), null, null, "GET");
                    if (!"5".equals(goodsinfo.get(i).get("front_area"))) {
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
                        jd.select(2);
                        String key = belongs + ":" +goodsinfo.get(i).get("front_area");
                        jd.del(key);
                        jd.set(key, JSON.toJSONString(map));
                        jd.close();
                    } else {

                    }
                }
                Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                    @Override
                    public JsonElement serialize(Double src, java.lang.reflect.Type typeOfSrc,
                                                 JsonSerializationContext context) {
                        if (src == src.longValue())
                            return new JsonPrimitive(src.longValue());
                        return new JsonPrimitive(src);
                    }
                }).create();
                List<List<?>> lastList =new ArrayList<>();
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
                            System.out.println(nextInt);
                            tempList.add(nextInt);
                            list.add(lastList.get(nextInt));
                        } else {
                            i--;
                        }
                    }
                }

                Map<String, Map<String, Object>> obj = JSON.parseObject(resultJsonStr, Map.class);
                lastMap = obj.get("result");
                lastMap.put("list",list);
                //newList.removeAll(newList);
                jd.select(2);
                String key = belongs + ":" + 5;
                jd.del(key);
                jd.set(key, JSON.toJSONString(lastMap));
                jd.close();


            }
        }

        resultbean.setResult("操作成功");
        return result;
    }
}
