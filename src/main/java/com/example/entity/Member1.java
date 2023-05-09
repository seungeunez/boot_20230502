package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
    @CreationTimestamp //추가시에만 날짜 정보 저장
    private Date regdate;


    //EAGER => member1조회시 address1을 조인하여 보여줌 //다 읽을 때 까지 느림
    //LAZY => member1조회시 address1을 조인하지 않고 address1을 필요할 때 조인함 //지연 // 필요할 때만 가져옴 //속도가 빠름
    //cascade => member1의 회원을 지우면 자동으로 address1의 관련 주소도 삭제함
    //가지고 있는 것만 됨. 검색, 페이지네이션 이런게 안됨
    //여기는 양방향 근데 단방향을 써라 양방향을 쓰면 복잡해짐 ㅠ
    @ToString.Exclude // 오류나서 exclude 함. stackoverflow
    @OneToMany(mappedBy = "member1", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) //Member1테이블 입장에선 1 : n 관계 //mappedBy = "여기" 들어갈 이름은 Address1에서 지정해준 private Member1 member1;이 들어간 거임
    // @OrderBy(value = "no desc") //정렬 (내림차순)
    List<Address1> list = new ArrayList<>();
    // 그래서 주석 처리 해버림 
    // 단방향을 쓰자

    //주석 처리하니깐 주소등록이 되어있는 회원이 삭제가 안됐음 그래서 다시 주석 풀었어
    
    
}
