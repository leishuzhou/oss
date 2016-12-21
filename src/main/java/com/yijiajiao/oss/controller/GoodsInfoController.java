package com.yijiajiao.oss.controller;

import com.yijiajiao.oss.domain.vo.GoodsInfoBean;
import com.yijiajiao.oss.service.GoodsInfoService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pub.tbc.mybatis.plugin.Objs;

import java.util.List;
import java.util.Map;

@RestController
public class GoodsInfoController {
    @Autowired
    GoodsInfoService service;

    /**
     * 添加商品课程信息
     *
     * @param
     * @return
     */
    @PostMapping("/goodsinfo/addgoods")
    public ResultWrapper addGoodsinfo(Map<String, Object> param) {
        Map<String, Object> result = service.save(param);
        return ResultWrapper.ok(result);
    }

    /**
     * 通过id得到商品的基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/goodsinfo/getgoods/{id}")
    public ResultWrapper getGoodsbyid(
            @PathVariable("id") int id) {
        GoodsInfoBean result = service.get(id);
        if (Objs.isEmpty(result))
            return ResultWrapper.bad("参数不匹配");
        return ResultWrapper.ok(result);
    }

    /**
     * 更新操作
     *
     * @param
     * @return
     */
   /* @PostMapping("/goodsinfo/exitgoodinfo")
    public ResultWrapper exitGoodsinfo(Map<String, Object> param) {
        Map<String, Object> result = service.saveorupdate(param);
        return ResultWrapper.ok(result);
    }*/


    /**
     * 根据标识得到商品的位置
     *
     * @return
     */
    @GetMapping("/goodsinfo/getgoodsarea/{belongs}/{area}")
    public ResultWrapper getGoodsarea(
            @PathVariable("belongs") String belongs,
            @PathVariable("area") String area) {
        List<Map<String, Object>> result = service.getfocusarea(belongs,area);
        if (Objs.isEmpty(result))
            return ResultWrapper.bad("参数不匹配");
        return ResultWrapper.ok(result);
    }

    /**
     * 上移下移改变焦点图位置
     *
     * @param
     * @return
     */
    @GetMapping("/goodsinfo/changepotation/{id1}/{sort2}/{id2}/{sort1}")
    public ResultWrapper changePotation(
            @PathVariable("id1") int id1,
            @PathVariable("sort2") int sort2,
            @PathVariable("id2") int id2,
            @PathVariable("sort1") int sort1) {
       int result = service.changeOfPostion(id1, sort2, id2, sort1);
        return ResultWrapper.ok("操作成功");
    }

    /**
     * 发布商品
     *
     * @return
     */
    @GetMapping("/goodsinfo/releasedgoods/{belongs}/{area}")
    public ResultWrapper releasedGoods(
            @PathVariable("belongs") String belongs,
            @PathVariable("area") String area) {
        service.releasefocus(belongs, area);
        return ResultWrapper.ok("操作成功");
    }

    /**
     * 更新操作
     *
     * @param
     * @return
     */
    @PostMapping("/goodsinfo/editgoods")
    public ResultWrapper editGoods(@RequestBody  Map<String, Object> param) {
        Map<String, Object> result = service.saveorupdates(param);
        return ResultWrapper.ok("修改成功");
    }

    /**
     * 搜索热词
     * @param searchName
     * @param type
     * @return
     */
    @GetMapping("/goodsinfo/hotSearch")
    public ResultWrapper hotSearch( @RequestParam(required = false) String searchName,
                                    @RequestParam(required = false) int type) {

        ResultWrapper result = new ResultWrapper();
        service.hotSearch(searchName,type);
        result.setMessage("success");
        return result.ok("操作成功");

    }





}
