package com.example.userservice2.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {
    @NotNull(message = "email cannot be null")
    @Size(min = 2, message = "email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min = 2, message = "password must be equal or greater than 8 characters and less than 16 characters")
    private String password;

}
