package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Member1;

//여기가 mapper?


@Repository 
public interface Member1Repository extends JpaRepository<Member1, String>{ //JpaRepository<엔티티, 엔티티의 기본키 타입>
    
    

}