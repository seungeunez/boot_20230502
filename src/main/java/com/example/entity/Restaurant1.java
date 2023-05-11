package com.example.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "RESTAURANT1")
@SequenceGenerator(name = "SEQ_RESTAURANT1_NO", sequenceName = "SEQ_RESTAURANT1_NO", initialValue = 1, allocationSize = 1) //시퀀스 //초기값:1 할당값:1
@IdClass(Restaurant1ID.class)
public class Restaurant1 {

    @Id
    //@Generated(GenerationTime.INSERT) => 기본키 두 개 안잡고 했을 땐 @Id 대신 Generated 쓰면 됨
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RESTAURANT1_NO")
    private BigInteger no; //오라클로 만들경우 시퀀스는 BigInteger 이걸루

    @Id
    private String phone;

    private String name;

    private String address;

    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp 
    @Column(updatable = false)
    private Date regdate;

    @OneToMany(mappedBy = "restaurant1")
    List<Menu1> menuList = new ArrayList<>();

    
}
