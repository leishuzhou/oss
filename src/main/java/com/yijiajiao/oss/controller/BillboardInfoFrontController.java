package com.yijiajiao.oss.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yijiajiao.oss.domain.vo.FrontBean;
import com.yijiajiao.oss.domain.vo.OtherResultBean;
import com.yijiajiao.oss.domain.vo.UserStoreInfoBean;
import com.yijiajiao.oss.service.BillboardInfoFrontService;
import com.yijiajiao.oss.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pub.tbc.mybatis.plugin.CastUtil;

import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class BillboardInfoFrontController {
      @Autowired
      BillboardInfoFrontService billboardInfofrontService;
      private String doubt_server=Config.getString("doubt_server");
      private String doubt_login=Config.getString("doubt_login");
      private Logger log= LoggerFactory.getLogger(BillboardInfoFrontController.class);
	  /**
       * 前端展示排行榜的信息
       * @return
       */

	  @GetMapping("/frontbill/showbill/{area}")
      public ResultWrapper showBill(@PathVariable("area") String area){
		  ResultWrapper ResultWrapper=new ResultWrapper();
		  String resultJsonStr="";
		  String resultJsonStr2="";
		  OtherResultBean d=null;
		  JSONObject json = null;
		  List<Map<String,Object>> goodslist=  billboardInfofrontService.showbill(area);
		  List<Map<String,Object>> getid= billboardInfofrontService.getid(area);
	      List<FrontBean> list=new ArrayList<>();
		  if(goodslist.size()==0){
			  ResultWrapper.bad(SystemStatus.UNAUTHORIZED.toString());
		  }else{
			  for(int i=0;i<getid.size();i++){
				  try {
					  FrontBean front=new FrontBean();
					  //教师接口
					  resultJsonStr = BegUtils.getUserByOPenId(getid.get(i).get("openId").toString());
					  if (!resultJsonStr.equals("查询结果为空")) {
						  json = JSONObject.parseObject(resultJsonStr);
						  front.setImg(CastUtil.castString(json.get("storePic")));
						  front.setName(CastUtil.castString(json.get("name")));
						  front.setOpenId(CastUtil.castString(json.get("userOpenId")));
						  front.setPopularity(CastUtil.castInt(json.get("count")));
					  }

						try {
							  resultJsonStr2=SolutionUtil.httpRest(doubt_server,doubt_login+getid.get(i).get("openId"), null, null , "GET");
							  d = (OtherResultBean) SolutionUtil.getTransObject(resultJsonStr2, OtherResultBean.class);
						} catch (Exception e) {
							ResultWrapper.bad(SystemStatus.SERVER_ERROR.toString());
							e.printStackTrace();
						}
						  if((d.getCode()+"").equals("200")){
							  log.info("正确信息： "+d.getResult().toString());
					          ObjectMapper mapper = new ObjectMapper();
							  Map<String, Object> map = mapper.readValue(JacksonUtil.toJSon(d.getResult()),Map.class);
							  front.setGoodRange(CastUtil.castString(map.get("goodRange").toString()));
							  front.setReferalWay(CastUtil.castString(map.get("referalWay").toString()));
					      }else{
					    	  log.info("错误信息： "+d.getMessage());
					          ResultWrapper.bad(d.getCode(), d.getMessage());
					      }
					  front.setUrl(CastUtil.castString(goodslist.get(i).get("frontUrl").toString()));
					  list.add(front);
				  } catch (Exception e) {
					  ResultWrapper.bad(SystemStatus.SERVER_ERROR.toString());
					  e.printStackTrace();
				  }
			  }
		  }
		  ResultWrapper.setCode("200");
		  ResultWrapper.setMessage("success");
		  ResultWrapper.setResult(list);
		  return ResultWrapper;
     }
}
