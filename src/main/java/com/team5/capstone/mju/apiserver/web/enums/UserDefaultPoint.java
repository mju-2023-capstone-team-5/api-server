package com.team5.capstone.mju.apiserver.web.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserDefaultPoint {
    DEFAULT(0L),
    DEMO(10000L);

    @Getter
    private Long amount;
}
