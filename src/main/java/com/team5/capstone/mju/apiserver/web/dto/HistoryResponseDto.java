package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.History;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryResponseDto {
    private Long reservationId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String parkingLotName;
    private boolean isReviewWritten;

    public static HistoryResponseDto of(History history, ParkingLot parkingLot, Reservation reservation) {
        return HistoryResponseDto.builder()
                .reservationId(Long.valueOf(history.getReservationId()))
                .startTime(history.getStartTime())
                .endTime(history.getEndTime())
                .parkingLotName(parkingLot.getName())
                .isReviewWritten(reservation.getIsReviewWritten())
                .build();
    }
}
