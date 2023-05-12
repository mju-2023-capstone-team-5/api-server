package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.User;
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

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .carNumber(user.getCarNumber())
                .dateJoined(user.getDateJoined())
                .build();
    }
}
