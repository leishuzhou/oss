package com.yijiajiao.oss.controller;

import com.eduspace.eduplatform.util.exception.MyException;
import com.yijiajiao.oss.service.DemoService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.List;
import java.util.Map;

/**
 * @author tbc on 2016/10/19 15:33:30.
 */
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/demo")
    public ResultWrapper demo(
            @RequestParam String param1,
            @RequestParam(required = false) Integer param2
    ) throws Exception {
        // 第一件事，校验参数，此处省。。。

        // 调用服务
        List<Map<String, Object>> result = demoService.demo(param1, param2);

        // 构造返回形式
        return ResultWrapper.ok(result);
    }

    @GetMapping("/pageDemo")
    public ResultWrapper pageDemo(
            @RequestParam(required = false) String param1,
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize
    ) throws MyException {
        // 第一件事，校验参数，此处省。。。

        // 调用服务
        PageWrapper<Map<String, Object>> result = demoService.demo2(param1, pageNum, pageSize);

        // 构造返回形式
        return ResultWrapper.ok(result);
    }


}
