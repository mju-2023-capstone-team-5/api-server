package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class UserPointAlreadyExistException extends CustomException {
    public UserPointAlreadyExistException() {
        super("사용자에 대한 포인트가 이미 존재하여 새로 생성할 수 없습니다.");
        this.httpStatus = HttpStatus.SC_BAD_REQUEST;
    }
}
