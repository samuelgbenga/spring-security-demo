package com.samuel.demo.spring_security_app.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDto {
    // to handle user request

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
