package com.logicalthining.endeshop.conf;

import com.logicalthining.endeshop.common.intercepters.AdminInterceptor;
import com.logicalthining.endeshop.common.intercepters.OptionsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/8/25 0025 上午 10:16
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private AdminInterceptor adminInterceptor;//后台用户拦截器
    @Autowired
    private OptionsInterceptor optionsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**").
                excludePathPatterns("/admin/admin/login","/admin/user/userQrCode");

        registry.addInterceptor(optionsInterceptor).addPathPatterns("/**");

    }


}
