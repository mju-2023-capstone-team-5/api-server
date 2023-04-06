package com.team5.capstone.mju.apiserver.web.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoProviderAuthorizationCode {
    private String code;
}
