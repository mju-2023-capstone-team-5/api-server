package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.service.RegisterService;
import com.team5.capstone.mju.apiserver.web.vo.KakaoProviderAuthenticationResponseVo;
import com.team5.capstone.mju.apiserver.web.vo.KakaoProviderAuthorizationParamVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<KakaoProviderAuthenticationResponseVo> authorization(@PathParam("code") KakaoProviderAuthorizationParamVo kakaoProviderAuthorizationCode) {
        return ResponseEntity.ok(registerService.getOauth2Token(kakaoProviderAuthorizationCode));
//        return ResponseEntity.ok("success");
    }


}
