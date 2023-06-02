package com.team5.capstone.mju.apiserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {
    private String jwt;
    private Long userId;
    private boolean isNewUser;
}
