package com.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.handler.CustomerLoginSuccessHandler;
import com.example.handler.CustomerLogoutSuccessHandler;
import com.example.service.SecurityServiceImpl;
import com.example.service.SecurityServiceImpl1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration //환경설정파일. 서버가 구동되기전에 호출됨
@EnableWebSecurity //시큐리티를 사용
@Slf4j
@RequiredArgsConstructor

//여기는 환경설정 파일임
public class SecurityConfig {


    final SecurityServiceImpl  memberTableService; // member테이블과 연동되는 서비스
    final SecurityServiceImpl1 student2TableService; //student2테이블과 연동되는 서비스
    // 관리자 테이블과 연동하는 서비스 ...
    
    
    @Bean   // 객체를 생성함. (자동으로 호출됨.)
    @Order(value = 1) // 순서를 먼저 설정 //가장먼저 처리
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain2");

        // 127.0.0.1:9090/ROOT/student2/login.do
        // 127.0.0.1:9090/ROOT/student2/loginaction.do
        // 127.0.0.1:9090/ROOT/student2/logout.do
        // 위의 두개의 주소만 필터함.
        http.antMatcher("/student2/login.do")
            .antMatcher("/student2/loginaction.do") 
            .authorizeRequests()
            .anyRequest().authenticated().and();

        // 로그인 처리
        http.formLogin()
            .loginPage("/student2/login.do")
            .loginProcessingUrl("/student2/loginaction.do")
            .usernameParameter("id") //login.html의 email name값
            .passwordParameter("password") // login.html의 password name값
            .defaultSuccessUrl("/student2/home.do")
            .permitAll();

        //로그아웃 처리는 한번만 하면 됨 밑에서 굳이 여기서 다 안해도 됨


        http.userDetailsService(student2TableService);
        return http.build();
    }



    @Bean // 객체를 생성함.
    @Order(value= 2) //마지막 숫자로 변경
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        log.info("SecurityConfig => {}", "start filter chain1");
        //로그인, 로그아웃, 권한설정 여기서 설정해야함

        // 권한 설정
        http.authorizeRequests()
            .antMatchers("/customer/join.do").permitAll() //고객 회원가입 페이지는 접근 가능하게 
            .antMatchers("/seller/join.do").permitAll() //판매자 회원가입 페이지는 접근 가능하게 
            .antMatchers("/admin/join.do").permitAll() //관리자 회원가입 페이지는 접근 가능하게

            .antMatchers("/admin","/admin/*").hasAuthority("ROLE_admin") //주소가 9090/ROOT/admin ~~모든 것 //어드민은 어드민만 가능
            .antMatchers("/seller","/seller/*").hasAnyAuthority("ROLE_admin", "ROLE_seller") //어드민은 판매자 페이지로 들어갈 수 있음 // 고객은 판매자 페이지로 접근 불가능
            .antMatchers("/customer","/customer/*").hasAnyAuthority("ROLE_customer") //이것만 두면 회원가입 불가능해서 회원가입 가능하게 맨 앞에 코드 설정했음 //나는 customer seller 소문자라서 소문자로 해야함

            .anyRequest().permitAll();

        //403페이지 설정 (접근권한 불가 시 표시할 화면)
        http.exceptionHandling().accessDeniedPage("/403page.do");
        
        //공통적으로 사용하는 건 home을 기준으로 뒀음 (로그인, 로그아웃) 

        //로그인 처리
        http.formLogin()
            .loginPage("/login.do") //()안에는 로그인 get의 주소를 쓰면 됨 //화면 url 물어보는거
            .loginProcessingUrl("/loginaction.do") // action이 뭔지 물어보는 거임 //login.html에 있는 action값이 뭔지 물어보는거임
            .usernameParameter("id") //내가 쓰는 아이디 name 값이 뭔지 물어보는 것
            .passwordParameter("password") //내가 쓰는 암호 name값이 뭔지 물어보는 것
            .successHandler(new CustomerLoginSuccessHandler()) //로긘 성공했을 때 직책에 맞게..? 홈으로 이동
            //.defaultSuccessUrl("/home.do") //로긘 성공했을 때 // 로그인하고 나서 어디로 갈건지 물어보는거임 // 로긘하고 홈으로 갈거임~
            .permitAll(); 


        //로그아웃 처리 (GET은 안됨 반드시 POST로 호출해야함)
        http.logout()
            .logoutUrl("/logout.do")
            //.logoutSuccessUrl("/home.do")
            .logoutSuccessHandler(new CustomerLogoutSuccessHandler()) //로그아웃 성공했을 때
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll();

        //post는 csrf를 전송해야하지만, 주소가 /api로 시작되는 것들은 csrf가 없어도 허용하도록 설정했음 근데 나머지는 안됨
        http.csrf().ignoringAntMatchers("/api/**");

        //이렇게 하고 시큐리티 ServiceImpl 만들어야함 그러고 서비스 등록해줘야함
        //서비스 등록
       // http.userDetailsService(userDetailsService); 

        return http.build();

    }

    //정적 자원에 스프링 시큐리티 필터 규칙을 적용하지 않도록 설정, resource/static은 시큐리티 적용받지 않음.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    //회원가입에서 사용했던 암호화 알고리즘 설정, 로그인에서도 같은 것을 사용해야하니깐
    @Bean //서버구동시 자동으로 실행됨 => @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
