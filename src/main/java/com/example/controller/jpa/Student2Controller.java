package com.example.controller.jpa;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.library.Student2;
import com.example.repository.library.Student2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Controller
@RequestMapping(value = "/student2")
@RequiredArgsConstructor
@Slf4j
public class Student2Controller {


    final Student2Repository s2Repository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();


    //홈
    @GetMapping(value="/home.do")
    public String homeGET(@AuthenticationPrincipal User user, Model model) { //@AuthenticationPrincipal User user 세션에서 거내기
        try {
            model.addAttribute("user", user);
            return "/student2/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirecct:/home.do"; 
        }
    }
    

/* ------------------------------------------------------------------------ */

    //회원가입
    //127.0.0.1:9090/ROOT/student2/insert.do
    @GetMapping(value="/insert.do")
    public String insertGET() {
        try {
            return "/student2/insert";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirecct:/home.do"; 
        }
    }

    @PostMapping(value="/insert.do")
    public String insertPOST(@ModelAttribute Student2 student2) {
        try {

            //암호는 bcpe를 이용하여 암호화하기
            student2.setPassword(bcpe.encode(student2.getPassword())); //이렇게만 넣으면 바로 암호화 됨

            log.info("{}", student2.toString());

            s2Repository.save(student2);

            return "redirect:/student2/login.do";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirecct:/home.do"; 
        }
    }

/* ------------------------------------------------------------------------ */

    //로그인
    @GetMapping(value="/login.do")
    public String loginGET(){
        try {

            return "/student2/login";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }



    

/* ------------------------------------------------------------------------ */

    
}
