package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.RegisterRequestDto;
import com.team5.capstone.mju.apiserver.web.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1") // API 요청 URL의 앞에 오는 Prefix 설정
@Tag(name = "회원가입 Controller", description = "회원가입 관련 API 요청 Controller")
public class RegisterController {
    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Operation(summary = "회원가입 API", description = "신규 유저의 정보를 받아 유저의 추가 정보를 업데이트하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "추가 정보 업데이트에 성공")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<String> addNewInfo(@RequestBody RegisterRequestDto registerRequestDto) {
        registerService.registerNewUser(registerRequestDto);
        return ResponseEntity.ok("success");
    }
}
