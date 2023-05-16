package com.team5.capstone.mju.apiserver.web.dto;

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
}
