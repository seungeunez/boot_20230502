package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Member1;


//여긴 뭐하는 곳임

@Repository 
public interface Member1Repository extends JpaRepository<Member1, String>{ //JpaRepository<엔티티, 엔티티의 기본키 타입>
    

    // SPQL => select * from member1 order by name desc;
    // 전체 조회
    public List<Member1> findAllByOrderByNameDesc(); //이게 mapper와 똑같음 오류나면 안돌아감 //findAll이라 ()안에 오는게 없음, 다 보는거라서

    // SPQL => select * from member1 where name like '%?%' order by name desc;
    public List<Member1> findByNameContainingOrderByNameDesc(String name);

    //페이지 네이션
    // SPQL => select * from member1 where name like '%?%' order by name desc limit 페이지네이션;
    public List<Member1> findByNameContainingOrderByNameDesc(String name, Pageable pageable);

}
