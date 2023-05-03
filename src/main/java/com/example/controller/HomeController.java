package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController{

    // 자동 import 단축키 shift + alt + o
    //127.0.0.1:9090/CONTEXTPATH/home.do
    //127.0.0.1:9090/ROOT/home.do --> properties에 가서 ROOT라고 설정 해줬음
    @GetMapping(value = {"/home.do", "/"}) 
    public String homeGET(Model model, @AuthenticationPrincipal User user) {  

        //request.setAttribute("key", "value")

        if(user != null) {//로그인 됐을 때 나옴
            System.out.println(user.toString());
        }

        model.addAttribute("user", user);
        return "home";

    }

/* -------------------------------------- */

    //로그인 화면
    //127.0.0.1:9090/ROOT/.do
    @GetMapping(value="/login.do")
    public String loginGET() {
        return "login"; //login.html
    }


/* -------------------------------------- */

    //403화면
    //127.0.0.1:9090/ROOT/403page.do
    @GetMapping(value = "/403page.do")
    public String PageGET(){

        return "403page";
    }



}