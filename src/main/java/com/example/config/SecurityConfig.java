package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Configuration //환경설정파일. 서버가 구동되기전에 호출됨
@EnableWebSecurity //시큐리티를 사용
@Slf4j
//여기는 환경설정 파일임
public class SecurityConfig {

    
    @Bean // 객체를 생성함.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        log.info("SecurityConfig => {}", "start filter chain");

        //로그인, 로그아웃, 권한설정 여기서 설정해야함
        return http.build();
    }
    
}
