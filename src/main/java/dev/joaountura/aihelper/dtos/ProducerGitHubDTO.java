package dev.joaountura.aihelper.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducerGitHubDTO {
    private String user_id;
    private String operation_id;
    private String username;
    private String repository;


}
