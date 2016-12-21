package com.yijiajiao.oss.domain.vo;

import lombok.Data;
import java.util.List;

@Data
public class UserStoreInfoBean {


    private int id;
    //用户名
    private String username;

    //姓名
    private String name;
    //性别
    private String sex;
    //职称(教师)
    private String teacher_grade;
    //邮箱
    private String mail;
    //生日
    private String birthday;
    //年级

    //学校
    private String school;


    //认证时间


    private String authDate;
    //教龄（老师）
    private int teachAge;
    //描述
    private String description;
    //状态  (学生or老师)
    /**
     * 1:学生，2：学生，正在认证教师，3：认证教师
     */
    private int status_st;
    //状态（是否拉黑）
    /**
     * 1:未来黑，2：拉黑
     */

    private int state;
    //电话
    private String phone;
    //头像图片
    private String pic;

    //教师头像
    private String teachPic;
    //积分等级
    private String scoreGrade;
    //注册时间
    private String registDate;
    //最后登录时间
    private String lastDate;
    private List<GradeModel> gradeModel;


    //视频授权的权限  1:有权限 0：无权限
    private int video_permission;
    private int facingTeachPermission = 0;
    //答疑的权限
    private int solutionpermission = 0;
    //private String checkDescription;
    //学段
    private String stageCode;
    private String stageName;


    //身份证号
    private String idCard;
    //身份证正面照片
    private String frontPic;
    //身份证反面照片
    private String backPic;
    //教师资格证书
    private String qualifyPic;
    //其他证书
    private String otherPic;
    //手持身份证照片
    private String idPic;


    private String subjectCode;
    private String subjectName;


    private String userOpenId;


    //private List<UserGrade> grades;


    private String createTime;
    private String storePic;
    private String storeOpenId;
    //店铺评分
    private double storeScore;
    //小星星
    private double score;
    //当前人数
    private Integer count;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String areaCode;
    private String cityName;
    private String areaName;
    //店铺名称
    private String storeName;
    //老师简介
    private String introduction;
    //店铺个性简介
    private String brief;
    //标签
    private String label;
    //自定义标签
    private String customLabel;
    //背景图片
    private String storeBackPic;
    private String parentName;
    private String parentPhone;
    private String address;

    private String invite_code;
    private String invite_selfcode;
    //申请老师的状态  1：填写资料 2：等待审核 3：资料审核未通过 4：资料审核通过去答题 5：考试通过显示我得店铺 6： 考试未通过
    //private int applyStatus;
    //人气
    private Integer popularity;
    //积分
    private int integral;

    private String accid;
    private String imToken;
    private String imName;

    private int status;

    private String videoDescription;
    private String weibo;
    private String weChat;
    private String qq;
    //名师风采描述
    private String famousTeacherDescription;
    //名师风采图片
    private String famousTeacherPic;
    //名师风采视频
    private String famousTeacherVideo;

}
