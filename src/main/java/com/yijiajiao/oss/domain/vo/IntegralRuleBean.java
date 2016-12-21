package com.yijiajiao.oss.domain.vo;
import lombok.Data;

import java.util.Date;

@Data
public class IntegralRuleBean {


    private Integer   id;

    private Integer ruleType;

    private Integer ruleGroup;

    private Integer value;

    private Integer isDel;

    private Integer ruleLimit;

    private String remark;

    private Date createTime;


    private Date updateTime;



}




