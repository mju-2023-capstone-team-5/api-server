package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class InvalidateTokenException extends CustomException {
    public InvalidateTokenException(String provider, String token) {
        super(provider + "의 token이 올바르지 않습니다. token: " + token);
        this.httpStatus = HttpStatus.SC_UNAUTHORIZED;
    }
}
