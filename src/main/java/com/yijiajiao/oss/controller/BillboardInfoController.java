package com.yijiajiao.oss.controller;

import com.yijiajiao.oss.service.BillboardInfoService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@RestController
public class BillboardInfoController {
    @Autowired
    BillboardInfoService billboardinfoService;


    /**
     * 添加排行榜信息
     *
     * @param param
     * @return
     */
    @PostMapping("/billbord/addbillboard")
    public ResultWrapper addbillboard(@RequestBody Map<String, Object> param) {
        Map<String, Object> result = billboardinfoService.save(param);
        return ResultWrapper.ok(result);
    }

    /**
     * 通过id得到排行榜的基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/billbord/getbillbord/{id}")
    public ResultWrapper getbillbordbyid(
            @PathVariable("id") int id) {
        Map<String, Object> result = billboardinfoService.get(id);
        return ResultWrapper.ok(result);
    }


    /**
     * 根据标识得到商品的位置
     *
     * @return
     */
    @GetMapping("/billbord/getbillboardarea/{area}")
    public ResultWrapper getBillboardarea(
            @PathVariable("area") String area) {
        String a = "";
        try {
            a = URLDecoder.decode(area, "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        List<Map<String, Object>> result = billboardinfoService.getfocusarea(a);
        return ResultWrapper.ok(result);
    }

    /**
     * 上移下移改变排行榜位置
     *
     * @param
     * @return
     */
    @GetMapping("/billbord/changepotation/{id1}/{sort2}/{id2}/{sort1}")
    public ResultWrapper changePotation(
            @PathVariable("id1") int id1,
            @PathVariable("sort1") int sort1,
            @PathVariable("id2") int id2,
            @PathVariable("sort2") int sort2) {
        int result = billboardinfoService.changeOfPostion(id1, sort2, id2, sort1);
        return ResultWrapper.ok("操作成功");
    }

    /**
     * 发布排行榜
     *
     * @return
     */
    @GetMapping("/billbord/releasebillboard/{area}")
    public ResultWrapper releasedBillboard(
            @PathVariable("area") String area) {
        int a= billboardinfoService.releaseBill(area);
        return ResultWrapper.ok("发布成功");
    }


    /**
     * 更新操作
     *
     * @param
     * @return
     */
    @PostMapping("/billbord/editbill")
    public ResultWrapper editbill(@RequestBody  Map<String, Object> param) {
        Map<String, Object> result = billboardinfoService.saveorupdates(param);
        return ResultWrapper.ok("操作成功");
    }
}
