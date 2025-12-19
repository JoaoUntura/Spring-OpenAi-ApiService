package dev.joaountura.aihelper.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerGitHubResultDTO {
    private String user_id;
    private String operation_id;
    private String document;
    private long tokenUsage;
    private ArrayList<String> extraDocs;

}
