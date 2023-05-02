package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Member;

@Mapper
public interface MemberMapper {
    
    // 회원가입
    public int insertMemberOne(Member member);

    //  로그인
    public Member selectMemberOne(Member member);

    // 회원정보 수정
    public int updateMemberOne(Member member);

    // 회원 탈퇴
    public int deleteMemberOne(Member member);

}
