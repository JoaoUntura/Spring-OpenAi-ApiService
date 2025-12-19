package dev.joaountura.aihelper.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public final static String REPOSITORY_TOPIC = "repo_topic";


    @Bean
    public NewTopic newRepositoryTopic(){

        return TopicBuilder.name(REPOSITORY_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();

    }

}
