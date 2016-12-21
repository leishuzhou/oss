package com.yijiajiao.oss.controller;

import com.yijiajiao.oss.service.FocusPictureService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;


@RestController
public class FoucusPictureController {

    @Autowired
    FocusPictureService focusPictureService;

    /**
     * 添加数据
     *
     * @param
     * @return
     */
    @PostMapping("/focus/addfocus")
    public ResultWrapper getFoucspicture(@RequestBody Map<String, Object> param) {
        Map<String, Object> result = focusPictureService.saveFocus(param);
        return ResultWrapper.ok(result);
    }

    /**
     * 通过id得到焦点图片的信息
     *
     * @param id
     * @return
     */
    @GetMapping("/focus/getfocuspicture/{id}")
    public ResultWrapper getFocusPictureDisable(@PathVariable("id") Integer id) {
        Map<String, Object> result = focusPictureService.get(id);
        if (result== null)
            return ResultWrapper.bad("参数不匹配");
        return ResultWrapper.ok(result);

    }

    /**
     * 更新操作
     *
     * @param
     * @return
     */
    @PostMapping("/focus/exitfocus")
    public ResultWrapper FocusPicture(@RequestBody  Map<String, Object> param) {
        Map<String, Object> result = focusPictureService.updateFocus(param);
        return ResultWrapper.ok(result);
    }


    /**
     * 根据标识得到焦点图的位置
     *
     * @return
     */
    @GetMapping("/focus/getfocusarea/{belongs}/{area}")
    public ResultWrapper getfocusarea(
            @PathVariable("belongs") String belongs,
            @PathVariable("area") String area) {
        String a = "";
        String b = "";
        try {
            a = URLDecoder.decode(belongs, "utf-8");
            b = URLDecoder.decode(area, "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        List<Map<String, Object>> result = focusPictureService.getfocusarea(belongs, area);
        return ResultWrapper.ok(result);
    }

    /**
     * 上移下移改变焦点图位置
     *
     * @param
     * @return
     */
    @GetMapping("/focus/changepotaion/{id1}/{sort2}/{id2}/{sort1}")
    public ResultWrapper changepotaion(
            @PathVariable("id1") int id1,
            @PathVariable("sort2") int sort2,
            @PathVariable("id2") int id2,
            @PathVariable("sort1") int sort1) {
        int result = focusPictureService.changeOfPostion(id1, sort2, id2, sort1);
        return ResultWrapper.ok("操作成功");
    }


    /**
     * 发布焦点图
     *
     * @return
     */
    @GetMapping("/focus/releasefocus/{belongs}/{area}")
    public ResultWrapper releaseFocus(
            @PathVariable("belongs") String belongs,
            @PathVariable("area") String area) {
        List<Map<String, Object>> result = focusPictureService.releasefocus(belongs, area);
        return ResultWrapper.ok("操作成功");
    }

}
