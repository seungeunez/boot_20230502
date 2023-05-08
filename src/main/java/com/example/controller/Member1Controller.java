package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        m1Repository.save(member1); //sql문을 안쓰면 DB종류 상관없이 넘어가니깐 이런점에선 좋음
        
        return "redirect:/member1/join.do";
    }


/* ----------------------------------------------------------------- */

    //회원전체조회
    //ModelAndView란  model.Attribute + return html 혼합된거라고 생각하면 됨
    @GetMapping(value = "selectlist.do")
    public ModelAndView selectListGET(){ // ModelAndView를 쓰면 (Model model) 이렇게 하던거 안한다고 생각하면됨 
        List<Member1> list = m1Repository.findAll();
        return new ModelAndView("/member1/selectlist", "list", list); //(return 값, model.attribute 했던 값들 두 개)

    }

    



/* ----------------------------------------------------------------- */
    
}
