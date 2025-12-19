package dev.joaountura.aihelper.websocket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

    private final SimpMessagingTemplate messagingTemplate;


    public NotificationsService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(String userId, String message){

        messagingTemplate.convertAndSendToUser(userId, "/queue/messages", message);

    }

}
