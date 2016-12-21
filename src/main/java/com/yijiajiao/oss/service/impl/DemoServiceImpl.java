package com.yijiajiao.oss.service.impl;

import com.google.common.collect.ImmutableMap;
import com.yijiajiao.oss.mapper.DemoMapper;
import com.yijiajiao.oss.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.tbc.mybatis.plugin.PageParams;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.List;
import java.util.Map;

/**
 * @author tbc on 2016/10/19 15:33:56.
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper mapper;

    @Override
    public List<Map<String, Object>> demo(String param1, Integer param2) {
        return mapper.demo(ImmutableMap.of("param1", (Object) param1, "param2", param2));
    }

    @Override
    public PageWrapper<Map<String, Object>> demo2(String param1, Integer pageNum, Integer pageSize) {
        if (null == pageNum) pageNum = 1;
        if (null == pageSize) pageSize = 2;

        PageParams<Map<String, Object>> p = new PageParams<Map<String, Object>>(pageNum, pageSize);
        List<Map<String, Object>> data = mapper.demo(ImmutableMap.of("param1", (Object) param1, "pageParams", p));
        return p.getPageWrapper(data);

    }
}
