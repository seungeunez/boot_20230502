package com.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//로그인 후에 이전페이지로 되돌아가기 위해서 현재의 페이지를 저장하는 필터
// 현재페이지중에서 로그인, 로그아웃은 제외시킴
@Component
@RequiredArgsConstructor
@Slf4j
public class UrlFilter extends OncePerRequestFilter {
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            try {

                // ex) 127.0.0.1:9090/ROOT/api/student2/update.json?id=d
                String contextPath = request.getContextPath(); // 현재 contextPath는 => /ROOT 이다
                String path = request.getServletPath(); //  => api/student2/update.json
                String query = request.getQueryString(); // => ?의 뒷 부분을 말함 =>  id=d

                //확인용
                log.info("contextPath => {}", contextPath);
                log.info("path => {}", path);
                log.info("query => {}", query);

                //url에 login, logout이 포함되지 않는 경우
                if(!path.contains("login") && !path.contains("logout")){

                    HttpSession httpSession = request.getSession();

                    if(query == null){
                        httpSession.setAttribute("url", path);
                    }else{
                        httpSession.setAttribute("url", path + "?" + query);
                    }

                }

                filterChain.doFilter(request, response);
                
            } catch (Exception e) {
                e.printStackTrace();


            }
    
    }


    
}
