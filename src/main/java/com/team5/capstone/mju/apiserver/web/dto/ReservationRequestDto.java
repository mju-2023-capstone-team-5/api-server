package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team5.capstone.mju.apiserver.web.entity.History;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import com.team5.capstone.mju.apiserver.web.vo.ReservationInfo;
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
    private int price;

    @JsonProperty(value = "monthlyReservation")
    private ReservationInfo monthly;

    @JsonProperty(value = "hourlyReservation")
    private ReservationInfo hourly;

    public Reservation toEntity(){
        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setParkingLotId(parkingLotId);
//        reservation.setStartTime(startTime);
//        reservation.setEndTime(endTime);
//        reservation.setDateReserved(dateReserved);
//        reservation.setStatus(status);
        return reservation;
    }
}