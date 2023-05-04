package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Board;
import com.example.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// => html을 표시할 수 없음
// Map, Member, Board, List => 를 반환하면 자동으로 json으로 바꿔줌

@RestController
@RequestMapping(value = "/api/board")
@RequiredArgsConstructor
@Slf4j

//GET => 조회
//POST => 추가(글쓰기), 로그인(암호가 있는거)
// DELETE => 삭제
// PUT => 전체수정, PATCH => 일부수정

public class RestBoardController {

    final String format = "RestBoard => {}";

    final BoardMapper boardMapper; //매퍼객체 생성

    //테스트 용
    //127.0.0.1:9090/ROOT/api/board/select.json
    @GetMapping(value = "/select.json")
    public Map<String, String> selectGET(){ //Map<String, String>을 권장함  String 쓰면 안나옴  List를 써야하는게 아닌이상 Map<String, 맞는 타입> 쓰면 됨
        Map<String, String> retMap = new HashMap<>();

        retMap.put("result", "ok");
        return retMap;
    }

/* ------------------------------------------ */


    //전체 게시글 조회
    //조회된 내용을 주세요 => GET임
    //127.0.0.1:9090/ROOT/api/board/selectlist.json
    @RequestMapping(value = "/selectlist.json", method = {RequestMethod.GET}) //@RequestMapping을 쓰면 GET POST 둘 다 쓸 수 있음
    public List<Board> RequestMethodName()  {
        //[{}, {}, {} ... {}] => 리스트 모양 이렇게 생겼음 postman에서도 저렇게 출력 됨

        return boardMapper.selectBoardList();
    }

/* ------------------------------------------ */

    //게시판 글 쓰기 => 제목, 내용, 작성자 => {"title" : "a", "content" : "b", "writer" : "c"}
    //새로운 데이터 추가해주세요 => POST
    //127.0.0.1:9090/ROOT/api/board/insert.json
    @RequestMapping(value = "/insert.json", method = {RequestMethod.POST})
    public Map<String, Integer> insertPOST(@RequestBody Board board) { //추가 성공했다 안햇다만 알려주면 되니깐 걍 Map 쓰면 됨 근데 result 0, 1로 확인되니깐 int 쓴거임
        
        log.info(format, board.toString()); //전송되는 값 확인

        int ret = boardMapper.insertBoardOne(board);
        
        //DB에 추가하고 결과를 1또는 0으로 반환
        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("result", ret); // 0 실패, 1 성공
        return retMap;
    }

/* ------------------------------------------ */

    //게시글 조회수 증가
    //게시글 번호가 전달되면 update를 이용해서 게시글증가 시키고 결과를 result:1, result:0 전달
    //127.0.0.1:9090/ROOT/api/board/updatehit.json?no=
    @PutMapping(value = "/updatehit.json")
    public Map<String, Integer> updatehitPUT(@RequestBody Board board){

        int ret = boardMapper.updateHit(board.getNo());

        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("result", ret); // 0 실패, 1 성공
        return retMap;

    }

/* ------------------------------------------ */

    //게시글 삭제
    //127.0.0.1:9090/ROOT/api/board/delete.json?no=
    @DeleteMapping(value = "/delete.json")
    public Map<String, Integer> deleteDelete(@RequestBody Board board){


        int ret = boardMapper.deleteBoard(board.getNo()); 

        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("result", ret); // 0 실패, 1 성공
        return retMap;
    }

/* ------------------------------------------ */

    //게시글 수정
     //127.0.0.1:9090/ROOT/api/board/update.json
    @PutMapping(value = "/update.json")
    public Map<String, Integer> updatePUT(@RequestBody Board board){


        int ret = boardMapper.updateBoard(board);

        Map<String, Integer> retMap = new HashMap<>();
        retMap.put("result", ret); // 0 실패, 1 성공
        return retMap;

    }

/* ------------------------------------------ */
    


    
}
