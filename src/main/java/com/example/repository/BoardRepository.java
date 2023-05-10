package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Board;



public interface BoardRepository extends JpaRepository<Board, BigDecimal> {

    //Select * from board order by no desc
    List<Board> findAllByOrderByNoDesc();



    //3. 검색어 타입에 따른 메소드 3개 만들기

    //제목
    public List<Board> findByTitleIgnoreCaseContainingOrderByNoDesc(String title); //IgnoreCase 대소문자 구분하지 않고 다 검색 되게 해줌

    //작성자
    public List<Board> findByWriterIgnoreCaseContainingOrderByNoDesc(String writer);

    //내용
    public List<Board> findByContentIgnoreCaseContainingOrderByNoDesc(String content);


    //총 개수
    public long countByTitleContaining(String title); 

    //페이지네이션

    // public List<Board> findByTitleIgnoreCaseContainingOrderByNoDesc(String title, Pageable pageable); 

    // public List<Board> findByWriterIgnoreCaseContainingOrderByNoDesc(String writer, Pageable pageable);

    // public List<Board> findByContentIgnoreCaseContainingOrderByNoDesc(String content, Pageable pageable);


    @Query(value="SELECT * FROM ( SELECT b1.*, ROW_NUMBER() OVER (ORDER BY no DESC) rown FROM BOARD b1 WHERE b1.title LIKE '%' || :title || '%' ) WHERE rown BETWEEN :start AND :end", nativeQuery=true)
    public List<Board> selectByTitleContainingPagenation(@Param("title") String title, @Param("start") int start, @Param("end") int end );




    
}
