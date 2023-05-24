package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.History;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryResponseDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String parkingLotName;

    public static HistoryResponseDto of(History history, ParkingLot parkingLot) {
        return HistoryResponseDto.builder()
                .startTime(history.getStartTime())
                .endTime(history.getEndTime())
                .parkingLotName(parkingLot.getName())
                .build();
    }
}
