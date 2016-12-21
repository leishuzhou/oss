package com.yijiajiao.oss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tbc on 2016/10/19 15:39:05.
 * ServletComponentScan
 */
@EnableTransactionManagement
@SpringBootApplication
@ServletComponentScan("com.yijiajiao.oss.filter")
@MapperScan(value = "com.yijiajiao.oss.mapper")
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
