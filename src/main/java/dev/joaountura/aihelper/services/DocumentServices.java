package dev.joaountura.aihelper.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.joaountura.aihelper.dtos.ConsumerGitHubResultDTO;
import dev.joaountura.aihelper.models.Document;
import dev.joaountura.aihelper.models.UserApp;
import dev.joaountura.aihelper.repositories.DocumentRepository;
import dev.joaountura.aihelper.repositories.UserRepository;
import dev.joaountura.aihelper.websocket.services.NotificationsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DocumentServices {

    @Autowired
    private NotificationsService notificationsService;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ObjectMapper objectMapper;


    public void saveDocument(String payload) throws JsonProcessingException {

        ConsumerGitHubResultDTO dto = objectMapper.readValue(payload, ConsumerGitHubResultDTO.class);

        String userId = dto.getUser_id();

        notificationsService.sendNotification(userId, objectMapper.writeValueAsString(dto));

        UserApp userApp = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + userId));

        Document document = Document.builder()
                .operation_id(dto.getOperation_id())
                .document(dto.getDocument())
                .extraDocs(dto.getExtraDocs())
                .tokenUsage(dto.getTokenUsage())
                .userApp(userApp)
                .build();

        documentRepository.save(document);

    }


}
