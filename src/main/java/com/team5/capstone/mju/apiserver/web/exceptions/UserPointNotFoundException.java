package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class UserPointNotFoundException extends CustomException {
    public UserPointNotFoundException(Integer id) {
        super("해당하는 유저의 포인트가 존재하지 않습니다. userId: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }

    public UserPointNotFoundException(Long id) {
        super("해당하는 유저의 포인트가 존재하지 않습니다. userId: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
}
