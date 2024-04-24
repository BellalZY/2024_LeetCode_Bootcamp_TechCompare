package com.leetcode2024spring.ecommercedemo1.config;

import com.leetcode2024spring.ecommercedemo1.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private TokenInterceptor tokenInterceptor;
//
//
//    @Override
//    // when add path no /techCompare
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor)
//                // 拦截
//                .addPathPatterns("/user/getWishlist")
//                .addPathPatterns("/user/addWishlist")
//                .addPathPatterns("/user/removeFromWishlist")
//                .addPathPatterns("/products/sendreview")
//
//                // 其他所有
//                .excludePathPatterns("/**");
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/techCompare/**")
//                .allowedOrigins("http://localhost:3000") // 允许的前端来源
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
//                .allowedHeaders("*") // 允许的请求头部
//                .exposedHeaders("auth") // 允许暴露的头部信息
//                .allowCredentials(true) // 是否允许发送Cookie
//                .maxAge(3600); // 预检请求的有效期，单位秒
//    }
//
//
//}

