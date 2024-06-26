package com.samuel.demo.spring_security_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EmailDetails {
    private String recipient;

    private String messageBody;

    private String subject;
}
