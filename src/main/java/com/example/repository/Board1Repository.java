package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Board1;

@Repository
public interface Board1Repository extends JpaRepository<Board1, Long> { //게시글 기본키는 long임


    //게시글 전체 조회
    public List<Board1> findAllByOrderByNoDesc();


    
}
