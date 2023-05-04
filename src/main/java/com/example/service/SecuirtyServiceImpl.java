package com.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecuirtyServiceImpl implements UserDetailsService {
    
    final String format = "SecurityServiceImpl => {}";
    final MemberMapper memberMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //여기 dto 타입은 UserDetails임.. 개발자가 뭘 쓸질 모르니깐
        log.info(format, username);

        //아이디 전달해서 정보를 받아옴 암호까지 받아와야함
        Member member = memberMapper.selectMemberOne1(username);

        if(member != null){ //가져올 정보가 있었다. 존재하는 아이디가 있음

            //Member DTO를 사용해서 처리하나 시큐리티는 User DTO를 사용함 
            //session에는 User타입으로 저장됨. 로그인 성공하면 저장되는거임 그래서 id는 user.getUserName()임
            return User.builder()
                    .username(member.getId())
                    .password(member.getPassword())
                    .roles(member.getRole()) //여러개 들어갈 수 있음
                    .build();

        }

            return User.builder()
                    .username("_")
                    .password("_")
                    .roles("_") 
                    .build();
        

        //DB연동 해야함

    }


    
}
