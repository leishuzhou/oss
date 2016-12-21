package com.yijiajiao.oss.domain.vo;

import lombok.Data;

@Data
public class RecommendBean {
	  public String openId;
	  /**
	   * 图片显示的img
	   */
	  public String storePic;
	  /**
	   * 老师上课的学段
	   */
	  public String stage;
	  /**
	   * 教师名
	   */
	  public String userName;

	  /**
	   * 人气
	   * @return
	   */
	  public Integer popularity;
	
	  /**
	   * 学科
	   * @return
	   */
	  public String subjectName;
	  
	  /**
	   * 学科code
	   */
	  public String subjectCode;
	  /**
	   * 学年名称
	   */
	  public String gradeName;
	  
	  /**
	   * 学年code
	   */
	  public String gradeCode;
	  
	  /**
	   * 店铺介绍
	   */
	  public String brief;
	  
	  /**
	   * 教师学年
	   */
	//  private List<GradeModel> gradeModel;
	  
	  /**
	   * 店铺评分
	   * @return
	   */
	  public double storeScore;
	  
	  /**
	    * 身份认证
	  */
	  public Integer status_st;
	  
	  /**
	   * 在线授课  
	   */
	  public Integer facingTeachPermission;
	  /**
	   * 教龄
	   */
	  public Integer teachAge;
	  


	@Override
	public String toString() {
		return "{openId:" + openId + ", storePic:" + storePic + ", stage:"
				+ stage + ", userName:" + userName + ", popularity:" + popularity
				+ ", subjectName:" + subjectName + ", storeScore:" + storeScore
				+ ", authentication:" + status_st + "}";
	}



	
	
	
	
	 
	
}
