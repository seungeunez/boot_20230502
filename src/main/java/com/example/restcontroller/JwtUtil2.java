package com.example.restcontroller;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import com.example.entity.library.Student2;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


//컨트롤러 x, 서비스x, 엔티티x .... 

//토큰에 변화가 생기면 안됨

@Component
public class JwtUtil2 {

    private final String BASEKEY = "abcdefhasdfasjldfkjasldifjalweknfqwmnflzxkhcfka;isflskdjflak";


    //토큰 생성하는 메소드
    public String createJwt(String id, String name) throws Exception{

        //1. header정보
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT"); // 타입
        headerMap.put("alg", "HS256"); // hash 알고리즘


        //2. 토큰에 포함시킬 사용자 정보들
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", id); //아이디를 토큰에 넣음 //이부분이 기본키가 들어갈 자린지 무튼 원랜 email넣었는데 다른곳에서도 쓰는것같으니 일단 id로 해둠 대신 id로하면 html에 name값이 id여야함 security부분도 id여야함
        claimsMap.put("name", name); //이름을 토큰에 넣음


        //3. 만료시간 ex) 2시간 => (현재시간 + 1000 * 60 * 60 * 2)
        Date expiredTime = new Date();
        expiredTime.setTime(expiredTime.getTime() + 1000 * 60 * 60 * 8); //8시간으로 설정



        //4. 키 발행
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(BASEKEY);
        Key signKey =  new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());


        // 1~4의 정보를 이용해서 토큰 생성
        JwtBuilder builder = Jwts.builder()
                                .setHeader(headerMap)
                                .setClaims(claimsMap)
                                .setSubject("TEST")
                                .setExpiration(expiredTime) //만료시간
                                .signWith(signKey, SignatureAlgorithm.HS256);

        //토큰을 String 타입으로 변환
        return builder.compact();

    }

    

    //Student2 기준으로 
    // 토큰에 대해서 검증하고 데이터를 추출하는 메소드
    public Student2 checkJwt(String token) throws Exception{

        try {

            //1. key 준비
            byte[] keyBytes = DatatypeConverter.parseBase64Binary(BASEKEY);

            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(keyBytes)
                                .build()
                                .parseClaimsJws(token)
                                .getBody();


            System.out.println("추출한 아이디 =>" + claims.get("id")); 
            System.out.println("추출한 이름 =>" + claims.get("name"));

            //이메일과 이름을 발행해서 8시간 유효?하게?

            Student2 student2 = new Student2();

            //형변환필요함 Object타입이라서 //이메일과 이름만 있음
            student2.setEmail( (String) claims.get("id")); 
            student2.setName( (String) claims.get("name"));

            return student2;

        } catch (ExpiredJwtException e1) { //상세한(세밀한) 오류일수록 위쪽에 배치되어야 함
            System.err.println("만료시간 종료" + e1.getMessage());
            return null;
        } 
        catch (JwtException e2) {
            System.err.println("토큰오류" + e2.getMessage());
            return null;
        }
        catch (Exception e) {
            System.out.println("e1과 e2 오류 아닌 모든 오류" + e.getMessage());
            return null;
        }
        
    }



}