package com.team5.capstone.mju.apiserver.web.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
public class JwtPayloadParam {
    private String userId;
    private LocalDateTime expiredAt;

    public JwtPayloadParam(long userId) {
        this.userId = String.valueOf(userId);
        this.expiredAt = LocalDateTime.now().plusYears(1);
    }

    public Date expirationDate() {
        return Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
