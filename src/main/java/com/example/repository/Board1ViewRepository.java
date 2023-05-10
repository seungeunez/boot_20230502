package com.example.repository;


import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Board1View;

@Repository
public interface Board1ViewRepository extends JpaRepository<Board1View, Long> {


    //전체조회
    List<Board1View> findAllByOrderByNoDesc();

    //글 번호와 제목이 정확하게 일치하는 것만 조회
    List<Board1View> findByNoAndTitleOrderByNoDesc(Long no, String title);


    //글 번호 제목 둘 중 하나이상 일치하는 것만 조회
    List<Board1View> findByNoOrTitleOrderByNoDesc(Long no, String title);


    //해당하는 번호만 조회
    List<Board1View> findByNoInOrderByNoDesc(Collection<Long> no);

    //해당 하는 제목만 조회
    List<Board1View> findByTitleInOrderByNoDesc(Collection<String> title);


    
}
