package com.team5.capstone.mju.apiserver.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private String accessToken;
}
