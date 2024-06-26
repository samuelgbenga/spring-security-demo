package com.samuel.demo.spring_security_app.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationInfo {
    private String firstName;

    private String lastName;

    private String email;


}
