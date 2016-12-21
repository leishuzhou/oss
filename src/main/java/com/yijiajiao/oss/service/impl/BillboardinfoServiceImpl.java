package com.yijiajiao.oss.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.yijiajiao.oss.domain.vo.OtherResultBean;
import com.yijiajiao.oss.domain.vo.UserStoreInfoBean;
import com.yijiajiao.oss.mapper.BillboardInfoFrontMapper;
import com.yijiajiao.oss.mapper.BillboardInfoMapper;
import com.yijiajiao.oss.service.BillboardInfoService;
import com.yijiajiao.oss.util.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.tbc.mybatis.plugin.Objs;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillboardinfoServiceImpl implements BillboardInfoService {

    @Autowired
    BillboardInfoMapper mapper;
    @Autowired
    BillboardInfoFrontMapper frontMapper;

    private static String redisIp = Config.getString("redis.ip");
    private static int redisPort = Config.getInt("redis.port");
    private static String goodinfo_2 = Config.getString("goodinfo_2");
    private String teacher_server = Config.getString("teacher_server");
    private String teacher_login = Config.getString("teacher_login");

    private Logger log = LoggerFactory.getLogger(BillboardinfoServiceImpl.class);

    /**
     * id查询
     */
    @Override
    public Map<String, Object> get(int id) {
        ResultWrapper resultWrapper = new ResultWrapper();
        Map<String, Object> entity = mapper.getById(id);
        if (Objs.isEmpty(entity)) {
            resultWrapper.bad(SystemStatus.UNAUTHORIZED.toString());
        }
        return entity;
    }

    /**
     * 保存添加数据
     */
    @Transactional
    @Override
    public Map<String, Object> save(Map<String, Object> param) {
        mapper.saveBill(param);
        frontMapper.saveFrontBill(param);
        return param;

    }

    @Override
    public List<Map<String, Object>> List() {
        return mapper.findAll();
    }


    /**
     * 上移下移改变焦点图位置
     */
    @Transactional
    @Override
    public int changeOfPostion(int id1, int sort2, int id2, int sort1) {
        return mapper.changeOfPostion(ImmutableMap.of("id1", (Object) id1, "sort2", sort2, "id2", id2, "sort1", sort1));
    }

    @Override
    public List<Map<String, Object>> getfocusarea(String area) {
        return mapper.getBillByArea(area);
    }

    @Transactional
    @Override
    public int releaseBill(String area) {
        frontMapper.deleteFrontBill(ImmutableMap.of("area", (Object) area));
        return frontMapper.releaseFrontBill(ImmutableMap.of("area", (Object) area));
    }


    @Transactional
    @Override
    public Map<String, Object> saveorupdates(Map<String, Object> param) {
        String resultJsonStr = "";
        JSONObject json = null;
        // 教师接口
        resultJsonStr = BegUtils.getUserByOPenId(param.get("openId").toString());
        Map<String,Object> map=new HashMap<>();
        if (!resultJsonStr.equals("查询结果为空")) {
            json = JSONObject.parseObject(resultJsonStr);
            map.put("userName", json.getString("name"));
            map.put("img", json.getString("teachPic"));
            map.put("id",param.get("id"));
            map.put("url",param.get("url"));
            mapper.saveOrUpdate(map);
        }
        return map;
    }
}
