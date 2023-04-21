package com.team5.capstone.mju.apiserver.web.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ParkingLotStatus {
    INIT("init"),
    WAIT("wait"),
    PERMITTED("permitted");

    @Getter
    private String status;
}
