package com.intuit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Please enter email")
    private String email;

    @NotBlank(message = "Please enter password")
    private String password;
}
