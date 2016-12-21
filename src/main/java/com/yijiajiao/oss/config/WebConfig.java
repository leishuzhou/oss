package com.yijiajiao.oss.config;

import com.yijiajiao.oss.OssApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by leishuzhou on 2016/11/16.
 */
public class WebConfig extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OssApplication.class);
    }
}


