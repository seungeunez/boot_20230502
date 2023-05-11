package com.example.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Menu1;


@Repository
public interface Menu1Repository extends JpaRepository<Menu1, BigInteger> {

    //외래키로 찾을 때
    //findBy변수명_하위변수명 <= Menu1.java의 Restaurant1 restaurant1을 가져온 것
    List<Menu1> findByRestaurant1_phone(String phone);

    
}
