package com.team5.capstone.mju.apiserver.web.vo;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationInfo {
    private LocalDateTime date;
    private int[] duration;
}
