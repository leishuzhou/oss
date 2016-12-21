package com.yijiajiao.oss.controller;


import com.yijiajiao.oss.service.MessageService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.Map;


@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    /**
     * 发送短信(单发)
     *
     * @param
     * @return
     */
    @PostMapping("/message/sendmessage")
    public ResultWrapper sendMessae(Map<String, Object> param) {

        Map<String, Object> result = messageService.saves(param);
        return ResultWrapper.ok(result);
    }

    /**
     * 发送短信(群发)
     *
     * @param
     * @return
     */
    @PostMapping("/message/sendmessageall")
    public ResultWrapper sendMessaeAll(Map<String, Object> param) {

        Map<String, Object> result = messageService.save(param);
        return ResultWrapper.ok(result);
    }

    /**
     * 查询消息列表
     *
     * @param
     * @param
     * @return
     */
    @GetMapping("/message/getpage")
    public ResultWrapper getPage(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String marks) {
        ResultWrapper ResultWrapper = new ResultWrapper();
      /*  MessageEntity me = new MessageEntity();
        DetachedCriteria dc = DetachedCriteria.forClass(me.getClass());
        Map<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.put("marks", new String[]{marks});
        HqlGenerateUtil.fillDetachedCriteria(dc, me, parameterMap);
        Page pagelist = messageService.pageList(dc, pageNo, pageSize, marks);
        if (pagelist != null) {
            ResultWrapper.setSucResult(pagelist);
        } else {
            ResultWrapper.setFailMsg(SystemStatus.UNAUTHORIZED);
        }*/
        PageWrapper<Map<String, Object>> result = messageService.pageList(pageNo, pageSize,marks);

        return ResultWrapper;
    }

    /**
     * 查看消息
     *
     * @param id
     * @return
     */
    @GetMapping("/message/getmessage/{id}")
    public ResultWrapper getMessage(
            @RequestParam int id) {

        Map<String, Object> result = messageService.get(id);
        return ResultWrapper.ok(result);


    }


    /**
     * 电商消息（根据用户名，已读未读，单发）
     *
     * @param
     * @param
     * @return
     */
    @GetMapping("/message/getfrontpages")
    public ResultWrapper getFrontPages(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String objecter,
            @RequestParam String status) {

        ResultWrapper ResultWrapper = new ResultWrapper();
      /*  MessageEntity me = new MessageEntity();
        DetachedCriteria dc = DetachedCriteria.forClass(me.getClass());
        Map<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.put("objecter", new String[]{objecter});
        parameterMap.put("status", new String[]{status});
        //Map<String,Object> otherdata = new HashMap<String ,Object>();
        HqlGenerateUtil.fillDetachedCriteria(dc, me, parameterMap);
        Page pagelist = messageService.query(dc, pageNo, pageSize, objecter, status);


        //pagelist.setOtherData(otherdata);
        if (pagelist != null) {
            ResultWrapper.setSucResult(pagelist);
        } else {
            ResultWrapper.setFailMsg(SystemStatus.UNAUTHORIZED);
        }*/
        // 调用服务
        PageWrapper<Map<String, Object>> result = messageService.query(pageNo, pageSize,objecter, status);
        return ResultWrapper;
    }


}
