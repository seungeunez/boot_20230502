package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Item;
import com.example.dto.Member;
import com.example.mapper.ItemMapper;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping(value = "/seller")
@RequiredArgsConstructor
public class SellerController {

    //매퍼 객체 생성
    final ItemMapper iMapper; // 매퍼객체

    final MemberMapper memberMapper;

    final HttpSession httpSession; //세션객체

    final String format = "SellerController => {}";

    //http://127.0.0.1:9090/ROOT/seller/home.do
    @GetMapping(value = "/home.do")
    public String homeGET(@RequestParam(name = "menu", defaultValue = "0", required = false) int menu, Model model){


        if(menu == 1){

        } else if(menu == 2){
            String userid = (String) httpSession.getAttribute("USERID");
            List<Item> list = iMapper.selectItemSellerList(userid);
            model.addAttribute("list",list);
        }
        return "/seller/home";

    }

    // ../ROOT/seller/home.do?menu=1
    @PostMapping(value = "/home.do")
    public String homePOST(

                @RequestParam(name = "menu", defaultValue = "0", required = false) int menu, //3개 만들기 싫어서(POST를) menu를 받는거임 //Model model ㄴㄴ 지금은 값을 하나씩 받아와야하는 상황 (배열이라 그런가)
                //1번일 때 씀               
                @RequestParam(name = "name[]", required = false) String[] name,
                @RequestParam(name = "content[]", required = false) String[] content,
                @RequestParam(name = "price[]", required = false) long[] price,
                @RequestParam(name = "quantity[]", required = false) long[] quantity,
                //2번일 때 씀
                @RequestParam(name = "chk[]", required = false) long[] no, 
                @RequestParam(name = "btn", required = false) String btn){  
        
        if(menu == 0){ //정상적이지 않은 상태라 그냥 menu=1로 보내버림
            return "redirect:home.do?menu=1";
        }
        
        if(menu == 1){ //일괄등록

            List<Item> list = new ArrayList<>();

            for(int i=0; i<name.length; i++){

                Item item = new Item(); //객체 생성
                
                item.setName(name[i]);
                item.setContent(content[i]);
                item.setPrice(price[i]);
                item.setQuantity(quantity[i]);
                item.setSeller( (String) httpSession.getAttribute("USERID")); //형변환도 해주기 //로그인 확인 //로그인 된 정보가 여기 들어감

                list.add(item);
            }

            log.info("seller.do => {}", list.toString()); //추가 됐는지 확인용
            
            int ret = iMapper.insertItemBatch(list);
            log.info("seller.do => {}", ret);

        }else if(menu == 2){ //일괄삭제 & 일괄수정

            log.info("seller.do => {}, {}", no, btn);

            if(btn.equals("일괄삭제")){
                int ret = iMapper.deleteItemBatch(no);

            } else if(btn.equals("일괄수정")){
                //체크한 항목 정보를 가지고 있음
                //redirect =>  get으로 이동후에 화면표시 해야함 냅다 주소갈기면 안됨
                httpSession.setAttribute("chk[]", no);
                //return "/item/updatebatch"; //post에서 이렇게("/item/updatebatch") 쓰면 F5눌렀을 때 양식을 다시 제출하겠냐는 말이 나옴
                return "redirect:updatebatch.do";

            }
        }

        return "redirect:home.do?menu=" + menu;
    }

/*-------------------------------------------------------------------*/

    @GetMapping(value="/updatebatch.do")
    public String updateBatchGET(Model model) {

        long[] no = (long[]) httpSession.getAttribute("chk[]"); //형변환 해야함 배열이라 [] 해줬음
        log.info("updatebatch.do[GET] => {} ", no);

        List<Item> list = iMapper.selectItemNoList(no);
        log.info("updatebatch.do[GET] => {} ", list.toString());
        model.addAttribute("list", list);

        return "/item/updatebatch";
    }

    @PostMapping(value="/updatebatch.do")
    public String updateBatchPOST(
        @RequestParam(name = "no[]") long[] no,
        @RequestParam(name = "name[]") String[] name,
        @RequestParam(name = "content[]") String[] content,
        @RequestParam(name = "price[]") long[] price,
        @RequestParam(name = "quantity[]") long[] quantity) {

        List<Item> list = new ArrayList<>();
        for(int i=0; i<no.length; i++) {

            Item item = new Item();

            item.setNo(no[i]);
            item.setName(name[i]);
            item.setContent(content[i]);
            item.setPrice(price[i]);
            item.setQuantity(quantity[i]);

            list.add(item);
        
        }
        log.info("updatebatch.do[POST] => {}", list.toString());

        int ret = iMapper.updateItemBatch(list);
        return "redirect:/seller/home.do?menu=2";
    }

    /*------------------------ */

    //회원가입 화면
    @GetMapping(value = "/join.do")
    public String joinGET(){
        return "/seller/join";
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member member ){
        log.info(format, member.toString()); //화면에 정확하게 표시되고 사용자가 입력한 항목을 member 객체에 저장했음

        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder(); //salt값을 자동으로 부여함 (이게 뭐누..)
        
        member.setPassword(bcpe.encode(member.getPassword())); //기존암호를 암호화 시켜서 다시 저장함.

        int ret = memberMapper.insertMemberOne(member);

        if(ret == 1){
            return "redirect:home.do";//주소창에 127.0.0.1:9090/ROOT/seller/home.do입력 후 엔터키를 자동화시키는 것
        } 
        return "redirect:join.do";  //실패시 그자리 그대로
    }

    /*----------------------- */

    //로그아웃
    //GET, POST가 같은 동작을 함.
    @RequestMapping(value="/logout.do", method = {RequestMethod.GET, RequestMethod.POST}) //RequestMapping을 이용해서 method로 GET POST 방식을 구별하는 건 번거롭기때문에 @PostMapping @GetMapping 방식을 씀
    public String logoutPOST() {
        httpSession.invalidate(); // 세션의 정보를 다 지움.
        return "redirect:/home.do";
    }

    /*-------------------------- */

    


}
