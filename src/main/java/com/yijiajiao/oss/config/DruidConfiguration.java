package com.yijiajiao.oss.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * druid 配置.
 *
 * @author tbc on 2016/10/30 0:21.
 */
@Slf4j
@Configuration
@ConditionalOnMissingBean(DataSource.class)
public class DruidConfiguration extends WebMvcConfigurerAdapter {

    @Value("${jdbc.druid.loginUsername:root}")
    private String loginUsername;
    @Value("${jdbc.druid.loginPassword:12345}")
    private String loginPassword;
    private static final String DB_PREFIX = "spring.datasource.";


    @Autowired
    private Environment environment;

    @Bean
    @ConfigurationProperties(prefix = DB_PREFIX)
    public DataSource druidDataSource() throws SQLException {

        Properties dbProperties = new Properties();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Iterator<PropertySource<?>> it =
             ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext(); ) {
            PropertySource<?> propertySource = it.next();
            getPropertiesFromSource(propertySource, map);
        }
        dbProperties.putAll(map);

        DruidDataSource dds = null;
        try {
            dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(dbProperties);
            dds.init();
        } catch (Exception e) {
            throw new RuntimeException("load datasource error, dbProperties is :" + dbProperties, e);
        }
        dds.setFilters("wall,stat");
        log.info("DruidDataSource created ...............");
        return dds;
    }

    private void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {
        if (propertySource instanceof MapPropertySource) {
            for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
                if (key.startsWith(DB_PREFIX)) {
                    map.put(key.replaceFirst(DB_PREFIX, ""), propertySource.getProperty(key));
                } else if (key.startsWith(DB_PREFIX)) {
                    map.put(key.replaceFirst(DB_PREFIX, ""), propertySource.getProperty(key));
                }
            }
        }

        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> s : ((CompositePropertySource) propertySource)
                    .getPropertySources()) {
                getPropertiesFromSource(s, map);
            }
        }
    }

    /**
     * 注册一个StatViewServlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean DruidStatViewServlet2() {
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //添加初始化参数：initParams

        //白名单：
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
//        servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", loginUsername);
        servletRegistrationBean.addInitParameter("loginPassword", loginPassword);
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");

        log.info("ServletRegistrationBean 已注册");
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter2() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");

        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

        log.info("FilterRegistrationBean 已注册");

        return filterRegistrationBean;
    }


    /**
     * 添加静态资源swagger-ui.html，否则请求不到页面
     *
     * @param registry .
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/druid/*")
                .addResourceLocations("classpath:/META-INF/resources/");

    }
}
