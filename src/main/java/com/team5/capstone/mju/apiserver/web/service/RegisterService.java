package com.team5.capstone.mju.apiserver.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.capstone.mju.apiserver.web.dto.AddInfoRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.AddNewUserFromRegisterRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.OAuth2ResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import com.team5.capstone.mju.apiserver.web.util.JwtUtil;
import com.team5.capstone.mju.apiserver.web.vo.JwtPayloadParam;
import com.team5.capstone.mju.apiserver.web.vo.KakaoProviderAuthenticationResponseVo;
import com.team5.capstone.mju.apiserver.web.vo.KakaoProviderAuthorizationParamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
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

    private final UserRepository userRepository;

    @Autowired
    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public KakaoProviderAuthenticationResponseVo getOauth2Token(KakaoProviderAuthorizationParamVo kakaoCode) {
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

        return authenticationResponseVo.orElseThrow(() -> new IllegalStateException("인증 과정에 오류가 발생하였습니다"));
    }

    public AddNewUserFromRegisterRequestDto getKakaoMemberProfileInfo(KakaoProviderAuthenticationResponseVo authenticationResponseVo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + authenticationResponseVo.getAccessToken());

        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        try {
            JsonNode root = objectMapper.readTree(responseEntity.getBody());
            Long kakaoAppId = root.get("id").asLong();
            String email = root.get("kakao_account").get("email").asText();

            AddNewUserFromRegisterRequestDto addNewUserFromRegisterDto = AddNewUserFromRegisterRequestDto.builder()
                    .kakaoAppId(kakaoAppId)
                    .email(email)
                    .socialLoginRefreshToken(authenticationResponseVo.getRefreshToken())
                    .build();

            return addNewUserFromRegisterDto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Long saveNewUser(AddNewUserFromRegisterRequestDto addNewUserFromRegisterDto) {
        User newUser = userRepository.save(
                User.builder()
                        .Userid(null)
                        .email(addNewUserFromRegisterDto.getEmail())
                        .kakaoAppId(addNewUserFromRegisterDto.getKakaoAppId())
                        .socialLoginRefreshToken(addNewUserFromRegisterDto.getSocialLoginRefreshToken())
                        .dateJoined(LocalDateTime.now())
                        .phone("")
                        .address("")
                        .carNumber("")
                        .name("")
                        .build()
        );
        return newUser.getUserid();
    }

    public OAuth2ResponseDto getJWTToken(AddNewUserFromRegisterRequestDto addNewUserFromRegisterRequestDto) {

        Optional<User> found = userRepository.findByKakaoAppId(addNewUserFromRegisterRequestDto.getKakaoAppId());

        OAuth2ResponseDto responseDto;
        boolean isNewUser = true;
        Long id;

        if (found.isEmpty()) {
            id = saveNewUser(addNewUserFromRegisterRequestDto);
        }
        else {
            id = found.get().getUserid();
            isNewUser = false;
        }

        responseDto = OAuth2ResponseDto.builder()
                .jwt(jwtUtil.createJwt(new JwtPayloadParam(id)))
                .isNewUser(isNewUser)
                .build();

        return responseDto;
    }

    public String addNewInfo(AddInfoRequestDto addInfoRequestDto) {
        // TODO: 사용자를 jwt 토큰 생성 시에 생성하므로 수정 필요
//        userRepository.save(new User(
//                null,
//                addInfoRequestDto.getName(),
//                addInfoRequestDto.getEmail(),
//                addInfoRequestDto.getPhone(),
//                addInfoRequestDto.getAddress(),
//                1234L,
//                "token",
//                addInfoRequestDto.getCarNumber(),
//                LocalDateTime.now()));
//        Optional<User> byId = userRepository.findById(1L);
        return "success";
    }
}
