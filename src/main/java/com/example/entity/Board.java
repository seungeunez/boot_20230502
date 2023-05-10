package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@SequenceGenerator(name = "SEQ_BOARD_NO", sequenceName = "SEQ_BOARD_NO", initialValue = 1, allocationSize = 1)
public class Board {
    

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD_NO") //시퀀스를 사용한다는 뜻
    @Column(name = "NO")
    private BigDecimal no; //글번호

    //이미 만들어진 테이블을 entity 연결 했음
    //근데 private long no; 하니깐
    //[no] in table [board]; found [decfloat (Types#NUMERIC)], but expecting [bigint (Types#BIGINT)] 이런 오류가 났음
    //그럴 땐 타입을 BigDecimal이라고 바꿔줘야함. DB에서 보면 숫자 타입 DECFLOAT로 써져있는데, 이거는 BigDecimal이라고 바꿔줘야 하는 것 같음

    private String title; //제목

    private String writer; //작성자

    @Lob //타입이 clob이란 뜻
    private String content; //내용

    private BigDecimal hit; //조회수

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate; //작성일자

}
