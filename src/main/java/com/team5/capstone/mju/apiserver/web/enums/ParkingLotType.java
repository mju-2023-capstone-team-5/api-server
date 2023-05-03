package com.team5.capstone.mju.apiserver.web.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ParkingLotType {

    LIGHT("경차"),
    COMPACT("소형"),
    MID("중형"),
    LARGE("대형"),
    RV("RV"),
    EV("EV"),
    TRUCK("화물");

    @Getter
    private String type;
}
