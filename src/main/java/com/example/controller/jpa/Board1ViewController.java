package com.example.controller.jpa;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Board1View;
import com.example.repository.Board1ViewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/board1view")
@RequiredArgsConstructor
public class Board1ViewController {

    final Board1ViewRepository b1vRepository;

    //num이 0또는 없으면 전체
    //num이 1이면 and
    //num이 2이면 or
    //num이 3이면 글 번호 in
    //num이 4이면 제목 in

    @GetMapping(value = "/selectlist.pknu")
    public String selectlistGET(Model model, @RequestParam(name = "num", defaultValue = "0") int num, @RequestParam(name = "no1", defaultValue = "") Long no, @RequestParam(name = "title1", defaultValue = "") String title){
        try {
            
            
            List<Board1View> list = b1vRepository.findAllByOrderByNoDesc(); //전체조회
            
            if(num == 1) {
                 list = b1vRepository.findByNoAndTitleOrderByNoDesc(no, title); //글 번호와 제목이 정확하게 일치하는 것만 조회 
            } else if(num == 2){
                 list = b1vRepository.findByNoOrTitleOrderByNoDesc(no, title); //글 번호, 제목 둘 중 하나 이상 일치하는 것만 조회
            } //else if(num == 3){
            //     list = b1vRepository.findByNoInOrderByNoDesc(no); //글 번호에 해당하는 항목만 조회
            // } else if(num == 4){
            //     list = b1vRepository.findByTitleInOrderByNoDesc(title); //제목이 해당하는 항목만 조회
            // }


            model.addAttribute("list", list);

            return "/board1view/selectlist";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";

        }
    }

    
}
