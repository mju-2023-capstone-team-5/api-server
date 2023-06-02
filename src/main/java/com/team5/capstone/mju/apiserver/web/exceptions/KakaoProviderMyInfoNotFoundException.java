package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class KakaoProviderMyInfoNotFoundException extends CustomException {
    public KakaoProviderMyInfoNotFoundException() {
        super("kakao 내 My 정보를 찾을 수 없습니다.");
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
}
