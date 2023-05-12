package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMBERINFO1")
public class MemberInfo1 {

    @Id
    @Column(name = "ID1")
    private String id1;

    @MapsId //컬럼을 줄이고 ID1컬럼 하나만 생성 //통합된다고 생각하면 됨  // 기본키면서 외래키일 경우 이렇게 설정하면 된다 // 기본키 외래키 역할은 하되 컬럼만 하나로 통합하는거임  // 외래키 설정해줬다고 두개로 인식하는게 아니라 @MapsId 얘가 있어서 컬럼이 하나로 통합되는거임 
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID1")
    private Member1 member1;

    private String info;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;

    
    
}
