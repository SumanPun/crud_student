package com.example.crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NotEmpty(message = "Please enter valid name.")
    private String name;
    @Email
    @NotEmpty(message = "Please enter valid email.")
    private String email;
    @NotEmpty(message = "Please enter valid password.")
    private String password;
}
