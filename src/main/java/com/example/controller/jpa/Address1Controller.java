package com.example.controller.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Address1;
import com.example.entity.Member1;
import com.example.repository.Address1Repository;
import com.example.repository.Member1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping(value = "/address1")
@RequiredArgsConstructor
public class Address1Controller {

    @Value("${address.pagetotal}") int PAGETOTAL; // 5 대신임. global.properties에 있음.

    final String format = "Address1Controller => {}";
    final Address1Repository a1Repository; //저장소 객체
    final Member1Repository m1Repository;



/* -------------------------------------------------- */


    //주소목록 조회
    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(Model model, @RequestParam(name = "id") String id, @RequestParam(name = "page", defaultValue = "0", required = false) int page){ //page 페이지 네이션으로 추가 된 부분

        try {

            if(page == 0){ //페이지 정보가 없다면 1로 변경하기 //페이지 네이션으로 추가 된 부분
                return "redirect:/address1/selectlist.do?id=" + id + "&page=1";

            }

            //회원정보
            Member1 member1 = m1Repository.findById(id).orElse(null);
            log.info(format, member1.toString()); //오류발생 시점 stackoverflow
            model.addAttribute("obj", member1); //보내기



            /* -------------페이지 네이션 한다고 추가된 부분--------------- */

            //전체 개수 가져오기
            long total = a1Repository.countByMember1_id(member1.getId());
            log.info(format, total);
            model.addAttribute("pages", (total-1)/ PAGETOTAL+1);

            //페이지 네이션 설정
            PageRequest pageRequest = PageRequest.of((page-1), PAGETOTAL);

            /* ------------------------------------------------------------- */



            List<Address1> addressList = a1Repository.findByMember1_idOrderByNoDesc(member1.getId(), pageRequest);
            //log.info(format, addressList); // 해당 아이디의 전체 주소 출력 확인용
            model.addAttribute("address", addressList);

            //redirect없을 때는 html표시
            return "/address1/selectlist";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }

        
    }

/* -------------------------------------------------- */

    //주소추가
    @PostMapping(value="/insert.do")
    public String insertPOST(@ModelAttribute Address1 address1) {
        
        try {

            log.info(format, address1.toString());

            a1Repository.save(address1);
        
            return "redirect:/address1/selectlist.do?id=" + address1.getMember1().getId();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do"; //redirect: 주소창의 주소 바꿈
        }

        
    }


/* -------------------------------------------------- */

    //주소삭제
    @PostMapping(value = "/delete.do")
    public String deletePOST(@RequestParam(name = "no1") long no, @RequestParam(name = "id1") String id){
        try {

            log.info(format, no);
            a1Repository.deleteById(no);

            return "redirect:/address1/selectlist.do?id=" + id;

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/address1/selectlist.do?id=" + id;
        }
    }


/* -------------------------------------------------- */




    
}
