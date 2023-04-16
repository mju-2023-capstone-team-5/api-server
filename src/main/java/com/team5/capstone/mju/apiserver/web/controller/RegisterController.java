package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.RegisterRequestDto;
import com.team5.capstone.mju.apiserver.web.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<String> addNewInfo(@RequestBody RegisterRequestDto registerRequestDto) {
        registerService.registerNewUser(registerRequestDto);
        return ResponseEntity.ok("success");
    }
}
