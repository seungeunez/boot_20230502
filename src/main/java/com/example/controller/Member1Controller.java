package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Member1;
import com.example.repository.Member1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/member1")
@RequiredArgsConstructor
public class Member1Controller {

    final String format = "Member1Controller => {}";
    final Member1Repository m1Repository; //저장소 객체 ////원래는 Service 써야하는데 생략했음

/* ----------------------------------------------------------------- */

    //회원가입
    //127.0.0.1:9090/ROOT/member1/join.do
    @GetMapping(value = "/join.do")
    public String joinGET(){
        return "/member1/join"; //templates / member1 / join.html 이동 (화면 나옴)
    }
    
    @PostMapping(value="/join.do")
    public String joinPOST(@ModelAttribute Member1 member1) {
        
        log.info(format, member1.toString()); //꼭 찍어보자

        //저장소 호출해서 DB에 넣기해야함
        m1Repository.save(member1);
        
        return "redirect:/member1/join.do";
    }

    

/* ----------------------------------------------------------------- */
    
}
