package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.LoginRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.LoginResponseDto;
import com.team5.capstone.mju.apiserver.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoginController {
    private final LoginService loginService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/v1/login/token")
    public ResponseEntity<LoginResponseDto> validationAndGenerateUserAndJwt(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(loginService.tryLogin(loginRequestDto));
    }
}
