package com.samuel.demo.spring_security_app.service;

import com.samuel.demo.spring_security_app.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
