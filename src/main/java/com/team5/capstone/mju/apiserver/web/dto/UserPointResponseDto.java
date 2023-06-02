package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.UserPoint;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPointResponseDto {
    private Integer userPointId;
    private Integer userId;
    private Long points;

    public static UserPointResponseDto of(UserPoint userPoint) {
        return UserPointResponseDto.builder()
                .userPointId(Math.toIntExact(userPoint.getUserPointId()))
                .userId(userPoint.getUserId())
                .points(userPoint.getPoints())
                .build();
    }
}
