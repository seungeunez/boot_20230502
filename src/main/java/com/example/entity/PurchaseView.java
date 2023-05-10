package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Immutable //뷰일 경우 추가 => 조회만 가능한 엔티티
@Entity
@Table(name = "PURCHASEVIEW")
public class PurchaseView {

    @Id
    @Column(name = "NO")
    private BigDecimal no;

    @Column(name = "NAME")
    private String name; //고객 이름

    private String customerid;

    private String itemname;

    @Column(name = "ITEMNO")
    private BigDecimal itemno;

    @Column(name = "CNT")
    private BigDecimal cnt;

    @Column(name = "ITEMPRICE")
    private BigDecimal itemprice;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp 
    @Column(name = "REGDATE")
    private Date regdate;
    
}
