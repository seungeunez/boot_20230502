package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.library.Library2;
import com.example.repository.library.Library2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value = "/api/library2")
@RequiredArgsConstructor
@Slf4j
public class RestLibrary2Controller {

    final Library2Repository l2Repository;

/* ------------------------------------------------------------------------------------- */

    //127.0.0.1:9090/ROOT/api/library2/insert.json => postman에서 확인할 것
    //{result:1}

    //@RequestBody Library2 library2 => 기본으로 보낼 때
    //@ModelAttribute Library2 library2 => 위에 방식이 불가능할 때 @ModelAttribute를 사용 (이미지 같은 경우)
    // @ModelParam도 위에 방식이 불가능할 때

    //프로젝트할 땐 안써도 되는데 나중에 회사에선 rest씀 백엔드는 화면을 넘나드는것 불가능
    //백엔드와 프론트엔드 한사람이 하지 않음
    //도서관 등록
    @PostMapping(value = "/insert.json")
    public Map<String, Object> insertPOST(@RequestBody Library2 library2){ //@RequestBody니깐 postman -> body에서 확인할 것 //rest에선 Map<String, Object>이렇게 사용할 것

        Map<String, Object> retMap = new HashMap<>();

        try {
            log.info("{}", library2.toString());
            l2Repository.save(library2); //혼자할 땐 save 빼고 데이터가 오는지 확인 후 save할 것
            retMap.put("status", 200); //성공하면 postman에서 "status": 200 이 뜸 

            
        } catch (Exception e) {
            e.printStackTrace(); //개발자를 위한 개발자용 debug
            retMap.put("status", -1);
            retMap.put("error", e.getMessage()); //오류메시지 던져주는
        }

        return retMap;
    }


/* ------------------------------------------------------------------------------------- */

    //전체조회
    //127.0.0.1:9090/ROOT/api/library2/selectlist.json
    @GetMapping(value="/selectlist.json")
    public Map<String, Object> selectlistGET(){

        Map<String, Object> retMap = new HashMap<>();

        try {
            
            List<Library2> list = l2Repository.findAllByOrderByNameAsc();
            log.info("전체조회 => {}", list.toString());
            retMap.put("status", 200); //성공하면 postman에서 "status": 200 이 뜸 
            retMap.put("list",list); // 조회만 하는거임 postman에서 확인가능 //view로 보내는거 아님 //view에선 어케 보여지는거임 ㅠ 

            
        } catch (Exception e) {
            e.printStackTrace(); //개발자를 위한 개발자용 debug
            retMap.put("status", -1);
            retMap.put("error", e.getMessage()); //오류메시지 던져주는
        }

        return retMap;
    }

/* ------------------------------------------------------------------------------------- */
    

    
}
