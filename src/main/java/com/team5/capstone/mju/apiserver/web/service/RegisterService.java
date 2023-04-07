package com.team5.capstone.mju.apiserver.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.capstone.mju.apiserver.web.dto.OAuth2ResponseDto;
import com.team5.capstone.mju.apiserver.web.util.JwtUtil;
import com.team5.capstone.mju.apiserver.web.vo.JwtPayloadParam;
import com.team5.capstone.mju.apiserver.web.vo.KakaoProviderAuthenticationResponseVo;
import com.team5.capstone.mju.apiserver.web.vo.KakaoProviderAuthorizationParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class RegisterService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    public OAuth2ResponseDto getOauth2Token(KakaoProviderAuthorizationParamVo kakaoCode) {
        OAuth2AccessToken accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", clientId);
        map.add("redirect_uri", redirectUri);
        map.add("code", kakaoCode.getCode());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token", request, String.class);

        String responseJson = response.getBody();

        Optional<KakaoProviderAuthenticationResponseVo> authenticationResponseVo;
        try {
            authenticationResponseVo = Optional.ofNullable(objectMapper.readValue(responseJson, KakaoProviderAuthenticationResponseVo.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        OAuth2ResponseDto responseDto = OAuth2ResponseDto.builder()
                .jwt(jwtUtil.createJwt(new JwtPayloadParam(1)))
                .isNewUser(true)
                .build();

        return responseDto;
    }
}
