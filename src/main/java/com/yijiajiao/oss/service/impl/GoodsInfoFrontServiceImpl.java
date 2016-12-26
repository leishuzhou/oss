package com.yijiajiao.oss.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.yijiajiao.oss.domain.vo.FrontBean;
import com.yijiajiao.oss.domain.vo.FrontListBean;
import com.yijiajiao.oss.domain.vo.IdsBean;
import com.yijiajiao.oss.domain.vo.OtherResultBean;
import com.yijiajiao.oss.mapper.FocusPictureFrontMapper;
import com.yijiajiao.oss.mapper.GoodsInfoFrontMapper;
import com.yijiajiao.oss.service.GoodsInfoFrontService;
import com.yijiajiao.oss.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.tbc.mybatis.plugin.CastUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class GoodsInfoFrontServiceImpl implements GoodsInfoFrontService {

	private Logger log = LoggerFactory.getLogger(GoodsInfoFrontServiceImpl.class);

	@Autowired
	GoodsInfoFrontMapper mapper;
	@Autowired
	FocusPictureFrontMapper focusMapper;

	// 获取多个教师店铺信息
	private String teacher_server = Config.getString("teacher_server");
	private String user_server = Config.getString("user_server");
	// 多个获取商品课程 信息
	private String goods_server = Config.getString("goods_server");
	private String goods_byIds = Config.getString("goods_byIds");

	/**
	 * 首页展示商品图片的信息
	 */
	@Override
	public List<Map<String,Object>> showgoods(String belongs, String area) {

		return this.mapper.showgoods(ImmutableMap.of("belongs",(Object) belongs,"area",area));
	}

	@Override
	public List<Map<String,Object>> getid(String belongs, String area) {
		return this.mapper.getid(ImmutableMap.of("belongs",(Object) belongs,"area",area));
	}



	@SuppressWarnings("unchecked")
	@Override
	public FrontListBean getGoods(String belongs, String area) {
		ResultWrapper resultbean = new ResultWrapper();
		long start=System.currentTimeMillis();
		OtherResultBean r = null;
		List<Map<String,Object>> goodslist = mapper.showgoods(ImmutableMap.of("belongs",(Object) belongs,"area",area));
		// 获取广告位焦点图
		List<Map<String,Object>> ads = focusMapper.getByBelongs(belongs);
		FrontListBean bean = new FrontListBean();
		List<FrontBean> list = new ArrayList<FrontBean>();
		LinkedList<FrontBean> link=new LinkedList<FrontBean>();
		if (goodslist.size() == 0) {
			resultbean.bad(SystemStatus.UNAUTHORIZED.toString());
		} else {
			if (belongs.equals("7") && area.equals("2")) {
				// 教师工作室
				StringBuffer sb = new StringBuffer();
				IdsBean Ids = new IdsBean();
				for (int i = 0; i < goodslist.size(); i++) {
					sb.append(goodslist.get(i).get("open_id") + ",");
					Ids.setIds(sb.toString().substring(0, sb.toString().length()));
				}
				try {
					// 教师接口
					String resultJsonStr2 = SolutionUtil.httpRest(teacher_server, user_server, null, Ids, "POST");
					r = (OtherResultBean) SolutionUtil.getTransObject(resultJsonStr2, OtherResultBean.class);
					if ((r.getCode() + "").equals("200")) {
						JSONArray array = JSONArray.parseArray(
								JSONObject.parseObject(JSONObject.parseObject(resultJsonStr2).get("result")+"").get("list")+"");
						for (Object object : array) {
							FrontBean front = new FrontBean();
							JSONObject obj = JSONObject.parseObject(object.toString());
							// 店铺图片
							front.setImg(CastUtil.castString(obj.get("pic")));
							// 教师头像
							front.setTeachPic(obj.get("teachPic").toString());
							front.setStoreName(obj.get("storeName").toString());
							front.setName(obj.get("name").toString());
							front.setOpenId(obj.get("userOpenId").toString());
							front.setStageCode(obj.get("stageName").toString());
							front.setSubject(obj.get("subjectName").toString());
							front.setStoreScore(obj.getDouble("storeScore"));
							front.setPopularity((Integer)obj.get("popularity"));
							list.add(front);
						}
						log.info("正确信息：" + r.getResult().toString());

					} else {
						log.info("错误信息： " + r.getMessage());
						resultbean.bad(r.getCode(), r.getMessage());
					}
				} catch (Exception e) {
					resultbean.setResult(SystemStatus.SERVER_ERROR);
					e.printStackTrace();
				}
				if (!area.equals("5")) {
					bean.setWaresType(String.valueOf(list.get(0).getWaresType()));
				}
				if (ads.size() > 0) {
					bean.setAds(ads.get(0).get("front_img").toString());
					bean.setUrl(ads.get(0).get("front_url").toString());
				}
				bean.setStageCode(list.get(0).getStageCode());
				bean.setList(list);

			} else if (belongs.equals("7") && area.equals("4")) {
				// 教师工作室
				StringBuffer sb = new StringBuffer();
				IdsBean Ids = new IdsBean();
				for (int i = 0; i < goodslist.size(); i++) {
					sb.append(goodslist.get(i).get("open_id") + ",");
					Ids.setIds(sb.toString().substring(0, sb.toString().length()));
				}
				try {
					// 教师接口
					String resultJsonStr2 = SolutionUtil.httpRest(teacher_server, user_server, null, Ids, "POST");
					r = (OtherResultBean) SolutionUtil.getTransObject(resultJsonStr2, OtherResultBean.class);
					if ((r.getCode() + "").equals("200")) {
						JSONArray array = JSONArray.parseArray(
								JSONObject.parseObject(JSONObject.parseObject(resultJsonStr2).get("result")+"").get("list")+"");
						for (Object object : array) {
							FrontBean front = new FrontBean();
							JSONObject obj = JSONObject.parseObject(object.toString());
							// 店铺图片
							front.setStorePic(obj.get("teachPic").toString());
							front.setName(obj.get("name").toString());
							front.setOpenId(obj.get("userOpenId").toString());
							String jsonString = obj.get("gradeModel").toString();
							JSONArray jsonArray = JSONArray.parseArray(jsonString);
							for (Object json: jsonArray
								 ) {
								JSONObject jsonObject = JSONObject.parseObject(json.toString());
								front.setGradeName(CastUtil.castString(jsonObject.get("gradeName")));
								front.setGradeCode(CastUtil.castString(jsonObject.get("gradeCode")));

							}
							// Java集合FrontBean
							/*FrontBean list1 =  new Gson().fromJson(new Gson().toJson(jsonArray), FrontBean.class);
									//(List<FrontBean>) jsonArray.toCollection(jsonArray,FrontBean.class);
							front.setGradeName(list1.getGradeName());
							front.setGradeCode(list1.getGradeCode());*/
							front.setSubject(obj.get("subjectName").toString());
							front.setStoreScore(obj.getDouble("storeScore"));
							front.setPopularity(Integer.valueOf(obj.getString("popularity")));
							front.setBrief(obj.getString("brief"));
							front.setIntroduction(CastUtil.castString(obj.getString("introduction")));
							list.add(front);
						}
						log.info("正确信息：" + r.getResult().toString());

					} else {
						log.info("错误信息： " + r.getMessage());
						resultbean.bad(r.getCode(), r.getMessage());
					}
				} catch (Exception e) {
					resultbean.bad(SystemStatus.SERVER_ERROR.toString());
					e.printStackTrace();
				}
				if (!area.equals("5")) {
					bean.setWaresType(String.valueOf(list.get(0).getWaresType()));
				}
				if (ads.size() > 0) {
					bean.setAds(ads.get(0).get("front_img").toString());
					bean.setUrl(ads.get(0).get("front_url").toString());
				}
				// bean.setStageCode(list.get(0).getStageCode());
				bean.setList(list);

			} else {
				try {

					// 调用课程接口
					StringBuffer sb = new StringBuffer();
					IdsBean ids = new IdsBean();
					for (int i = 0; i < goodslist.size(); i++) {
						sb.append(goodslist.get(i).get("open_id") + ",");
						ids.setIds(sb.substring(0, sb.toString().length() - 1));
					}
					String resultJsonStr =  SolutionUtil.httpRest(goods_server, goods_byIds + ids.getIds(), null, null,
							"GET");
					Map<?, ?> map = new Gson().fromJson(resultJsonStr, Map.class);
					List<Map<?,?>> _list = (List) map.get("result");

					for (int i=0;i<goodslist.size();i++){
						for (Map o : _list) {
							FrontBean b = new Gson().fromJson(new Gson().toJson(o), FrontBean.class);
							if ((b.getWaresId()+"").equals(goodslist.get(i).get("open_id"))){
								link.add(b);
							}
						}
					}

					// 教师工作室
					StringBuffer sb2 = new StringBuffer();
					IdsBean ids2 = new IdsBean();
					for (int i = 0; i < link.size(); i++) {
						sb2.append(link.get(i).getOpenId() + ",");
						ids2.setIds(sb2.substring(0, sb2.toString().length()));
					}
					String resultJsonStr3 = SolutionUtil.httpRest(teacher_server, user_server, null, ids2, "POST");
					Map<?, ?> map2 = new Gson().fromJson(resultJsonStr3, Map.class);

					List<Map> list2 = (List) ((Map) map2.get("result")).get("list");
					LinkedList<FrontBean> userInfoList = new LinkedList<FrontBean>();
					for (Map map1 : list2) {
						FrontBean beanlist = new Gson().fromJson(new Gson().toJson(map1), FrontBean.class);
						userInfoList.add(beanlist);
 					}

					for (int i = 0; i < link.size(); i++) {
						link.get(i).setName(userInfoList.get(i).getName());
						link.get(i).setTeachPic(userInfoList.get(i).getTeachPic());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (!area.equals("5")) {
					bean.setWaresType(String.valueOf(link.get(0).getWaresType()));
				}
				if (ads.size() > 0) {
					bean.setAds(ads.get(0).get("front_img").toString());
					bean.setUrl(ads.get(0).get("front_url").toString());
				}
				bean.setStageCode(link.get(0).getStageCode());
				bean.setList(link);
			}
		}
		return bean;
	}



}
