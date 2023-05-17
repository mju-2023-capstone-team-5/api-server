package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class TimeNotFoundException extends CustomException {
    public TimeNotFoundException() {
        super("예약 시간 정보가 존재하지 않습니다.");
        this.httpStatus = HttpStatus.SC_BAD_REQUEST;
    }
}
