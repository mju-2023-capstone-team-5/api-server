package com.team5.capstone.mju.apiserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserResponseDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String carNumber;
    private LocalDateTime dateJoined;
}
