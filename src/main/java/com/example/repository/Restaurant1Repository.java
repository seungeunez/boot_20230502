package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Restaurant1;
import com.example.entity.Restaurant1ID;


@Repository
public interface Restaurant1Repository extends JpaRepository<Restaurant1, Restaurant1ID> { //복합키가 들어가있어서 String이 아니라 Restaurant1ID

    //전체 조회
    public List<Restaurant1> findAllByOrderByNoDesc();


    /* --------------------------------- 검색 기능 -------------------------------------------- */

    //전화
    public List<Restaurant1> findByPhoneContainingOrderByNoDesc(String phone); 
    //가게명
    public List<Restaurant1> findByNameContainingOrderByNoDesc(String name); 
    //종류
    public List<Restaurant1> findByTypeContainingOrderByNoDesc(String type); 
    //주소
    public List<Restaurant1> findByAddressContainingOrderByNoDesc(String address); 

    /* ------------------------------------------------------------------------------------------ */

    /* 검색 + 페이지네이션 */

    //select * from restaurant1 where phone like '%' || ? || '%' order by no desc;
    
    //전화
    public List<Restaurant1> findByPhoneContainingOrderByNoDesc(String phone, Pageable pageable); 
    //상호명
    public List<Restaurant1> findByNameContainingOrderByNoDesc(String name, Pageable pageable); 
    //종류
    public List<Restaurant1> findByTypeOrderByNoDesc(String type, Pageable pageable); 
    //주소
    public List<Restaurant1> findByAddressContainingOrderByNoDesc(String address, Pageable pageable); 


/* ------------------------------------------------------------------------------------------ */

    /* 개수 */

    // select count(*) from restaurant1 where phone like '%' || ? || '%';

    //전화
    public long countByPhoneContaining(String phone); 
    //상호
    public long countByNameContaining(String name); 
    //종류
    public long countByType(String type); 
    //주소
    public long countByAddressContaining(String address); 


/* ------------------------------------------------------------------------------------------ */

    
}
