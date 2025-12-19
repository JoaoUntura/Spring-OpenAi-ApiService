package dev.joaountura.aihelper.services;

import dev.joaountura.aihelper.dtos.CreateUserDTO;
import dev.joaountura.aihelper.dtos.LoginDTO;
import dev.joaountura.aihelper.models.UserApp;
import dev.joaountura.aihelper.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserApp createUser(CreateUserDTO createUserDTO){

        UserApp userApp = UserApp.builder()
                .email(createUserDTO.getEmail())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .build();

        return userRepository.save(userApp);

    }

    public UserApp findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


}
