package com.samuel.demo.spring_security_app.service;

import com.samuel.demo.spring_security_app.dto.AuthResponseDto;
import com.samuel.demo.spring_security_app.dto.LoginRequestDto;
import com.samuel.demo.spring_security_app.dto.LoginResponse;
import com.samuel.demo.spring_security_app.dto.RegistrationDto;

public interface UserService {

    AuthResponseDto registerUser(RegistrationDto registrationDto);


    LoginResponse loginUser(LoginRequestDto loginRequestDto);
}
