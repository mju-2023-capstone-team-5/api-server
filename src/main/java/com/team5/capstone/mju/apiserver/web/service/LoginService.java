package com.team5.capstone.mju.apiserver.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.capstone.mju.apiserver.web.dto.LoginRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.LoginResponseDto;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LoginService {

    @Value("${extends.app-id}")
    private String appId;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidateToken(LoginRequestDto loginRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loginRequestDto.getAccessToken());

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        JsonNode node = null;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "https://kapi.kakao.com/v1/user/access_token_info",
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            );
            node = objectMapper.readTree(responseEntity.getBody());

            if (node.get("app_id").asText().equals(appId)) return true;
        } catch (HttpClientErrorException | JsonProcessingException e) { // json 해석 오류 / expired 되거나 잘못 된 token일 시(401)
            return false;
        }
        return false;
    }

    public boolean createOrFound() {
        return true;
    }

    public LoginResponseDto tryLogin(LoginRequestDto loginRequestDto) {

        boolean isValidate = isValidateToken(loginRequestDto);
        log.info("validate: " + isValidate);

        return LoginResponseDto.builder()
                .jwt(null)
                .userId(null)
                .isNewUser(true)
                .build();
    }
}
