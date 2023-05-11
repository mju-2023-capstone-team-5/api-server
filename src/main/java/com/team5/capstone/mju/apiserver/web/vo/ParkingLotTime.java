package com.team5.capstone.mju.apiserver.web.vo;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotTime {
    private int startTime;
    private int startMinute;
    private int endTime;
    private int endMinute;
}
