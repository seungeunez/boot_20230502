package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "BOARD1")
@SequenceGenerator(name = "SEQ_B1", sequenceName = "SEQ_BOARD1_NO", initialValue = 1, allocationSize = 1) //시퀀스 //초기값:1 할당값:1
public class Board1 {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_B1") //시퀀스를 사용한다는 뜻
    private long no; //글번호

    private String title; //제목

    private String writer; //작성자

    @Lob //타입이 clob이란 뜻
    private String content; //내용

    @Column(columnDefinition = "long default 1")//디폴트값으로 1 넣음
    private long hit=1; //조회수

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate; //작성일자

    
    //답글
    @ToString.Exclude //답글쪽에서 오류가 안나게끔 한쪽은 toString 막아야함
    @OneToMany(mappedBy = "board1", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 1 : n  //여기는 연관관계만 가지는거임 Reply1에서 생성하는거임 외래키~
    @OrderBy(value = "no desc")
    List<Reply1> list = new ArrayList<>();


    //게시글 이미지
    @ToString.Exclude
    @OneToMany(mappedBy = "board1", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 1 : n  //여기는 연관관계만 가지는거임 Reply1에서 생성하는거임 외래키~
    @OrderBy(value = "no desc")
    List<BoardImage1> list1 = new ArrayList<>();

    @Transient //임시변수 == 컬럼이 생성되지 않는다. mybatis의 dto개념임
    private String imageUrl; //임시변수에 url정보를 넣을 예정
    
}
