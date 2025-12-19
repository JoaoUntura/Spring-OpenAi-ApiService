package dev.joaountura.aihelper.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String document;

    private String operation_id;

    private String username;

    private String repository;

    private long tokenUsage;

    @ElementCollection
    @CollectionTable(name = "document_extra_docs", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "doc")
    @Builder.Default //
    private List<String> extraDocs = new ArrayList<String>();


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserApp userApp;


}
