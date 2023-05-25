package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team5.capstone.mju.apiserver.web.entity.History;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotPriceType;
import com.team5.capstone.mju.apiserver.web.vo.ReservationInfo;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDto {

    private int reservationId;
    private int userId;
    private int parkingLotId;
    private int price;

    @JsonProperty(value = "monthlyReservation")
    private ReservationInfo monthly;

    @JsonProperty(value = "hourlyReservation")
    private ReservationInfo hourly;

    public static ReservationResponseDto of(Reservation reservation) {
        ReservationInfo hourly = null;
        ReservationInfo monthly = null;

        if (reservation.getDateType().equals(ParkingLotPriceType.HOUR.getType())) {
            hourly = new ReservationInfo();
            hourly.setDate(reservation.getDate());

            String[] splited = reservation.getDuration().split(",");
            List<Integer> integerList = Arrays.stream(splited).map(d -> Integer.parseInt(d)).collect(Collectors.toList());
            int[] intList = new int[integerList.size()];
            for(int i = 0; i < integerList.size(); i++) intList[i] = integerList.get(i);
            hourly.setDuration(intList);

        }
        else if (reservation.getDateType().equals(ParkingLotPriceType.MONTH.getType())) {
            monthly = new ReservationInfo();
            monthly.setDate(reservation.getDate());
            monthly.setDuration(new int[]{Integer.parseInt(reservation.getDuration())});
        }


        return ReservationResponseDto.builder()
                .reservationId(Math.toIntExact(reservation.getReservationId()))
                .userId(reservation.getUserId())
                .parkingLotId(reservation.getParkingLotId())
                .price(reservation.getPrice())
                .hourly(hourly)
                .monthly(monthly)
                .build();
    }

}
