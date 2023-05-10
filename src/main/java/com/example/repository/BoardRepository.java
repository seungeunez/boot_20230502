package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Board;



public interface BoardRepository extends JpaRepository<Board, BigDecimal> {

    //Select * from board order by no desc
    List<Board> findAllByOrderByNoDesc();

    
}
