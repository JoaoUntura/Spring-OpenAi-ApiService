package dev.joaountura.aihelper.controllers;

import dev.joaountura.aihelper.dtos.ApiResponse;
import dev.joaountura.aihelper.dtos.LoginDTO;
import dev.joaountura.aihelper.services.LoginServices;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginServices loginServices;


    @PostMapping
    private ResponseEntity<ApiResponse<?>> loginUser(@RequestBody LoginDTO loginDTO, HttpServletResponse httpServletResponse){

        String token = loginServices.logIn(loginDTO);

        ResponseCookie cookie = ResponseCookie.from("auth", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .build();

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Ok", null));

    }

}
