package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class UploadImageCannotConvertException extends CustomException {
    public UploadImageCannotConvertException() {
        super("이미지 파일이 잘못되었거나 존재하지 않습니다.");
        this.httpStatus = HttpStatus.SC_BAD_REQUEST;
    }
}
