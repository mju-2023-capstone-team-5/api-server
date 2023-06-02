package com.team5.capstone.mju.apiserver.web.exceptions;


import org.apache.http.HttpStatus;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(Integer id) {
        super("사용자를 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }

    public UserNotFoundException(Long id) {
        super("사용자를 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
}
