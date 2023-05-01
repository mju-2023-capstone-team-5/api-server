package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.History;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {

    private int userId;
    private int parkingLotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime dateReserved;
    private String status;
    public Reservation toEntity(){
        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setParkingLotId(parkingLotId);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setDateReserved(dateReserved);
        reservation.setStatus(status);
        return reservation;
    }
}