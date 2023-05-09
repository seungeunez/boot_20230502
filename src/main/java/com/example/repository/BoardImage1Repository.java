package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.BoardImage1;


@Repository
public interface BoardImage1Repository extends JpaRepository<BoardImage1, Long>{



    
}
