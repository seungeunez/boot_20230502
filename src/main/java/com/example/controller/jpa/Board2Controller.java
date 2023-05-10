package com.example.controller.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Board;
import com.example.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping(value = "/board2")
@RequiredArgsConstructor
public class Board2Controller {

    final String format = "Board2Controller => {}";

    final BoardRepository bRepository; //저장소 객체 생성
    final HttpSession httpSession; //세션 객체

/* ------------------------------------------------ */

    //전체 목록
    //127.0.0.1:9090/ROOT/board2/selectlist.pknu
    //1. 전달값 받기 ?page=&type=writer&text=이름
    @GetMapping(value = "/selectlist.pknu")
    public String selectListGET(Model model, 
                            @RequestParam(name = "text", defaultValue = "") String text, 
                            @RequestParam(name = "page", defaultValue = "1") int page,
                            @RequestParam(name = "type", defaultValue = "title") String type ){

        try {


            /* 게시글 수 */
            long total = bRepository.countByTitleContaining(text);
            
            /* -----검색------- */

            //2. 타입에 따라서 다른 메소드 호출

            List<Board> list = bRepository.findByTitleIgnoreCaseContainingOrderByNoDesc(text);
            if(type.equals("content")){
                list = bRepository.findByContentIgnoreCaseContainingOrderByNoDesc(text);
            }else if(type.equals("writer")){
                list = bRepository.findByWriterIgnoreCaseContainingOrderByNoDesc(text);
            }

            /* -------------- */




            //List<Board> list = bRepository.findAllByOrderByNoDesc(); //목록 전체 조회만 했을 때

            model.addAttribute("list", list);
            model.addAttribute("pages", (total-1)/10+1); 
            
            return "/board2/selectlist";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/home.do";

        }

    }

/* ------------------------------------------------ */


    //일괄추가
    @GetMapping(value = "/insertbatch.pknu")
    public String insertBatchGET(Model model){

        try {

            return "/board2/insertbatch";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/board2/selectlist.pknu";
        }

    }

    @PostMapping(value = "/insertbatch.pknu")
    public String insertBatchPOST(
                    @RequestParam(name = "title[]") String[] title, 
                    @RequestParam(name = "content[]") String[] content, 
                    @RequestParam(name = "writer[]") String[] writer ){  //배열이라 하나씩 받아오는게 나음 ModelAttribute로 한번에 받아오는건 복잡해서 ㄴㄴ

        try {
            
            List<Board> list =  new ArrayList<>();

            for(int i=0; i < title.length; i++){

                Board board = new Board();
                
                board.setTitle(title[i]);
                board.setContent(content[i]);
                board.setWriter(writer[i]);
                board.setHit(BigDecimal.valueOf(1));

                list.add(board);
            }

            bRepository.saveAll(list);
            return "redirect:/board2/selectlist.pknu";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
    }

/* ------------------------------------------------ */

    //일괄삭제
    @PostMapping(value = "/deletebatch.pknu")
    public String deleteBatchPOST(@RequestParam(name = "chk[]") List<BigDecimal> chk){

        try {

            log.info("삭제 => {}",chk.toString());
            bRepository.deleteAllById(chk);
            return "redirect:/board2/selectlist.pknu";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/home.do";
        }

    }

/* ------------------------------------------------ */

    //수정
    @SuppressWarnings("unchecked")
    @GetMapping(value = "/updatebatch.pknu")
    public String updateBatchGet(Model model){

        try {
            List<BigDecimal> chk = (List<BigDecimal>) httpSession.getAttribute("chk[]");
            List<Board> list = bRepository.findAllById(chk);
            model.addAttribute("list", list);
            return "board2/updatebatch";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/home.do";
        }

    }

    @PostMapping(value = "/updatebatch.pknu")
    public String updateBatchPOST(@RequestParam(name = "chk[]") List<BigDecimal> chk){ 

        try {
            log.info("수정 ㄱㄱㅆ => {}",chk.toString());
            httpSession.setAttribute("chk[]", chk);
            return "redirect:/board2/updatebatch.pknu";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/home.do";
        }
    }


    //진짜 수정
    @PostMapping(value="/updatebatchaction.pknu")
    public String updatebatchactionPOST(
            @RequestParam(name = "no[]") long[] no, 
            @RequestParam(name = "title[]") String[] title, 
            @RequestParam(name = "content[]") String[] content, 
            @RequestParam(name = "writer[]") String[] writer ){ 

        try {

            List<Board> list =  new ArrayList<>();

            for(int i=0; i < no.length; i++){

                //1. no를 이용하여 기존 정보 가져오기
                Board board = bRepository.findById( BigDecimal.valueOf(no[i])).orElse(null);

                //2. 기존 정보에 위에서 받은 제목, 내용, 작성자 변경하기
                board.setTitle(title[i]);
                board.setContent(content[i]);
                board.setWriter(writer[i]);

                //3. list에 담기
                list.add(board);

            }

            //4. 일괄 저장하기
            bRepository.saveAll(list);
            return "redirect:/board2/selectlist.pknu";

            } catch (Exception e) {

                e.printStackTrace();
                return "redirect:/home.do";
            }
    }
            

/* ------------------------------------------------ */
    

    
}
