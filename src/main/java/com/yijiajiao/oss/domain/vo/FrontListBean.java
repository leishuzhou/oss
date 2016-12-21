package com.yijiajiao.oss.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class FrontListBean {
	  //广告位图片
	  public String ads;
	  //图片路径
	  public String url;
	  //学段code
	  public String stageCode;
	  //班型（1、一对一直播课，2、小班直播3、大班直播 4、视频课  全部为空）
	  public String waresType;
	  private List<FrontBean> list;

	 
	
}
