package com.example.entity.library;

import java.math.BigInteger;
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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "CHECKOUT2") 
@SequenceGenerator(name = "SEQ_CHECKOUT2_NO", sequenceName = "SEQ_CHECKOUT2_NO", initialValue = 1, allocationSize = 1)
@Data
public class Checkout2 {

    //대출코드
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHECKOUT2_NO")
    @Column(name = "NO")
    private BigInteger no;


    //등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name="REGDATE", updatable = false)
    private Date regdate;


    //외래키 - 도서
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bcode", referencedColumnName = "code")
    private Book2 book2;

    //외래키 - 학생
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semail", referencedColumnName = "email")
    private Student2 student2;

    
    
}
