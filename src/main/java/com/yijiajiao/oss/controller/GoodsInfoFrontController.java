package com.yijiajiao.oss.controller;

import com.yijiajiao.oss.domain.vo.FrontListBean;
import com.yijiajiao.oss.domain.vo.GoodsBean;
import com.yijiajiao.oss.mapper.GoodsInfoFrontMapper;
import com.yijiajiao.oss.service.GoodsInfoFrontService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodsInfoFrontController {

    @Autowired
    GoodsInfoFrontService goodsinfofrontService;
    @Autowired
    GoodsInfoFrontMapper mapper;

    /**
     * 首页展示商品图信息
     *
     * @param belongs
     * @param area
     * @return
     */

    @GetMapping("/frontgoods/getGoods/{belongs}/{area}")
    public ResultWrapper getGoods(
            @PathVariable("belongs") String belongs,
            @PathVariable("area") String area) {
        FrontListBean result = goodsinfofrontService.getGoods(belongs, area);
        return ResultWrapper.ok(result);

    }


    @GetMapping("/frontgoods/show")
    public List<GoodsBean> getShowGoods() {
        ResultWrapper ResultWrapper = new ResultWrapper();
        List<GoodsBean> goodsList = mapper.getShowGoods();
        for (GoodsBean b : goodsList) {
            FrontListBean list = goodsinfofrontService.getGoods(b.getFrontBelongs(), b.getFrontArea());
        }
        return (List<GoodsBean>) ResultWrapper.ok(goodsList);


    }

}
