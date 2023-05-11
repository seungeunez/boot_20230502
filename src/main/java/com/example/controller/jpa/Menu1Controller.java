package com.example.controller.jpa;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Menu1;
import com.example.repository.Menu1Repository;
import com.example.repository.Restaurant1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
@RequestMapping(value = "/menu1")
@RequiredArgsConstructor
public class Menu1Controller {

    final Menu1Repository m1Repository;
    final Restaurant1Repository r1Repository;
    final String format = "m1Controller => {}";

/* --------------------------------------------------- */

    //메뉴 등록
    //127.0.0.1:9090/ROOT/menu1/insert.food
    @GetMapping(value = "/insert.food")
    public String insertGET(Model model, @RequestParam(name = "rno") long rno, @RequestParam(name = "rphone") String rphone){

        try {


            List<Menu1> list = m1Repository.findByRestaurant1_phone(rphone);

            model.addAttribute("list", list);
            

            model.addAttribute("rno", rno);
            model.addAttribute("rphone", rphone);


            return "/menu1/insert";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/home.do";

        }
    }

    @PostMapping(value="/insert.food")
    public String insertPOST(@ModelAttribute Menu1 obj, @RequestParam(name = "tmpFile") MultipartFile file) {
        try {

            //파일은 수동으로 obj에 추가하기
            obj.setImagedata(file.getInputStream().readAllBytes());
            obj.setImagesize(BigInteger.valueOf(file.getSize()));
            obj.setImagetype(file.getContentType());
            obj.setImagename(file.getOriginalFilename());

            log.info("메뉴등록 => {}",obj.toString());


            m1Repository.save(obj);

            return "redirect:/menu1/insert.food?rno=" + obj.getRestaurant1().getNo() + "&rphone=" +obj.getRestaurant1().getPhone();
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }


/* --------------------------------------------------- */

    

    




    
}
