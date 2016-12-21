package com.yijiajiao.oss.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 继承adapter，在仍然使用自动配置的情况下，添加自定义配置
 *
 * @author tbc on 2016/8/22 18:23:16.
 */
@Configuration
@ComponentScan({"pub.tbc.mybatis.plugin", "com.yijiajiao.oss"})
@MapperScan("com.yijiajiao.oss.mapper") // xxxMapper.xml文件路径（跟mapper接口同一目录）
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ObjectMapper om = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
                // null序列化为空串""
                om.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                    @Override
                    public void serialize(Object paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider) throws IOException, JsonProcessingException, IOException {
                        paramJsonGenerator.writeString("");
                    }
                });
                // 设置日期返回格式
                om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            }
        }
    }


}
