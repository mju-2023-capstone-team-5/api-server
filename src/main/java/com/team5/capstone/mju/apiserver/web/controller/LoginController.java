package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.LoginRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.LoginResponseDto;
import com.team5.capstone.mju.apiserver.web.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1") // API 요청 URL의 앞에 오는 Prefix 설정
@Tag(name = "로그인 Controller", description = "로그인 관련 API 요청 Controller")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(summary = "로그인 API", description = "액세스 토큰을 받아 로그인을 진행하고 로그인 성공 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인에 성공"),
                    @ApiResponse(responseCode = "401", description = "토큰이 올바르지 않아 로그인에 실패"),
                    @ApiResponse(responseCode = "404", description = "카카오에서 조회한 My Info 정보가 존재하지 않아 로그인에 실패")
            }
    )
    @PostMapping("/login/token")
    public ResponseEntity<LoginResponseDto> validationAndGenerateUserAndJwt(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(loginService.tryLogin(loginRequestDto));
    }
}
