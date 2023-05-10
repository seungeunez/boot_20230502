package com.example.controller.jpa;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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


    //이미지 출력할 때 필요
    final ResourceLoader resourceLoader; // resources폴더의 파일을 읽기 위한 객체 생성
    @Value("${default.image}") String DEFAULTIMAGE;

/* ---------------------------------------------- */

    //이미지 목록
    @GetMapping(value="/selectlist.do")
    public String selectListGET(@RequestParam(name = "no") long no, Model model, HttpServletRequest request) {

        try {

            //게시글 정보
            Board1 board1 = b1Repository.findById(no).orElse(null);
            


            /* -------------------------대표이미지 추가됐음---------------------------- */
            BoardImage1 image1 = bi1Repository.findTop1ByBoard1_noOrderByNoAsc(no);
            board1.setImageUrl(request.getContextPath() + "/boardimage1/image?no=0");
            if(image1 != null){
                board1.setImageUrl(request.getContextPath() + "/boardimage1/image?no=" + image1.getNo());
                log.info(format, image1.toString());
            }
            /* ------------------------------------------------------------------------- */

            //이미지도 포함하여 view로 전달
            model.addAttribute("board1", board1);

            /* -------------------------전체이미지 추가됐음---------------------------- */

            List<String> imageList = new ArrayList<>();
            List<BoardImage1> list1 = bi1Repository.findByBoard1_noOrderByNoAsc(no);
            if(!list1.isEmpty()){ //리스트가 비어있지 않는지 확인

                for(BoardImage1 tmp : list1) {
                    imageList.add(request.getContextPath() + "/boardimage1/image?no=" + tmp.getNo());
                }
                
                //log.info(format, list1.toString());
            }

            model.addAttribute("imageList", imageList);

            /* ------------------------------------------------------------------------- */

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

    //이미지 출력
    //127.0.0.1:9090/ROOT/boardimage1/image?no=1 이미지 나오는지 확인용
    @GetMapping(value = "/image")

    public ResponseEntity<byte[]> image(@RequestParam(name = "no", defaultValue = "0") long no) throws IOException {
        
        BoardImage1 obj = bi1Repository.findById(no).orElse(null);
        HttpHeaders headers = new HttpHeaders();  // import org.springframework.http.HttpHeaders;
        
        if(obj != null) { // 이미지가 존재할 경우
            if(obj.getImageSize() > 0) {
                headers.setContentType(  MediaType.parseMediaType( obj.getImageType()  )  );
                return new ResponseEntity<>( obj.getImageData(), headers, HttpStatus.OK );
            }
        }

        //이미지가 없을 경우 // 저장된 default 이미지가 나옴 
        InputStream is = resourceLoader.getResource(DEFAULTIMAGE).getInputStream(); // exception 발생됨.
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>( is.readAllBytes(), headers, HttpStatus.OK );
    }

/* ---------------------------------------------- */


}
