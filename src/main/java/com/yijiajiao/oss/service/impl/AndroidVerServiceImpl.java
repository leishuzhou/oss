package com.yijiajiao.oss.service.impl;

import com.google.common.collect.ImmutableMap;
import com.yijiajiao.oss.mapper.AndroidVerMapper;
import com.yijiajiao.oss.service.AndroidVerService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.tbc.mybatis.plugin.CastUtil;
import pub.tbc.mybatis.plugin.PageParams;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.List;
import java.util.Map;

@Service
public class AndroidVerServiceImpl implements AndroidVerService {

    @Autowired
    AndroidVerMapper mapper;


    /**
     * 保存添加数据
     */
    @Transactional
    @Override
    public  Map<String,Object> commitIsssueVer(Map<String, Object> param) {
        ResultWrapper result=new ResultWrapper();
        //获取之前最新的版本号
        Map<String,Object> map=mapper.newAndroidVer(CastUtil.castInt(param.get("appType")));
        //新增
        mapper.commitIsssueVer(param);
        //新增之后的新版本
        Map<String,Object> newMap=mapper.newAndroidVer(CastUtil.castInt(param.get("appType")));
        mapper.autoVersionCode(ImmutableMap.of("versionCode",map.get("versionCode"),"id",newMap.get("id")));
       return newMap;
    }
    @Override
   public Map<String,Object> newAndroidVer(int appType){
       return mapper.newAndroidVer(appType);
   }




    @Override
    public PageWrapper<Map<String, Object>> pageList(Integer appType,Integer pageNo, Integer pageSize) {
        if (null == pageNo) pageNo = 1;
        if (null == pageSize) pageSize = 5;
         PageParams<Map<String, Object>> p = new PageParams<>(pageNo, pageSize);
        List<Map<String, Object>> data = mapper.pageList(ImmutableMap.of("appType",appType ,"pageParams",p));
        return p.getPageWrapper(data);

    }

}
