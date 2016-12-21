package com.yijiajiao.oss.mapper;

import com.yijiajiao.oss.domain.vo.GoodsInfoBean;

import java.util.List;
import java.util.Map;

public interface GoodsInfoMapper {

    GoodsInfoBean getByGoodsId(int id);

    int changeOfPostion(Map<String,Object> param);

    List<Map<String, Object>> getGoodsByBelongsAndArea(Map<String,Object> param);

    int updateGoods(GoodsInfoBean bean);

    Map<String, Object> save(Map<String,Object> param);

    List<Map<String, Object>> findAll();

    GoodsInfoBean updateGoods(Map<String,Object> param);

}
