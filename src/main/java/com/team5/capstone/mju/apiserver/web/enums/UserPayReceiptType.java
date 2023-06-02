package com.team5.capstone.mju.apiserver.web.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserPayReceiptType {

    POINT_EARN("포인트 추가"),
    POINT_USED("포인트 사용");

    @Getter
    private String type;
}
