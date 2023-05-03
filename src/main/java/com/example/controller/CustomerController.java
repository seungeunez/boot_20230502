package com.example.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Controller
@RequestMapping(value = "/customer")
@RequiredArgsConstructor //fianl 사용시 필요한듯
@Slf4j
public class CustomerController {

    final String format = "CustomerController => {}";
    final MemberMapper memberMapper;

/*-----------------------------------------------------------------*/

    //홈 화면 
    @GetMapping(value = "/home.do")
    public String homeGET(){
        return "/customer/home";
    }

/*-----------------------------------------------------------------*/

    //회원가입 화면
    @GetMapping(value = "/join.do")
    public String joinGET(){
        return "/customer/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member member ){
        log.info(format, member.toString()); //화면에 정확하게 표시되고 사용자가 입력한 항목을 member 객체에 저장했음

        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder(); //salt값을 자동으로 부여함 (이게 뭐누..)
        
        member.setPassword(bcpe.encode(member.getPassword())); //기존암호를 암호화 시켜서 다시 저장함.

        int ret = memberMapper.insertMemberOne(member);

        if(ret == 1){
            return "redirect:home.do";//주소창에 127.0.0.1:9090/ROOT/customer/joinok.do입력 후 엔터키를 자동화시키는 것
        } 
        return "redirect:join.do";  //실패시 그자리 그대로
    }

/*-----------------------------------------------------------------*/

    //회원가입 완료 화면
    @GetMapping(value = "/joinok.do")
    public String joinokGET(){
        return "/customer/joinok";
    }

/*-----------------------------------------------------------------*/


    
    
    
    
}
