package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.service.RegisterService;
import com.team5.capstone.mju.apiserver.web.vo.KakaoProviderAuthorizationCode;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class RegisterController {
    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/oauth2/kakao")
    public ResponseEntity<String> authorization(@PathParam("code") KakaoProviderAuthorizationCode kakaoProviderAuthorizationCode) {
        LoggerFactory.getLogger(this.getClass())
                .info("code: " + kakaoProviderAuthorizationCode.getCode());
        return ResponseEntity.ok(registerService.getOauth2Token(kakaoProviderAuthorizationCode));
//        return ResponseEntity.ok("success");
    }


}
