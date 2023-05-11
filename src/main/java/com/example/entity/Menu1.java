package com.example.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "MENU1") 
@SequenceGenerator(name = "SEQ_MENU1_NO", sequenceName = "SEQ_MENU1_NO", initialValue = 1000, allocationSize = 1)
@Data
public class Menu1 {

    //메뉴번호, 기본키, 시퀀스
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MENU1_NO")
    private BigInteger no;

    //메뉴 이름
    private String name;

    //가격
    private BigInteger price;

    //이미지 명
    private String imagename;

    //이미지 타입
    private String imagetype;

    //이미지 사이즈
    private BigInteger imagesize;

    //이미지 데이터
    @Lob //blob - byte[] 
    @ToString.Exclude //길어서 출력이 안되는바람에 뺐음
    private byte[] imagedata; 

    //등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE", insertable = true, updatable = false)
    private Date regdate;


    //외래키 //기본키가 두 개 잡혀있어서 외래키도 두 개 잡혀야함
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "rphone", referencedColumnName = "phone"),
        @JoinColumn(name = "rno", referencedColumnName = "no")
    })
    private Restaurant1 restaurant1;



    
}
