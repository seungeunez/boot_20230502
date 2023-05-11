package com.example.controller.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping(value = "/selectlist.pknu")
    public String selectlistGET(Model model, @ModelAttribute Board1View board1View,
                            @RequestParam(name = "num", defaultValue = "0") int num, 
                            @RequestParam(name = "no1", defaultValue = "") String no1, 
                            @RequestParam(name = "title1", defaultValue = "") String title1){

        try {
            
            List<Board1View> list = new ArrayList<>();
            
            if(num == 1) {

                list = b1vRepository.findByNoAndTitleOrderByNoDesc(board1View.getNo(), board1View.getTitle()); //글 번호와 제목이 정확하게 일치하는 것만 조회 

            } else if(num == 2){

                list = b1vRepository.findByNoOrTitleOrderByNoDesc(board1View.getNo(), board1View.getTitle()); //글 번호, 제목 둘 중 하나 이상 일치하는 것만 조회

            } else if(num == 3){  

                log.info(" no1 => {}",no1.toString());

                String[] arr = no1.split(","); //split은 문자열 분할이라서

                List<Long> obj = new ArrayList<>();

                for(int i=0; i<arr.length; i++){
                    obj.add(Long.parseLong(arr[i])); // add()는 리스트의 가장 끝에 값을 추가함
                }

                log.info("obj => {}",obj.toString());

                list = b1vRepository.findByNoInOrderByNoDesc(obj);
                

            } else if( num == 4){

                String[] arr = title1.split(","+ " "); //띄어쓰기도 가능함 
                List<String> obj = new ArrayList<>();

                for(int i=0; i<arr.length; i++){
                    obj.add(arr[i]);
                }

                list = b1vRepository.findByTitleInOrderByNoDesc(obj);


            }

            if(num == 0){ //0이거나 공백일 때 전체조회
                list = b1vRepository.findAllByOrderByNoDesc();
            }
            
            
            

            model.addAttribute("list", list);

            return "/board1view/selectlist";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";

        }
    }

    
}
