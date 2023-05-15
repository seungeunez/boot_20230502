package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.standard.expression.Each;

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

    final Student2Repository s2Repository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

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

}
