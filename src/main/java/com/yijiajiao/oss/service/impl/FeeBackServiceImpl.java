package com.yijiajiao.oss.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.eduspace.eduplatform.util.exception.MyException;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.yijiajiao.oss.domain.vo.FeedBackBean;
import com.yijiajiao.oss.domain.vo.IdsBean;
import com.yijiajiao.oss.mapper.FeedBackMapper;
import com.yijiajiao.oss.service.FeedBackService;
import com.yijiajiao.oss.util.BegUtils;
import com.yijiajiao.oss.util.DateUtils;
import com.yijiajiao.oss.util.ResultWrapper;
import com.yijiajiao.oss.util.SystemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.tbc.mybatis.plugin.CastUtil;
import pub.tbc.mybatis.plugin.PageParams;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeeBackServiceImpl implements FeedBackService {


    @Autowired
    FeedBackMapper mapper;

    /**
     * id查询
     */
    @Override
    public ResultWrapper get(Integer id) {
        ResultWrapper resultBean = new ResultWrapper();
        String resultJsonStr = "";
        JSONObject json = null;
        Map<String, Object> entity = mapper.getById(id);

        Map<String, Object> map = new HashMap();
        //判断是否为空，返回参数不匹配
        if (entity == null) {
            resultBean.bad(SystemStatus.UNAUTHORIZED.toString());
        }
        //修改查看状态
        map.put("status", 1);
        map.put("id", id);
        //保存修改
        mapper.saveOrUpdate(map);
        FeedBackBean bean = new FeedBackBean();
        try {
            //获取用户信息
            resultJsonStr = BegUtils.getUserByOPenId(CastUtil.castString(entity.get("openId")));
            if (!resultJsonStr.equals("查询结果为空")) {
                json = JSONObject.parseObject(resultJsonStr);
                bean.setImg(CastUtil.castString(json.getString("pic")));
                bean.setPhone(json.getString("username"));
                bean.setName(json.getString("name"));
            }
            bean.setContactWay(CastUtil.castString(entity.get("contactWay")));
            bean.setContent(CastUtil.castString(entity.get("content")));
            bean.setCreateTime(DateUtils.transByString(CastUtil.castString(entity.get("createTime"))));
            bean.setStatus(CastUtil.castInt(entity.get("status")));
            bean.setId(CastUtil.castInt(entity.get("id")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBean.ok(bean);
    }

    /**
     * 保存添加数据
     */
    @Transactional
    @Override
    public ResultWrapper save(Map<String, Object> param) {
        ResultWrapper result = new ResultWrapper();
        mapper.save(param);
        return result.ok("反馈成功");
    }

    @Override
    public List<Map<String, Object>> List() {
        return mapper.findAll();
    }

    /**
     * 回复消息
     */
    @Transactional
    @Override
    public Map<String, Object> saveorupdate(Map<String, Object> param) {
        ResultWrapper resultbean = new ResultWrapper();
        resultbean.setResult("回复成功");
        int a = mapper.saveOrUpdate(param);
        return null;
    }

    @Override
    public Map<String, Object> save(String content, String objecter) {
        return null;
    }


    @Override
    public PageWrapper<Map<String, Object>> getPageper (Integer pageNo, Integer pageSize, String status) throws MyException{
        if (null == pageNo) pageNo = 1;
        if (null == pageSize) pageSize = 5;
        PageParams<Map<String, Object>> p = new PageParams<>(pageNo, pageSize);
        List<Map<String, Object>> data = mapper.pageList(ImmutableMap.of("status", status, "pageParams", p));
        IdsBean ids = new IdsBean();
        StringBuffer sb = new StringBuffer();
        List<Map<String, Object>> newData = new ArrayList<>();
        List<Map<String,Object>> newList = new ArrayList<>();
        for (Map s : data) {
            sb.append(s.get("openId") + ",");
            ids.setIds(sb.toString().substring(0, sb.toString().length()));
        }
        List<Map<String,Object>> resultJsonStr = BegUtils.getUserByOPenIds(ids);
        for (Map map : resultJsonStr) {
            Map<String,Object> beanlist = new Gson().fromJson(new Gson().toJson(map), Map.class);
            newList.add(beanlist);
        }
        for (int i = 0; i < data.size(); i++ ) {
            Map<String, Object> bean = new HashMap();
            bean.put("img", newList.get(i).get("pic"));
            bean.put("phone", newList.get(i).get("username"));
            bean.put("name",  newList.get(i).get("name"));
            bean.put("status", data.get(i).get("status"));
            bean.put("content", data.get(i).get("content").toString());
            bean.put("contactWay", data.get(i).get("contactWay"));
            bean.put("createTime", data.get(i).get("createTime"));
            bean.put("id", data.get(i).get("id"));
            newData.add(bean);
        }
        return p.getPageWrapper(newData);
    }
}
