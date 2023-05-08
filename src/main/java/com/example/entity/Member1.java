package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "MEMBER1") //생성하고자하는 테이블, 또는 생성되어 있는 테이블 매칭

//칠판 Repository부분 하는데 먼저 테이블 생성하려고 만들었음 dto느낌
public class Member1 {


    @Id //@Id가 기본키가 되는거임 //import javax.persistence.Id;
    @Column(name = "ID", length = 50) // length = 50 -> 길이 50자로 지정
    private String id; //@Column을 생략하면 변수명이 컬럼명이 된다

    private String pw;

    private String name;

    private int age;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;
    
}
