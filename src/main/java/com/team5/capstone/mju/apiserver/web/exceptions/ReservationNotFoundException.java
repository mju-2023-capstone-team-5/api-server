package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class ReservationNotFoundException extends CustomException {
    public ReservationNotFoundException(Integer id) {
        super("예약 내역을 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }

    public ReservationNotFoundException(Long id) {
        super("예약 내역을 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
}
