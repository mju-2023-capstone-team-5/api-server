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
public class ReservationResponseDto {

    private  int reservationId;
    private int userId;
    private int parkingLotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime dateReserved;
    private String status;

    public Reservation ToEntity(){

        Reservation reservation = new Reservation();
        reservation.setReservationId((long) reservationId);
        reservation.setUserId(userId);
        reservation.setParkingLotId(parkingLotId);
//        reservation.setStartTime(startTime);
//        reservation.setEndTime(endTime);
//        reservation.setDateReserved(dateReserved);
//        reservation.setStatus(status);

        return reservation;
    }
    public static ReservationResponseDto of(Reservation reservation) {
        return ReservationResponseDto.builder()
                .reservationId(Math.toIntExact(reservation.getReservationId()))
                .userId(reservation.getUserId())
                .parkingLotId(reservation.getParkingLotId())
//                .startTime(reservation.getStartTime())
//                .endTime(reservation.getEndTime())
//                .dateReserved(reservation.getDateReserved())
//                .status(reservation.getStatus())
                .build();
    }

}
