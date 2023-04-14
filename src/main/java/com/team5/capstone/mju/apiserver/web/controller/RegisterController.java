package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.AddInfoRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.AddNewUserFromRegisterRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.OAuth2ResponseDto;
import com.team5.capstone.mju.apiserver.web.service.RegisterService;
import com.team5.capstone.mju.apiserver.web.vo.KakaoProviderAuthenticationResponseVo;
import com.team5.capstone.mju.apiserver.web.vo.KakaoProviderAuthorizationParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class RegisterController {
    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/oauth2/kakao")
    public ResponseEntity<OAuth2ResponseDto> authorization(@PathParam("code") KakaoProviderAuthorizationParamVo kakaoProviderAuthorizationCode) {
        KakaoProviderAuthenticationResponseVo oauth2Token = registerService.getOauth2Token(kakaoProviderAuthorizationCode);
        AddNewUserFromRegisterRequestDto addNewUserRequestDto = registerService.getKakaoMemberProfileInfo(oauth2Token);

        OAuth2ResponseDto oAuth2ResponseDto = registerService.getJWTToken(addNewUserRequestDto);

        return ResponseEntity.ok(oAuth2ResponseDto);
        }

    @PostMapping(value = "/api/v1/add-info", produces = "application/json")
    public ResponseEntity<String> addNewUserInfo(@RequestBody AddInfoRequestDto requestDto) {
        return ResponseEntity.ok(registerService.addNewInfo(requestDto));
    }

}
