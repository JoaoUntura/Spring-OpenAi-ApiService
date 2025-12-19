package dev.joaountura.aihelper.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateUserDTO {

    @Email
    private String email;

    @NotEmpty
    private String password;


}
