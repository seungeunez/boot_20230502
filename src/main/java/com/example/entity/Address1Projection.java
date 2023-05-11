package com.example.entity;

public interface Address1Projection {


    // 주소번호
    long getNo();

    // 주소
    String getAddress();

    // 회원정보 (외래키를 위한 회원 정보 불러오기)
    Member1 getMember1();

    // 외래키 항목 (외래키는 이렇게 가져올 것)
    interface Member1 {

        // 회원 아이디
        String getId();

        // 회원 이름
        String getName();

    }

    //조합 (주소번호와 주소 정보를 합쳤음 내마음대로 조합 가능함) 
    default String getNoAddress(){

        return getNo() + ", " + getAddress();
    
    }


    
}
