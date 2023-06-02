package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class IllegalImageFormatException extends CustomException {

    public IllegalImageFormatException() {
        super("png, jpg/jpeg를 제외한 확장자는 허용되지 않습니다.");
        this.httpStatus = HttpStatus.SC_BAD_REQUEST;
    }
}
