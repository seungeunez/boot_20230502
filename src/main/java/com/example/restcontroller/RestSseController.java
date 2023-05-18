package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.Media;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;


/* 실시간 채팅은 restcontroller로 만들 것 */

/* websocket */
/* socket.io */ 
/* mqtt, xmpp */
/* SSE: Server-Send-Events */


@Slf4j
@RestController
public class RestSseController {


    //접속하는 클라이언트의 정보를 보관할 변수
    private static final Map<String, SseEmitter> clients = new HashMap<>(); //이건 채팅방 1개라고 생각하면 됨  //여러개 필요하면 변수 여러개 생성하면 됨
    

    //클라이언트 접속했을 때 수행
    @GetMapping(value="/api/sse/subscribe")
    public SseEmitter subscribe(@RequestParam(name = "id") String id) {

        //sse객체 생성
        SseEmitter emitter = new SseEmitter(1000L * 1200); // 1초(1000) * 20분(1200) //20분동안 접속 유지 이후엔 끊김 //접속할 때 마다 객체만들어지고 clients에 보관됨
        clients.put(id, emitter);

        //클라이언트 연결 중지 및 완료되면 clients변수에서 정보 제거
        emitter.onTimeout(() -> clients.remove(id));
        emitter.onCompletion(() -> clients.remove(id));

        return emitter;
    }


    //클라이언트가 전송을 했을 때 수행
    // 이렇게 나올 예정 =>  {"ret" : 1, "abc" : "def"}
    @GetMapping(value="/api/sse/publish")
    public void publish(@RequestParam(name = "message") String message) {

            //map에 보관된 개수만큼 반복하면 키 값을 꺼냄
            for(String id : clients.keySet()){ //키가 id에 담긴상황

                try{
                    
                    //map의 키를 이용해서 value값을 꺼냄
                    SseEmitter emitter = clients.get(id);
                    
                    //클라이언트로 메시지 전송
                    emitter.send(message, MediaType.APPLICATION_JSON);
                    
                } catch (Exception e) {
                    
                    clients.remove(id); //문제가 생기면 id제거
                    
                }
            
            }
            

        
    }
    
    /* 포스트맨에서 테스트 불가능 */ 
    /* script를 사용해야 함 // html태그와 script는 다른역할을 함 // script는 뒷쪽에서 사용자에 의해 바꿔지는? //script로 접속할수만 있으면 됨 어떤 html파일이라도 상관없음 별개라고 생각해 */
    /* 이정도만 있어도 채팅은 가능함 */
}
