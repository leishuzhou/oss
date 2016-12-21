package com.yijiajiao.oss.service;

import com.yijiajiao.oss.domain.vo.FrontListBean;

import java.util.List;
import java.util.Map;

public interface GoodsInfoFrontService {


    List<Map<String,Object>> showgoods(String belongs, String area);

    List<Map<String, Object>> getid(String belongs, String area);

    FrontListBean getGoods(String belongs, String area);


}
