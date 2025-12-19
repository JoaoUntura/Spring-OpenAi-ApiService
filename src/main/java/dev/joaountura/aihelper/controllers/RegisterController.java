package dev.joaountura.aihelper.controllers;

import dev.joaountura.aihelper.dtos.ApiResponse;
import dev.joaountura.aihelper.dtos.CreateUserDTO;
import dev.joaountura.aihelper.models.UserApp;
import dev.joaountura.aihelper.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserServices userServices;

    @PostMapping
    private ResponseEntity<ApiResponse<Object>> createUser(@RequestBody   CreateUserDTO createUserDTO) {

        UserApp userApp = userServices.createUser(createUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse
                .builder()
                .success(true)
                .message("Created")
                .data(userApp)
                .build());

    }

}
