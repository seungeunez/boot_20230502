package com.example.repository.library;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.library.Library2;

public interface Library2Repository extends JpaRepository<Library2, BigInteger> {


    // select * from 테이블명 where 컬럼=?
    //                        findBy + 컬럼

    //전체조회
    //select * from library2 order by name asc;
    public List<Library2> findAllByOrderByNameAsc();

    //연락처별 내림차순 정렬
    //select * from library2 order by phone desc;
    public List<Library2> findAllByOrderByPhoneDesc();

    
}
