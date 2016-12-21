package com.yijiajiao.oss.domain.vo;

import com.yijiajiao.oss.util.DateAdapter;
import lombok.Data;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@Data
public class FeedBackBean {

	public Integer id;
	/**
	 * 姓名
	 */
	public String name;
	/**
	 * 头像
	 */
	public String img;
	/**
	 * 手机号
	 */
	public String phone;

	/**
	 * 反馈时间
	 */
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date createTime;

	/**
	 * 反馈内容
	 */
	private String content;

	/**
	 * 联系方式
	 */
	private String contactWay;

	/**
	 * 状态 0:未查看,1:已查看
	 */
	private Integer status;


}
