package com.example.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Reply1;
import com.example.repository.Reply1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping(value = "/reply1")
@RequiredArgsConstructor
@Slf4j
public class Reply1Controller {

    final String format = "Reply1Controller => {}";
    final Reply1Repository r1Repository;

/* ------------------------------------------------ */

    //답글 등록
    @PostMapping(value="/insert.do")
    public String insertPOST(@ModelAttribute Reply1 reply1){

        log.info(format, reply1.toString());

        r1Repository.save(reply1);
        
        return "redirect:/board1/selectone.do?no="+reply1.getBoard1().getNo();
    }

/* ------------------------------------------------ */



    
}
