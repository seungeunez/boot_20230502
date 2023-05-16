package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.library.Student2;
import com.example.entity.library.Student2Projection;
import com.example.repository.library.Student2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value = "/api/student2")
@RequiredArgsConstructor
@Slf4j
public class RestStudent2Controller {


    final JwtUtil2 jwtUtil2; //컴포넌트 객체 생성 

    final Student2Repository s2Repository; //저장소 객체 생성
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder(); //암호화 객체



/* ------------------------------------------------------------------------ */

    //회원가입
    //127.0.0.1:9090/ROOT/api/student2/insert.json
    @PostMapping(value="/insert.json")
    public Map<String, Object> insertPost(@RequestBody Student2 student2) {

        Map<String, Object> retMap = new HashMap<>();
        
        try {

            //암호는 bcpe를 이용하여 암호화하기
            student2.setPassword(bcpe.encode(student2.getPassword())); //이렇게만 넣으면 바로 암호화 됨

            log.info("{}", student2.toString());

            s2Repository.save(student2);

            retMap.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage()); 
        }
        return retMap; 
    }

/* ------------------------------------------------------------------------ */


    //get은 body를 못보냄
    //이메일 중복 확인용
    //127.0.0.1:9090/ROOT/api/student2/emailchkeck.json?email=이메일
    @GetMapping(value = "/emailchkeck.json")
    public Map<String, Object> emailchkeckGET(@RequestParam(name="email") String email){
        
        Map<String, Object> retMap = new HashMap<>();

        try {

            retMap.put("status", 200);
            // retMap.put("student2", s2Repository.countByEmail(student2.getEmail()));
            retMap.put("student2", s2Repository.countByEmail(email));
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage()); 
        }
        return retMap; 
    }


    //get은 body를 못보냄
    //이메일이 전달되면 회원의 이름, 연락처가 반환
    //127.0.0.1:9090/ROOT/api/student2/selectone.json?email=이메일
    @GetMapping(value = "/selectone.json")
    public Map<String, Object> selectoneGET(@RequestParam(name="email") String email){

        Map<String, Object> retMap = new HashMap<>();

        try {

            // Student2Projection  s2Projection = s2Repository.findByEmail(student2.getEmail());
            Student2Projection  s2Projection = s2Repository.findByEmail(email);


            retMap.put("status", 200);
            retMap.put("student2", s2Projection);
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage()); 
        }
        return retMap; 
    }


/* ------------------------------------------------------------------------ */

    //로그인
    //127.0.0.1:9090/ROOT/api/student2/login.json
    @PostMapping(value="/login.json")
    public Map<String, Object> loginPOST(@RequestBody Student2 student2) {

        Map<String, Object> retMap = new HashMap<>();

        try {

            //1. 이메일, 암호를 전송 확인
            log.info("{}", student2.toString());

            //2. 이메일을 이용해서 정보를 가져옴
            Student2 retStudent2 = s2Repository.findById(student2.getEmail()).orElse(null);


            //3. 로그인 실패 했을 경우 전송할 데이터
            retMap.put("status", 0);

            //4. 암호가 일치하는지 확인 => 전송된 hash되지 않은 암호와 DB에 해시된 암호 일치 확인
            if(bcpe.matches(student2.getPassword(), retStudent2.getPassword())){

                retMap.put("status", 200);
                retMap.put("token", jwtUtil2.createJwt(retStudent2.getEmail(), retStudent2.getName()));

            }  
            
        } catch (Exception e) {

            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage()); 

        }
        return retMap; 
    }

/* ------------------------------------------------------------------------ */

    //회원탈퇴, 비밀번호 변경, 회원정보 수정 => 로그인이 되어야 되는 모든 것이 진행될 수 있음

    //원래는 Put을 써야함 수정이다보니
    //회원정보 수정 => 토큰을 주세요. 검증해서 성공하면 정보수정을 진행
    @PostMapping(value = "/update.json")
    public Map<String, Object> updatePOST(@RequestHeader(name = "token") String token){

        Map<String, Object> retMap = new HashMap<>();

        try {

            //1. 토큰을 받는다
            log.info("{}", token);

            //2. 실패시 전달값
            retMap.put("status", 0);

            //3. 토큰을 검증
            if( jwtUtil2.checkJwt(token) == true ){ //검증 성공하면 정보 수정으로

                //4. 정보 수정



                retMap.put("status", 200);
            }
            

            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("status", -1);
            retMap.put("error", e.getMessage()); 
        }
        return retMap; 
    }



    

}



