package com.team5.capstone.mju.apiserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequestDto {
    private Long userId;
    private String name;
    private String address;
    private String phone;
    private String carNumber;
}
