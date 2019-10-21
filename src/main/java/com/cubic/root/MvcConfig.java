package com.cubic.root;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("load --- html");
        registry.addResourceHandler("/**").addResourceLocations("file:D:\\11\\page\\templates\\");
//        registry.addResourceHandler("/static/**").addResourceLocations("file:D:\\11\\page\\templates\\static\\");

    }
}
