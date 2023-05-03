package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    final HttpSession httpSession; 
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

    @PostMapping(value="/home.do")
    public String homePOST( @RequestParam(name="menu", required = false) int menu, HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user, 	
                        @RequestParam Member member, Model model ) {


        log.info(format, menu);

        if(menu == 1){

            member.setId(user.getUsername());
        
            memberMapper.updateMemberOne(member);

            log.info("CustomerControllerUpdate => {}", member.toString());

            return "redirect:/customer/home.do?menu=1";

        }else if(menu == 2){
            
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

            //아이디 정보를 이용해서 DB에서 1명 조회
            Member obj = memberMapper.selectMemberOne1(user.getUsername());

            //조회된 정보의 아이디와 사용자가 입력한 아이디를 matches로 비교
            //비밀번호 확인 => matches(바꾸기전 비번, 해시된 비번)
            if(bcpe.matches(member.getPassword(), obj.getPassword())){

                member.setId(user.getUsername());
                member.setNewpassword(bcpe.encode(member.getNewpassword()));
                memberMapper.updatePW(member);

                log.info("CustomerController updatPW =>  {}", member.toString());

            } return "redirect:/customer/home.do?menu=2";

        }else if(menu == 3){

            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

            //아이디 정보를 이용해서 DB에서 1명 조회
            Member obj = memberMapper.selectMemberOne1(user.getUsername());

            
            //조회된 정보와 현재 암호가 일치하는지 matches로 비교
            if(bcpe.matches(member.getPassword(), obj.getPassword())){
            //비교가 true 이면 DB에서 삭제 후 로그아웃
                memberMapper.deleteMemberOne(member);
                
                //컨트롤러에서 logout처리하기
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                
                log.info("CustomerController => {}", auth.toString());

                if(auth != null){
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                    
                }

                return "redirect:/customer/home.do?menu=3";
            }else{
                return "redirect:/customer/home.do?menu=3";
            }
            
        }
        return "redirect:/customer/home.do";
    
    }
}
