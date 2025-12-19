package dev.joaountura.aihelper.producer;

import dev.joaountura.aihelper.configs.KafkaConfig;
import dev.joaountura.aihelper.dtos.ProducerGitHubDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerGitHub {

    @Autowired
    private KafkaTemplate<String, ProducerGitHubDTO> kafkaTemplate;

    public void sendToTopic(ProducerGitHubDTO producerGitHubDTO){
        kafkaTemplate.send(KafkaConfig.REPOSITORY_TOPIC, producerGitHubDTO.getOperation_id(), producerGitHubDTO);
    }

}
