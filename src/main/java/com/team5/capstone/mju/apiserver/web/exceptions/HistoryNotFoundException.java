package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class HistoryNotFoundException extends CustomException {
    public HistoryNotFoundException(Integer id) {
        super("예약 내역을 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
    public HistoryNotFoundException(Long id) {
        super("예약 내역을 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
}
