package com.team5.capstone.mju.apiserver.web.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AvailableDay {
    MON(1, "월"),
    TWE(2, "화"),
    WEN(3, "수"),
    TUR(4, "목"),
    FRI(5, "금"),
    SAT(6, "토"),
    SUN(7, "일");

    private int value;
    private String kor;
}
