package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/test1")
@RequiredArgsConstructor
@Slf4j
public class RestTestController {


     // 127.0.0.1:9090/ROOT/api/test1/select.do?aaa=a&bbb=b&ccc=c
    // 조회용 @RequestParam(name="aaa") String aaa => 주소는 RequestParam으로 받아라
    @GetMapping(value="/select.do")
    public Map<String,Object> get1(       ){
        Map<String, Object> retMap = new HashMap<>();
        try{
            //db처리
            retMap.put("status", 200);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

    // 추가
    // @RequestBody 엔티티 obj
    @PostMapping(value="/insert.do")
    public Map<String,Object> get2(       ){
        Map<String, Object> retMap = new HashMap<>();
        try{
            //db처리
            retMap.put("status", 200);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

    // 수정
    // 127.0.0.1:9090/ROOT/api/test1/update.do
    // // @RequestBody 엔티티 obj
     // 데이터 변화가 생기는 것들 body에 심ㅁ어서 보내기
    @PutMapping(value="/update.do")
    public Map<String,Object> get3(       ){
        Map<String, Object> retMap = new HashMap<>();
        try{
            //db처리
            retMap.put("status", 200);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

    // 삭제
    // // @RequestBody 엔티티 obj
    @DeleteMapping(value="/delete.do")
    public Map<String,Object> get4(       ){
        Map<String, Object> retMap = new HashMap<>();
        try{
            //db처리
            retMap.put("status", 200);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

    

}
