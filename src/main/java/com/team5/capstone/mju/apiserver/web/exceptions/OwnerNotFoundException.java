package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class OwnerNotFoundException extends CustomException {
    public OwnerNotFoundException(Integer id) {
        super("주차장 주인을 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }

    public OwnerNotFoundException(Long id) {
        super("주차장 주인을 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
}
