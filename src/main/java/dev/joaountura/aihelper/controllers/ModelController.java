package dev.joaountura.aihelper.controllers;

import dev.joaountura.aihelper.dtos.ApiResponse;
import dev.joaountura.aihelper.dtos.UploadRepositoryDTO;


import dev.joaountura.aihelper.services.ModelServices;
import dev.joaountura.aihelper.websocket.services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("docs_model")
public class ModelController {

    @Autowired
    private ModelServices modelServices;

    @Autowired
    private NotificationsService notificationsService;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> uploadCodeBase(@RequestBody() UploadRepositoryDTO uploadRepositoryDTO, @AuthenticationPrincipal String userId) throws Exception {

        modelServices.sendToGitHubTopic(uploadRepositoryDTO, userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .message("PipeLine Started")
                        .success(true)
                .build());

    }

}
