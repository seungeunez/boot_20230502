package com.example.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"password"})
public class CustomUser extends User { //이름, 나이도 홈페이지에 띄우고 싶은데 id, password, authorities만 현재 User.class에 있기때문에 필요한 항목을 추가하기 위해서 만들었음

    private String id; //username
    private String password; //password
    private Collection<GrantedAuthority> authorities; //role
    private String name;
    private int age;

    // 밑에 코드 생성 방법은 우클릭 - 소스작업 - Generate Constructors.. - User String String 위에 선택 - 전부 선택 하면 만들어짐 근데 손을 좀 봐야함 개쓰뤡~!

    //User의 생성자 기본구조
    public CustomUser(String username, String password, Collection<?extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        
    }

    //기본구조에서 추가할 내용 포함 (이름, 나이)
    public CustomUser(String username, String password, Collection<GrantedAuthority> authorities, String name, int age) {
        super(username, password, authorities); //부모의 생성자는 유효하고
        this.id = username;
        this.password = password;
        this.authorities = authorities;
        this.name = name; //필요한 항목 추가했음
        this.age = age; //필요한 항목 추가했음
    }


    
    
    
    
}
