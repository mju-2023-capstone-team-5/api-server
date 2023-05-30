package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team5.capstone.mju.apiserver.web.entity.History;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotPriceType;
import com.team5.capstone.mju.apiserver.web.exceptions.TimeNotFoundException;
import com.team5.capstone.mju.apiserver.web.vo.ReservationInfo;
import lombok.*;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    public Reservation toEntity() throws EntityNotFoundException {
        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setParkingLotId(parkingLotId);
        reservation.setPrice(price);
        if (monthly == null && hourly == null) throw new TimeNotFoundException();
        else if (monthly != null) {
            reservation.setDateType(ParkingLotPriceType.MONTH.getType());
            reservation.setDate(monthly.getDate());
            reservation.setDuration(String.valueOf(monthly.getDuration()[0]));
        }
        else if (hourly != null) {
            reservation.setDateType(ParkingLotPriceType.HOUR.getType());
            reservation.setDate(hourly.getDate());
            reservation.setDuration(Arrays.stream(hourly.getDuration()).mapToObj(String::valueOf).collect(Collectors.joining(",")));
        }
        reservation.setIsReturned(false);
        reservation.setIsReviewWritten(false);
        return reservation;
    }
}