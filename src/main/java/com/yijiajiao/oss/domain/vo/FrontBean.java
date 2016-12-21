package com.yijiajiao.oss.domain.vo;

import lombok.Data;

@Data
public class FrontBean {
	
	  public String openId;
	  public String name;
	  
	  public int waresType;
	  
	  public int waresId;
	  /**
	   * 商品名称
	   */
	  public String curriculumName;
	  /**
	   * 图片显示的img
	   */
	  public String img;
	  
	  public String isYjj;
	  
	  /**
	   * 教师头像
	   */
	  private String teachPic;
	  
	  /**
	   * 老师上课的阶段
	   */
	  public String stageCode;
	  
	  public String gradeCode;
	  
	  public String gradeName;
	  
	  
	  /**
	   * 点击跳转的路径图
	   */
	  public String url;
	  /**
	   * 价格
	   * @return
	   */
	  public double  price;
	  /**
	   * 人气
	   */
	  public Integer popularity;
	  /**
	   * 开课时长
	   * @return
	   */
	  public String courceTime;
	  
	  /**
	   * 易豆
	   * @return
	   */
	  public String easyPea;
	  
	  /**
	   * 图片介绍
	   * @return
	   */
	  public String didescriptions;
	  /**
	   * 学科
	   * @return
	   */
	  public String subject;
	  
	  /**
	   * 店铺评分
	   * @return
	   */
	  public double storeScore;
	  /**
	   * 教师擅长
	   * @return
	   */
	  public String goodRange;
	  
	  /**
	   * 教师答疑方式介绍
	   * @return
	   */
	  public String referalWay;
	  
	  
	  //店铺名称
	  private String storeName;
	  
	  
	  private String liveTime;
	  
	  
	  private String storePic;
	  /**
	   * 店铺介绍
	   */
	  public String brief;
	  

}
