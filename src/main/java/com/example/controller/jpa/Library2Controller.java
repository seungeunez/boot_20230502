package com.example.controller.jpa;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.library.Library2;
import com.example.repository.library.Library2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping(value = "/library2")
@RequiredArgsConstructor
@Slf4j
public class Library2Controller {

    final Library2Repository l2Repository;

/* ------------------------------------------------------------------------------------- */

    //등록
    @GetMapping(value = "/insert.do")
    public String insertGET(){
        try {

            return "/library2/insert"; //html로 이동
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

    @PostMapping(value="/insert.do")
    public String insertPOST(@ModelAttribute Library2 library2) {
        try {

            log.info("등록 => {}", library2.toString());
            l2Repository.save(library2);

            return "redirect:/library2/selectlist.do"; //등록 후 페이지 이동

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

/* ------------------------------------------------------------------------------------- */

    //전체 조회
    @GetMapping(value = "/selectlist.do")
    public String selectlistGET(Model model ){
        try {

            List<Library2> list =  l2Repository.findAllByOrderByNameAsc();

            // log.info 찍어보면 [클래스, 클래스] 이렇게 나올 거임
            //Library2(code=103, name=도서관, address=부산, phone=070-0000-0004, regdate=2023-05-15 09:58:24.403) code, name, address, phone, regdate가 key임
            log.info("{}", list.toString()); //전체 조회 확인용
            model.addAttribute("list", list); //view로 전송

            return "/library2/selectlist";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }
    
/* ------------------------------------------------------------------------------------- */
    
    
}
