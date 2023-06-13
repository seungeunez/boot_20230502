package com.example.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.entity.Board1;
import com.example.repository.Board1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component //기능적으로 특별한게 없으면 component넣을 것
@Slf4j
@RequiredArgsConstructor
public class MyScheduler {

    // final Board1Repository board1Repository;


    // crob tab
    // 초, 분, 시간, 일, 월, 요일
    // */5 * * * * * => 5초 간격을 동작되게 되어있음
    // */20 * * * * * => 20초 간격 동작
    // 0 * * * * * => 정확하게 0초가 될 때 (1분 간격) 동작
    @Scheduled(cron = "*/5 * * * * *") // =>이게 있어야 스케쥴링이 제대로 동작됨
    public void printDate(){
        log.info("{}" , new Date().toString());

        // List<Board1> list = board1Repository.findAll();
        // log.info("{}", list.toString());
    }
    
}
