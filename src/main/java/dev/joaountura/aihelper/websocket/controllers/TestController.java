package dev.joaountura.aihelper.websocket.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class TestController {

    @MessageMapping("/test.send")
    @SendTo("/topic/chat")
    private String send(String msg, Principal principal){
        System.out.println("Topic " + principal.getName());
        return msg;
    }



}
