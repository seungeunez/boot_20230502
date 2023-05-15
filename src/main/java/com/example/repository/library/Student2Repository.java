package com.example.repository.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.library.Student2;
import com.example.entity.library.Student2Projection;



@Repository
public interface Student2Repository extends JpaRepository<Student2, String> {

    //이메일 존재 여부 확인 // 있으면 =>1 없으면 =>0
    //select count(*) from student2 where email=?
    long countByEmail(String email);

    //1명 정보 확인
    // select name, phone from student2 where email=?;
    Student2Projection findByEmail(String email);


}
