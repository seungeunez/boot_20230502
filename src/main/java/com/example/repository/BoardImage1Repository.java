package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.BoardImage1;


@Repository
public interface BoardImage1Repository extends JpaRepository<BoardImage1, Long>{

    //조회
    // 변수명 객체 Baord1_no, 객체가 아닌경우는 No, Name, Age 이렇게 적으면 된다
    //findBy변수명OrderBy변수명Asc

    //게시글 번호가 일치하는 것 중에서 이미지번호가 가장 적은것을 반환 (1개만)
    //select * from boardimage1 where board1.no=? order by no asc litmit 1; //Top1이 하나만 갖고 오는건데 sql에서 limit 1로 표현
    BoardImage1 findTop1ByBoard1_noOrderByNoAsc(Long no);


    //게시글 번호가 일치하는 모든 이미지 (오름차순 - 번호가 작은것부터)
    //select * from boardimage1 where board1.no=? order by no asc
    List<BoardImage1> findByBoard1_noOrderByNoAsc(Long no);
    
}
