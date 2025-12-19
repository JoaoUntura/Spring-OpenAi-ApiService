package dev.joaountura.aihelper.services;

import dev.joaountura.aihelper.dtos.LoginDTO;
import dev.joaountura.aihelper.models.UserApp;
import dev.joaountura.aihelper.security.services.JWTServices;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTServices jwtServices;

    @Autowired
    private UserServices userServices;

    public String logIn(LoginDTO loginDTO){
        this.authenticateUser(loginDTO);

        UserApp userApp = userServices.findUserByEmail(loginDTO.getEmail());

        return jwtServices.encodeToken(userApp);

    }

    public void authenticateUser(LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        authenticationManager.authenticate(auth);

    }


}
