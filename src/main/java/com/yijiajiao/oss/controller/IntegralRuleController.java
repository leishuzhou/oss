package com.yijiajiao.oss.controller;

import com.yijiajiao.oss.service.IntegralRuleService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.Map;

@RestController
public class IntegralRuleController {
    @Autowired
    IntegralRuleService integralRuleService;

    /**
     * 通过id获取积分
     *
     * @param id
     * @return
     */

    @GetMapping("/integral/getById")
    public ResultWrapper getIntegration(
            @RequestParam int id,
            @RequestParam(required = false) Double price) {
        Map<String, Object> result = integralRuleService.get(id, price);
        return ResultWrapper.ok(result);
    }

    /**
     * 添加积分规则
     *
     * @param \
     * @return
     */
    @PostMapping("/integral/add")
    public ResultWrapper add(@RequestBody  Map<String,Object> param) {
        int result = integralRuleService.save(param);
        return ResultWrapper.ok("success");
    }

    /**
     * 更新操作
     *
     * @param
     * @return
     */
    @PostMapping("/integral/edit")
    public ResultWrapper exitIntegration(@RequestBody  Map<String,Object> param) {
        int result = integralRuleService.saveOrUpdate(param);
        return ResultWrapper.ok("success");
    }

 /**
     * 根据状态查询操作
     *
     * @param status
     * @return
     *//*
    @GetMapping("/integral/querybystatus/{status}")
    public ResultWrapper querybyStatus(
            @PathVariable("status") String status) {

        List<Map<String, Object>> result = integralRuleService.querybystatus(status);
        return ResultWrapper.ok(result);
    }*/

    /**
     * 根据行为获取积分规则
     *
     * @param ruleType
     * @param ruleGroup
     * @return
     */
    /*@GetMapping("/integral/getIntegral")
    public ResultWrapper getIntegral(
            @RequestParam("ruleType") int ruleType,
            @RequestParam("ruleGroup") int ruleGroup) {

        List<Map<String, Object>> result = integralRuleService.getIntegral(ruleType, ruleGroup);
        return ResultWrapper.ok(result);
    }*/

    /**
     * 获取积分规则列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/integral/getList")
    public ResultWrapper getList(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize) {
        PageWrapper<Map<String, Object>> result = integralRuleService.getList(pageNo, pageSize);
        return ResultWrapper.ok(result);

    }


}
