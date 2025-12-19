package dev.joaountura.aihelper.services;

import dev.joaountura.aihelper.dtos.ProducerGitHubDTO;
import dev.joaountura.aihelper.dtos.UploadRepositoryDTO;
import dev.joaountura.aihelper.producer.ProducerGitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ModelServices {

    @Autowired
    private ProducerGitHub producerGitHub;

    public void sendToGitHubTopic(UploadRepositoryDTO uploadRepositoryDTO, String user_id){
        ProducerGitHubDTO producerGitHubDTO = ProducerGitHubDTO
                .builder()
                .user_id(user_id)
                .username(uploadRepositoryDTO.getUsername())
                .repository(uploadRepositoryDTO.getRepository())
                .operation_id(UUID.randomUUID().toString())
                .build();

        producerGitHub.sendToTopic(producerGitHubDTO);
    }

}
