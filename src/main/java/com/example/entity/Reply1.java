package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "REPLY1")
@SequenceGenerator(name = "SEQ_R1", sequenceName = "SEQ_REPLY1_NO", initialValue = 1, allocationSize = 1) //시퀀스 //초기값:1 할당값:1
public class Reply1 {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_R1") //시퀀스를 사용한다는 뜻
    private long no; // 답글 번호(시퀀스)

    @Lob
    private String content; //답글 내용

    private String writer; //답글 작성자

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate; //작성일자


    //답글과 게시글의 관계 n : 1
    @ManyToOne
    @JoinColumn(name = "BRDNO", referencedColumnName = "NO")
    private Board1 board1;

    
}
