package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Member1;


//여긴 뭐하는 곳임
//mapper처럼 쓰는 곳인가

@Repository 
public interface Member1Repository extends JpaRepository<Member1, String>{ //JpaRepository<엔티티, 엔티티의 기본키 타입>
    

    // 전체 조회
    // SPQL => select * from member1 order by name desc;
    public List<Member1> findAllByOrderByNameDesc(); //이게 mapper와 똑같음 오류나면 안돌아감 //findAll이라 ()안에 오는게 없음, 다 보는거라서

    // 검색
    // SPQL => select * from member1 where name like '%?%' order by name desc;
    public List<Member1> findByNameContainingOrderByNameDesc(String name);

    //개수 가져오기 (총 회원수)
    public long countByNameContaining(String name); 

    // 페이지 네이션 (지금 이게 안되는 중 - db이슈)
    // SPQL => select * from member1 where name like '%?%' order by name desc limit 페이지네이션;
    public List<Member1> findByNameContaining(String name, Pageable pageable);


    
    //mybatis mapper와 같음 #{name} #{start} #{end} == :name :start :end
    //nativequery 사용하기
    //페이지 네이션이 안되는 바람에 native 쓰는거임
    @Query(value="SELECT * FROM ( SELECT m1.*, ROW_NUMBER() OVER (ORDER BY name DESC) rown FROM MEMBER1 m1 WHERE m1.name LIKE '%' || :name || '%' ) WHERE rown BETWEEN :start AND :end", nativeQuery=true)
    public List<Member1> selectByNameContainingPagenation(@Param("name") String name, @Param("start") int start, @Param("end") int end );
    
    


}
