package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "ADDRESS1")
@SequenceGenerator(name = "SEQ_ADDRESS1_NO", sequenceName = "SEQ_ADDRESS1_NO", initialValue = 1, allocationSize = 1) 
public class Address1 {

    //주소번호, 기본키, 시퀀스
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADDRESS1_NO") //generator = "시퀀스명"은 여기서 쓰는 시퀀스 명임 //sequenceName="시퀀스명"이 DB에 등록되는 시퀀스 명임
    @Column(name = "NO") //컬럼명 지정안해주면 필드명이 알아서 컬럼명이 됨
    private long no;

    //우편번호
    @Column(name = "POSTCODE", length = 10)
    private String postcode; 

    //주소 (생략시 컬럼명 변수명과 같고 길이는 255)
    private String address; 


    //등록일자
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp //변경시에도 날짜 정보 변경됨
    private Date regdate;

    //외래키 생성 - 회원 아이디 (생성되는 컬럼은 MEMID 레퍼런스컬럼은 MEMBER1테이블의 ID임)
    @ManyToOne(fetch = FetchType.LAZY) // Address1 테이블 입장에선 Member1테이블과 n : 1 관계임
    @JoinColumn(name = "MEMID", referencedColumnName = "ID")
    private Member1 member1;

    
}
