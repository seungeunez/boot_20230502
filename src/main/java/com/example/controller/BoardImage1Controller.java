package com.example.controller;

import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Board1;
import com.example.entity.BoardImage1;
import com.example.repository.Board1Repository;
import com.example.repository.BoardImage1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping(value = "/boardimage1")
@RequiredArgsConstructor
public class BoardImage1Controller {

    final String format = "BImage => {}";
    final BoardImage1Repository bi1Repository; //이미지
    final Board1Repository b1Repository; //게시글

/* ---------------------------------------------- */

    //이미지 목록
    @GetMapping(value="/selectlist.do")
    public String selectListGET(@RequestParam(name = "no") long no, Model model) {

        try {

            Board1 board1 = b1Repository.findById(no).orElse(null);
            model.addAttribute("board1", board1);

            return "/boardimage1/selectlist";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/home.do";
            
        }
        
    }

/* ---------------------------------------------- */

    //이미지 등록
    @PostMapping(value = "/insertimage.do")
    public String insertPOST(@ModelAttribute BoardImage1 boardImage1, @RequestParam(name = "tmpfile") MultipartFile file){

        try {

            boardImage1.setImageSize(file.getSize());
            boardImage1.setImageData(file.getInputStream().readAllBytes());
            boardImage1.setImageType(file.getContentType());
            boardImage1.setImageName(file.getOriginalFilename());
            //log.info(format, boardImage1.toString());

            
            bi1Repository.save(boardImage1);

            return "redirect:/boardimage1/selectlist.do?no=" + boardImage1.getBoard1().getNo(); //첫 등록일 땐 get 해서 불러 오는듯 //수정, 삭제와는 다르다

            

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/home.do";

        }
        
    }

    
/* ---------------------------------------------- */




}
