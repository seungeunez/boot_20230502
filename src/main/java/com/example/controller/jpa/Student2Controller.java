package com.example.controller.jpa;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    //로그아웃은 필요없음 security에서 되게 해줬음

/* ------------------------------------------------------------------------ */

    //로그인 다른버전
    //127.0.0.1:9090/ROOT/student/mylogin.do
    @GetMapping(value = "/mylogin.do")
    public String myloginGET(){
        return "/student2/mylogin";
    }

    @PostMapping(value="/myloginaction.do")
    public String myloginActionPOST(@ModelAttribute Student2 student2){
        try {
            
            log.info("myloginAction => {}", student2.toString());

            //DeatailService를 사용하지 않고 세션에 저장하기 //직접 넣는 것 // 카카오에서 하려면 비교할 필요 없이 4.수동으로 세션에 저장 부분만 있으면 된다 아뉜가? if문 안에 있는건 다 필요한가 그랬음

            //카카오에선 자료 읽고, 비교빼 if문 내부만 하면 됨

            //1. 기존자료 읽기 //비교해야해서 기존자료 읽어야함
            Student2 obj = s2Repository.findById(student2.getEmail()).orElse(null); 

            //2. 전달한 아이디와 읽은 데이터 암호 비교 (해시 되기전, 해시 된 것)
            if(bcpe.matches(student2.getPassword(), obj.getPassword())){

                log.info("myloginAction => {}", obj.toString());

                //3. 세션에 저장할 객체 생성하기 (저장할 객체, null, 권한)
                String[] strRole = {"ROLE_STUDENT2"};
                Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);

                //생성자 3개가 들어갔음 마지막은 collection이라서 위에처럼 했음 아니면 안들어간단 말이지
                User user = new User(obj.getEmail(), obj.getPassword(), role); //import org.springframework.security.core.userdetails.User;
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, role);

                //4. 수동으로 세션에 저장 (로그인)
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);

                /* 
                //5. 수동으로 세션에 저장 (로그아웃)
                Authentication authenticationToken1 = SecurityContextHolder.getContext().getAuthentication();
                if(authenticationToken1 != null){
                    new SecurityContextLogoutHandler().logout(request, reponse, authenticationToken1);
                }
                */
            }

            return "redirect:/student2/home.do";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    

    
}
