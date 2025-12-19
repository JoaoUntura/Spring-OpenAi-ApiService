package dev.joaountura.aihelper.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.joaountura.aihelper.dtos.ConsumerGitHubResultDTO;
import dev.joaountura.aihelper.services.DocumentServices;
import dev.joaountura.aihelper.websocket.services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerGitHubServiceResult {

    @Autowired
    private DocumentServices documentServices;

    @KafkaListener(topics = "result_repo_topic", groupId = "result_default")
    public void resultListener(String payload) throws JsonProcessingException {
            documentServices.saveDocument(payload);
    }

}
