package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController{

    // 자동 import 단축키 shift + alt + o
    //127.0.0.1:9090/CONTEXTPATH/home.do
    //127.0.0.1:9090/ROOT/home.do --> properties에 가서 ROOT라고 설정 해줬음
    @GetMapping(value = {"/home.do", "/"}) 
    public String homeGET(Model model) {  

        //request.setAttribute("key", "value")
        model.addAttribute("title", "전송된 타이틀");
        model.addAttribute("abc", "잠온다");

        return "home";

    }

    //127.0.0.1:9090/ROOT/main.do
    @GetMapping(value = "/main.do")
    public String mainGET(){

        return "main";
    }



}