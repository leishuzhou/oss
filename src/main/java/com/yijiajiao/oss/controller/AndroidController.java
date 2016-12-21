package com.yijiajiao.oss.controller;

import com.yijiajiao.oss.service.AndroidVerService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.Map;

@RestController
public class AndroidController {
    @Autowired
    AndroidVerService service;


    /**
     * 发布安卓下载新版本
     *
     * @param param
     * @return
     */
    @PostMapping("/androidVer/issue")
    public ResultWrapper add(@RequestBody Map<String, Object> param) {
        service.commitIsssueVer(param);
        return ResultWrapper.ok("操作成功");
    }

    /**
     * 获取最新版本
     *
     * @return
     */
    @GetMapping("/androidVer/newAndroidVer")
    public ResultWrapper newAndroidVer(@RequestParam int appType) {
        ResultWrapper wrapper = new ResultWrapper();
        Map<String, Object> result = service.newAndroidVer(appType);

        return ResultWrapper.ok(result);
    }

    /**
     * 安卓版本分页列表
     *
     * @param appType
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/androidVer/pageList")
    public ResultWrapper pageDemo(
            @RequestParam(required = false) Integer appType,
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize
    ) {
        PageWrapper<Map<String, Object>> result = service.pageList(appType, pageNo, pageSize);
        return ResultWrapper.ok(result);
    }


}
