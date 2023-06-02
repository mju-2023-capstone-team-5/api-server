package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class ParkingLotNotFoundException extends CustomException {
    public ParkingLotNotFoundException(Long id) {
        super("주차장을 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }

    public ParkingLotNotFoundException(Integer id) {
        super("주차장을 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
}
