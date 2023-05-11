package com.team5.capstone.mju.apiserver.web.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ParkingLotPriceType {
    MONTH("월간결제"),
    HOUR("시간결제");
    @Getter
    private String type;
}
