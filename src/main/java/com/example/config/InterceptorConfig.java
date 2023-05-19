package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.interceptor.HttpInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration //서버 구동시 자동으로 호출됨.
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer{
    
    final HttpInterceptor httpInterceptor; //인터셉터 객체 생성
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor)
                .addPathPatterns("/**") //전체 페이지 인터셉터 동작 //전체를 다 넣고 필요없는 일부를 빼는형식임(그래서 로그인 부분을 제외했음 아마 회원가입도 제외해야할듯)
                .excludePathPatterns("/login**", "/login/**", "/css/*"); //login부분은 제외, css도 제외
                
    }

    
    
}
