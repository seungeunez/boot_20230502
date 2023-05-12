package com.example.controller.jpa;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Menu1;
import com.example.entity.Menu1ImageProjection;
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

    //이미지 출력할 때 필요
    final ResourceLoader resourceLoader; // resources폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}") String DEFAULTIMAGE;

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

    //BoardImage1Controller에서 갖고왔음
    //이미지 출력
    //이미지는 DB에서 꺼내서 url형태로 변경시켜야함
    //127.0.0.1:9090/ROOT/menu1/image?no=1001 => 나오는지 확인
    //<img src="/ROOT/menu1/image?no=???" /> => 이미지는 무조건 url형태여야함
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") long no) throws IOException {
        
        Menu1ImageProjection obj =  m1Repository.findByNo(BigInteger.valueOf(no)); //저장소에서 만들어줘야함 //no 타입이 BigInteger이라서 .valueOf()가 필요했음

        HttpHeaders headers = new HttpHeaders();  // import org.springframework.http.HttpHeaders;
        
        if(obj != null) { // 이미지가 존재할 경우
            if(obj.getImagesize().longValue() > 0) { //사이즈 타입이 BigInteger이라서 .longValue()가 필요했음
                headers.setContentType(  MediaType.parseMediaType( obj.getImagetype()  )  );
                return new ResponseEntity<>( obj.getImagedata(), headers, HttpStatus.OK );
            }
        }

        //이미지가 없을 경우 => 저장된 default 이미지가 나옴 
        InputStream is = resourceLoader.getResource(DEFAULTIMAGE).getInputStream(); // exception 발생됨.
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>( is.readAllBytes(), headers, HttpStatus.OK );
    }

    
/* --------------------------------------------------- */

    //메뉴삭제
    @PostMapping(value="/delete.food")
    public String deletePOST(@RequestParam(name = "no") long no, @RequestParam(name = "rno") long rno, @RequestParam(name = "rphone") String rphone) {


        try {
            
            log.info("{}",no);

            m1Repository.deleteById(BigInteger.valueOf(no));

            return "redirect:/menu1/insert.food?rno=" + rno + "&rphone=" + rphone;

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
        
    }
    


/* --------------------------------------------------- */


    //메뉴수정
    @GetMapping(value = "/update.food")
    public String updateGET(Model model,@RequestParam(name = "no") long no, @RequestParam(name = "rno") long rno, @RequestParam(name = "rphone") String rphone){
        try {

            Menu1 obj = m1Repository.findById(BigInteger.valueOf(no)).orElse(null);

            model.addAttribute("obj", obj);
            model.addAttribute("rno", rno);
            model.addAttribute("rphone", rphone);

            return "/menu1/update";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }


    @PostMapping(value="/update.food")
    public String updatePOST( @RequestParam(name = "tmpFile") MultipartFile file, @ModelAttribute Menu1 menu1 ) { 

        try {
            
            log.info("메뉴 => {}",menu1.toString());
            log.info("파일 => {}",file.toString());

            //기존의 데이터를 읽어서 필요한 부분 변경후 다시 저장하기
            Menu1 obj = m1Repository.findById(menu1.getNo()).orElse(null);

            //저장하면 자동으로 DB에 변경됨 (자동commit)
            obj.setName(menu1.getName());
            obj.setPrice(menu1.getPrice());

            if(file.isEmpty() == false){  //파일 객체는 만들어져 있어서 isEmpty()로 구분해야함
                obj.setImagedata(file.getBytes());
                obj.setImagesize(BigInteger.valueOf(file.getSize()));
                obj.setImagetype(file.getContentType());
                obj.setImagename(file.getOriginalFilename());
            }
            
            m1Repository.save(obj);

            return "redirect:/menu1/insert.food?rno=" + menu1.getRestaurant1().getNo() + "&rphone=" + menu1.getRestaurant1().getPhone();

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }

    }
    
/* --------------------------------------------------- */

    
}
