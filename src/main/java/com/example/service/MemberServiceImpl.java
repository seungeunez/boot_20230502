package com.example.service;

import org.springframework.stereotype.Service;

import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    final MemberMapper memberMapper;

    //회원정보 수정
    @Override
    public int updateMemberOne(Member member) {
        try {
            return memberMapper.updateMemberOne(member);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //탈퇴
    @Override
    public int deleteMemberOne(Member member) {
        try {
            return memberMapper.deleteMemberOne(member);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //비밀번호 변경
    @Override
    public int updatePW(Member member) {
        try {
            return memberMapper.updatePW(member);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
