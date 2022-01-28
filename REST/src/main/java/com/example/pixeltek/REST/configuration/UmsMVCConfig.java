package com.example.pixeltek.REST.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UmsMVCConfig implements WebMvcConfigurer {
    @Autowired
    UmsInterceptor umsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(umsInterceptor).addPathPatterns("/ums/delete/{email}");
    }
}
