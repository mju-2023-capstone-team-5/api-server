package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.entity.UserPoint;
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
    private Long points;

    public static UserResponseDto of(User user, UserPoint userPoint) {
        return UserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .carNumber(user.getCarNumber())
                .dateJoined(user.getDateJoined())
                .points(userPoint.getPoints())
                .build();
    }
}
