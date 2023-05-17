package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class KakaoSocialLoginUserNotFoundException extends CustomException {
    public KakaoSocialLoginUserNotFoundException(Integer uuid) {
        super("해당하는 카카오 유저를 찾을 수 없습니다. uuid: " + uuid);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
    public KakaoSocialLoginUserNotFoundException(Long uuid) {
        super("해당하는 카카오 유저를 찾을 수 없습니다. uuid: " + uuid);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
}
