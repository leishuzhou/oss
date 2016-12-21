package com.yijiajiao.oss.mapper;

import com.yijiajiao.oss.domain.vo.GoodsBean;
import java.util.List;
import java.util.Map;

public interface GoodsInfoFrontMapper {

    int releaseFrontGoods(Map<String,Object> param);

    List<Map<String,Object>> showgoods(Map<String,Object> param);

    List<Map<String, Object>> getid(Map<String,Object> param);

    List<GoodsBean> getShowGoods();

    List<Map<String, Object>> getBelongs(String belongs);

    Map<String, Object> save(Map<String, Object> param);

    int  deleteFrontGoods(Map<String,Object> param);


}
