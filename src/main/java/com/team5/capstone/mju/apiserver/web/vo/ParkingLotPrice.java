package com.team5.capstone.mju.apiserver.web.vo;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotPrice {
    private int minimum;
    private int surcharge;
}
