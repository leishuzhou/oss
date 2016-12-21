package com.yijiajiao.oss.controller;

import com.eduspace.eduplatform.util.exception.MyException;
import com.google.common.collect.ImmutableMap;
import com.yijiajiao.oss.mapper.FeedBackMapper;
import com.yijiajiao.oss.service.FeedBackService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pub.tbc.mybatis.plugin.PageWrapper;

import java.util.Map;

@RestController
public class FeedBackController {
	
    @Autowired
    FeedBackService feedbackService;
	@Autowired
	FeedBackMapper mapper;

  	
	   /**
       * 查看反馈信息
       * @param id
       * @return
       */
	  @GetMapping("/feedback/getById/{id}")
      public ResultWrapper getFeedback(
			  @PathVariable("id") Integer id
	  ) throws Exception {
		  return  feedbackService.get(id);
      }


	/**
	 * 分页查询反馈信息
	 * @param pageNo
	 * @param pageSize
	 * @param status
	 * @return
	 */
	  @GetMapping("/feedback/getpage")
      public ResultWrapper getPageper(
      		@RequestParam(required = false) int pageNo,
			@RequestParam(required = false) int pageSize,
			@RequestParam(required = false) String status) throws MyException{

		  PageWrapper<Map<String, Object>> result = feedbackService.getPageper(pageNo, pageSize, status);
		  Map<String,Object> map=  mapper.countRead();
		  Map<String,Object> map1=  mapper.countUnRead();
		  result.setOtherData(ImmutableMap.of(("readCount"),(Object)map.get("count"),"unReadCount",map1.get("count")));
		  return ResultWrapper.ok(result);

		 
     }
	  
	  /**
       * 新增反馈信息
       * @param
       * @return
       */
	  @PostMapping("/feedback/add")
      public ResultWrapper addFeed(@RequestBody Map<String,Object> param) {
		  return feedbackService.save(param);
      }
}
