package com.team5.capstone.mju.apiserver.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddFcmTokenRequestDTO {
    private String fcmToken;
}
