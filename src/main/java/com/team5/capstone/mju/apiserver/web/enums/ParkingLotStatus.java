package com.team5.capstone.mju.apiserver.web.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ParkingLotStatus {
    WAIT("허가대기"),
    PARKING_AVAILABLE("주차가능"),
    PARKED("주차중");
    @Getter
    private String status;
}
