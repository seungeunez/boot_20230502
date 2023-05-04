package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/board")
public class RestBoardController {


    //127.0.0.1:9090/ROOT/api/board/select.json
    @GetMapping(value = "/select.json")
    public Map<String, String> selectGET(){
        Map<String, String> retMap = new HashMap<>();

        retMap.put("result", "ok");
        return retMap;


    }
    
}
