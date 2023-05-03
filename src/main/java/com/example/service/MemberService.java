package com.example.service;

import org.springframework.stereotype.Service;

import com.example.dto.Member;

@Service
public interface MemberService {

    // 회원정보 수정
    public int updateMemberOne(Member member);

    // 회원 탈퇴
    public int deleteMemberOne(Member member);

    //비밀번호 변경
    public int updatePW(Member member);
    
}
