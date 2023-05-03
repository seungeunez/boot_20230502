package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
    
    final MemberMapper memberMapper;

    final HttpSession httpSession; //세션객체

    final String format = "AdminController => {}";


/*---------------------------------------------*/

    //홈 화면 
    @GetMapping(value = "/home.do")
    public String homeGET(){
        return "/admin/home";
    }


/*---------------------------------------------*/

    //회원가입 화면
    @GetMapping(value = "/join.do")
    public String joinGET(){
        return "/admin/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member member ){
        log.info(format, member.toString()); //화면에 정확하게 표시되고 사용자가 입력한 항목을 member 객체에 저장했음

        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder(); //salt값을 자동으로 부여함 (이게 뭐누..)
        
        member.setPassword(bcpe.encode(member.getPassword())); //기존암호를 암호화 시켜서 다시 저장함.

        int ret = memberMapper.insertMemberOne(member);

        if(ret == 1){
            return "redirect:home.do";//주소창에 127.0.0.1:9090/ROOT/admin/home.do입력 후 엔터키를 자동화시키는 것
        } 
        return "redirect:join.do";  //실패시 그자리 그대로
    }

/*---------------------------------------------*/

    //로그아웃
    //GET, POST가 같은 동작을 함.
    @RequestMapping(value="/logout.do", method = {RequestMethod.GET, RequestMethod.POST}) //RequestMapping을 이용해서 method로 GET POST 방식을 구별하는 건 번거롭기때문에 @PostMapping @GetMapping 방식을 씀
    public String logoutPOST() {
        httpSession.invalidate(); // 세션의 정보를 다 지움.
        return "redirect:/home.do";
    }


/*---------------------------------------------------------------*/

    //로그인
    @GetMapping(value="/login.do")
    public String loginGET() {
        return "login";
    }
    
    @PostMapping(value="/login.do")
    public String loginPOST(@ModelAttribute Member member) {
        log.info("login.do => {}", member.toString()); // view에서 잘전송되었는지
        Member ret = memberMapper.selectMemberOne(member); //로그인한 사용자의 정보 반환
        if(ret != null) {
            log.info("login.do => {}", ret.toString()); 
            // 세션에 2개의 정보 아이디와 이름, 권한 추가하기 ( 기본시간 30분 )
            // 다른페이지에서 세션의 아이디가 존재하는지 확인후 로그인 여부 판단
            httpSession.setAttribute("USERID", ret.getId());
            httpSession.setAttribute("USERNAME", ret.getName());
            httpSession.setAttribute("ROLE", ret.getRole());
            return "redirect:/home.do";    // 로그인 성공 시
        }
        return "redirect:login.do"; // 로그인 실패 시 //response.sendRedirect("login.do"); 
    }

/*---------------------------------------------------------------*/

}
