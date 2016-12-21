package com.yijiajiao.oss.controller;
import com.yijiajiao.oss.service.FocusPictureFrontService;
import com.yijiajiao.oss.util.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@RestController
public class FoucusPictureFrontController {
    @Autowired
    FocusPictureFrontService service;

    /**
     * 首页展示焦点图
     *
     * @return
     */
    @GetMapping("/frontfocus/showfocus/{belongs}/{area}")
    public ResultWrapper showFocus(
            @PathVariable("belongs") String belongs,
            @PathVariable("area") String area) {
        String a = "";
        String b = "";
        try {
            a = URLDecoder.decode(belongs, "utf-8");
            b = URLDecoder.decode(area, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> result = service.showfocus(a,b);
        return ResultWrapper.ok(result);
    }
}
