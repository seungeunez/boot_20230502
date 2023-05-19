package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//aop => 관점지향프로그램
//mapper나 service 등등 모든것들의  앞에 필터를 걸어줄 수 있음

@Component
@Aspect
@Slf4j
public class LogAspect {

    //내가 짠 코드의 특정 메소드만 꺼내서 쓸 수 있음
    //내가 썼던 파일의 특정메소드가 뭐냐
    // 뭔소리지 싀벌탱
    //저장소에 있는 모든것들을 반환함

    //interceptor와는 전혀 다르다

    //com.example.controller의 모든Controller의 모든메소드 반응
    //com.example.repository 모든Repository의 모든메소드 반응
    @Around("execution(* com.example.controller.jpa.*Controller.*(..)) or execution(* com.example.repository.*Repository.*(..))" )
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable{

        String clsname = joinPoint.getSignature().getDeclaringTypeName();
        String metname = joinPoint.getSignature().getName();

        String result = "";

        if(clsname.contains("Controller")){

            result = " type : Controller => ";

        }else if(clsname.contains("Service")){

            result = " type : Service => ";

        }else if(clsname.contains("Repository")){

            result = " type : Repository => ";
            
        }else if(clsname.contains("Mapper")){

            result = " type : Mapper => ";
            
        }

        result += clsname + "=>" + metname + "()";

        log.info(result);

        return joinPoint.proceed();

    }


    
}
