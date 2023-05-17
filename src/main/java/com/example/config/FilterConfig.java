package com.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.filter.JwtFilter2;
import com.example.filter.UrlFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FilterConfig {

    @Bean //서버가 구동될 때 자동으로 호출됨
    //JwtFilter2필터가 적용되는 url을 설정
    public FilterRegistrationBean<JwtFilter2> filterRegistrationBean(JwtFilter2 jwtFilter){
        log.info("filter => {}", "filterConfig");

        FilterRegistrationBean<JwtFilter2> filterReg = new FilterRegistrationBean<>();
        filterReg.setFilter(jwtFilter);

        //필터가 지원되는 url 등록
        filterReg.addUrlPatterns("/api/student2/update.json");
        filterReg.addUrlPatterns("/api/student2/delete.json");

        // filterReg.addUrlPatterns("/api/student2/*"); // *는 전체 url
        
        return filterReg;
    }


    @Bean //서버가 구동될 때 자동으로 호출됨 //필터를 만든다음에 url세팅을 해야함 이것만 하는건 ㄴㄴ
    public FilterRegistrationBean<UrlFilter> filterRegistrationBea1(UrlFilter urlFilter){
        log.info("filter => {}", "filterConfig");

        FilterRegistrationBean<UrlFilter> filterReg = new FilterRegistrationBean<>();
        filterReg.setFilter(urlFilter);

        //필터가 지원되는 url 등록
        filterReg.addUrlPatterns("/api/student2/*"); // *는 전체 url
        
        return filterReg;
    }
    
}
