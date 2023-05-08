package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.Member1;
import com.example.repository.Member1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping(value = "/member1")
@RequiredArgsConstructor
public class Member1Controller {

    final String format = "Member1Controller => {}";
    final Member1Repository m1Repository; //저장소 객체 ////원래는 Service 써야하는데 생략했음

/* ----------------------------------------------------------------- */

    //회원가입
    //127.0.0.1:9090/ROOT/member1/join.do
    @GetMapping(value = "/join.do")
    public String joinGET(){
        return "/member1/join"; //templates / member1 / join.html 이동 (화면 나옴)
    }
    
    @PostMapping(value="/join.do")
    public String joinPOST(@ModelAttribute Member1 member1) {

        try {

            log.info(format, member1.toString()); //꼭 찍어보자

            //저장소 호출해서 DB에 넣기해야함 (mapper, service가 없는데도 들어가짐)
            m1Repository.save(member1); //sql문을 안쓰면 DB종류 상관없이 넘어가니깐 이런점에선 좋음
            
            return "redirect:/member1/join.do";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
        
        
    }


/* ----------------------------------------------------------------- */

    //회원전체조회
    //ModelAndView란  model.Attribute + return html 혼합된거라고 생각하면 됨
    ////127.0.0.1:9090/ROOT/member1/selectlist.do
    @GetMapping(value = "selectlist.do")
    public ModelAndView selectListGET(){ // ModelAndView를 쓰면 (Model model) 이렇게 하던거 안한다고 생각하면됨 
        List<Member1> list = m1Repository.findAll();
        return new ModelAndView("/member1/selectlist", "list", list); //(return 값, model.attribute 했던 값들 두 개)

    }


/* ----------------------------------------------------------------- */

    //삭제
    @PostMapping(value = "delete.do")
    public String deletePOST(@RequestParam(name = "id") String id){

        try {
            m1Repository.deleteById(id); //id를 불러오면 되니깐 @ModelAttribute Member1 member1를 쓴게 아니라서 .delete()안했음  //int가 없어서 성공했는지 실패했는지 알 수 없음 그래서 예외처리를 하면됨
            return "redirect:/member1/selectlist.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }

    }

/* ----------------------------------------------------------------- */

    //수정
    @GetMapping(value = "/update.do")
    public ModelAndView updateGET(@RequestParam(name = "id") String id){
        Member1 member1 = m1Repository.findById(id).orElse(null); //null값은 왜 했냐 Optional<Member1>이라고 떠서 뭐.. 궁시렁 궁시렁 null에 대한 처리? null에 대한 처리를 왜 하는데 // 뭔데 그게
        return new ModelAndView("/member1/update", "member1", member1);
    }


    @PostMapping(value="/update.do")
    public String updatePOST(@ModelAttribute Member1 member1) {

        try {

            log.info(format, member1.toString());

            
            //기존데이터를 읽어서 변경되지 않는 항목을 그대로 유지
            //기존 항목을 읽어와야함 아니면 날짜가 날아가버림

            //기존데이터를 읽음(아이디를 이용해서)
            Member1 member2 = m1Repository.findById(member1.getId()).orElse(null);
            //변경항목을 바꿈(이름, 나이)
            member2.setId(member1.getId());
            member2.setName(member1.getName());
            member2.setAge(member1.getAge());

            //다시 저장함 (기본키인 아이디 정보가 있어야 됨. 없으면 추가됨)
            m1Repository.save(member2); //save는 id값이 있는걸 넣으면 update 없으면 insert 

            return "redirect:/member1/selectlist.do";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }

        

    }
    

/* ----------------------------------------------------------------- */
}
