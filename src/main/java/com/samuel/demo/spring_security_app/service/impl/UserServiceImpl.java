package com.samuel.demo.spring_security_app.service.impl;

import com.samuel.demo.spring_security_app.config.JwtService;
import com.samuel.demo.spring_security_app.dto.*;
import com.samuel.demo.spring_security_app.entity.UserEntity;
import com.samuel.demo.spring_security_app.enums.Role;
import com.samuel.demo.spring_security_app.repository.UserRepository;
import com.samuel.demo.spring_security_app.service.EmailService;
import com.samuel.demo.spring_security_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public AuthResponseDto registerUser(RegistrationDto registrationDto) {


        UserEntity user = UserEntity.builder()
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        // send email to recipient
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Account Creation")
                .messageBody("Congratulations Your Account Has been created")
                .build();

        emailService.sendEmailAlert(emailDetails);


        return AuthResponseDto.builder()
                .responseCode("001")
                .responseMessage("Account Created Successfully")
                .registrationInfo(RegistrationInfo.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .build())
                .build();
    }

    @Override
    public LoginResponse loginUser(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),
                        loginRequestDto.getPassword())
        );

        UserEntity user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow();

        var jwtToken = jwtService.generatedToken(user);


        return LoginResponse.builder()
                .responseCode("002")
                .responseMessage("Login Successfully")
                .loginInfo(LoginInfo.builder()
                        .email(user.getEmail())
                        .token(jwtToken)
                        .build())
                .build();
    }
}
