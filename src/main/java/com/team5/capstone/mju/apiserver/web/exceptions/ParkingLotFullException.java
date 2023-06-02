package com.team5.capstone.mju.apiserver.web.exceptions;


import org.apache.http.HttpStatus;

public class ParkingLotFullException extends CustomException {
    public ParkingLotFullException(Integer id) {
        super("해당하는 주차장에 남은 주차공간이 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_BAD_REQUEST;
    }

    public ParkingLotFullException(Long id) {
        super("해당하는 주차장에 남은 주차공간이 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_BAD_REQUEST;
    }
}
