package com.team5.capstone.mju.apiserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDto {
    private String accessToken;
}
