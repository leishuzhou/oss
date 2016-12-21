package com.yijiajiao.oss.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.yijiajiao.oss.domain.vo.IdsBean;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;


@Slf4j
@SuppressWarnings("ALL")
public class BegUtils {

	private static Logger log = LoggerFactory.getLogger(BegUtils.class);
	private static String teacher_server = Config.getString("teacher_server");
	private static String teacher_login = Config.getString("teacher_login");
	private static String user_server = Config.getString("user_server");
	private static String goods_server = Config.getString("goods_server");
	private static String goods_login = Config.getString("goods_login");
	private static String goods_byIds = Config.getString("goods_byIds");

	/**
	 * 接收参数日志log
	 * @param arg
	 * @param args
	 */

	public static void logGetParameter(String arg , Object ... args) {
		String debugInfo = "\n获取传入参数：{},  所在类名：{}  所在行号：{}\n";
		log.debug(MessageFormat.format(debugInfo , args), Thread.currentThread().getStackTrace()[2].getFileName(),Thread.currentThread().getStackTrace()[2].getLineNumber());
	}

	/**
	 * 获取单个用户信息
	 * @param openId
	 * @return
	 */
	public static String getUserByOPenId(String openId) {
		log.info("\n调用户接口获取参数：openId={}",openId);
		JSONObject resultJson = null;
		String resultJsonStr = "";
		String result="";
		try {
			resultJsonStr = SolutionUtil.httpRest(teacher_server, teacher_login + openId, null, null, "GET");
			log.info("\n请求内部教师路径返回结果：{}\n",resultJsonStr);
			resultJson = JSONObject.parseObject(resultJsonStr);
			String mess=(String) resultJson.get("message").toString();
			if(mess.equals("success")){
				result = resultJson.getString("result");
			}else{
				result=mess;
				log.debug("\n错误信息返回code：{},返回message：{}",resultJson.getString("code"),resultJson.getString("message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  result;
	}


	/**
	 * 通过多个openId查询用户信息
	 * @param ids
	 * @return
	 */
	public static List<Map<String,Object>> getUserByOPenIds(IdsBean ids) {
		log.info("\n调用户接口获取参数：ids={}",ids.getIds());
		List<Map<String,Object>> list=null;
		try {
			String resultJsonStr = SolutionUtil.httpRest(teacher_server, user_server, null, ids, "POST");
			log.info("\n请求教师路径返回结果：{}\n",resultJsonStr);
			Map<?, ?> map = new Gson().fromJson(resultJsonStr, Map.class);
			if(map.get("code").equals("200")){
				list = (List) ((Map) map.get("result")).get("list");
			}else{
				log.info("\n错误信息返回code：{},返回message：{}",map.get("code"),map.get("message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * 获取单个商品信息
	 * @param openId
	 * @return
	 */
	public static String getGoodsById(String openId) {
		ResultWrapper resultBean=new ResultWrapper();
		log.info("调课程接口获取参数：{}",openId);
		JSONObject resultJson = null;
		String resultJsonStr = "";
		String result="";
		try {
			resultJsonStr = SolutionUtil.httpRest(goods_server, goods_login + openId, null, null, "GET");
			log.info("请求课程路径返回结果：{}\n",resultJsonStr);
			resultJson = JSONObject.parseObject(resultJsonStr);
			String mess=(String) resultJson.get("message").toString();
			if(mess.equals("ok")){
				result = resultJson.getString("result");
			}else{
				result=mess;
				resultBean.bad(resultJson.getString("code"), resultJson.getString("message"));
				log.info("错误信息返回code：{},返回message：{}",resultJson.getString("code"),resultJson.getString("message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  result;
	}




	/**
	 * 通过多个id查询商品信息
	 * @param ids
	 * @return
	 */
	public static List<Map> queryWaresByIds(IdsBean ids){
		log.info("\n课程接口获取参数：ids={}",ids.getIds());
		List<Map> list=null;
		try {
			String resultJsonStr = SolutionUtil.httpRest(goods_server, goods_byIds+ids.getIds(), null, null, "GET");
			log.info("\n请求课程路径返回结果：{}\n",resultJsonStr);
			Map<?, ?> map = new Gson().fromJson(resultJsonStr, Map.class);
			if(map.get("code").equals("200")){
				list =  (List) map.get("result");
			}else{
				log.info("\n错误信息返回code：{},返回message：{}",map.get("code"),map.get("message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}



	/*public static void main(String[] args) {
		IdsBean ids = new IdsBean();
		StringBuffer sb = new StringBuffer();ids.setIds("b21b351c-d142-4789-a165-efe918242913");
	   BegUtils.getUserByOPenIds(ids);



	}*/





}
