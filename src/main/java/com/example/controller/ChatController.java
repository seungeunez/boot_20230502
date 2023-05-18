package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller

public class ChatController {


    @GetMapping(value = "/chat.do")
    public String chatGET(){
        return "chat";
    }
    
}
