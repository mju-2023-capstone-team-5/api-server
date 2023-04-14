package com.team5.capstone.mju.apiserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddNewUserFromRegisterRequestDto {
    private Long kakaoAppId;
    private String email;
    private String socialLoginRefreshToken;
}
