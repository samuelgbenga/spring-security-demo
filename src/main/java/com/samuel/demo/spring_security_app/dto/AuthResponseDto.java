package com.samuel.demo.spring_security_app.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {

    private String responseCode;

    private String responseMessage;

    private RegistrationInfo registrationInfo; // response for us the developer (prolly for postman)
}
