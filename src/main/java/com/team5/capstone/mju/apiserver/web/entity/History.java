package com.team5.capstone.mju.apiserver.web.entity;

import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import com.team5.capstone.mju.apiserver.web.vo.ReservationInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "parking_history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", nullable = false, columnDefinition = "int")
    private Long historyId;

    @Column(name = "user_id", columnDefinition = "int")
    private Integer userId;

    @Column(name = "parking_lot_id", columnDefinition = "int")
    private Integer parkingLotId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "reservation_id")
    private Integer reservationId;

    public static History of(ReservationRequestDto reservationRequestDto, Long reservationId) {
        History history = new History();
        history.setUserId(reservationRequestDto.getUserId());
        history.setParkingLotId(reservationRequestDto.getParkingLotId());
        history.setReservationId(Math.toIntExact(reservationId));
        if (reservationRequestDto.getHourly() != null) {
            ReservationInfo hourly = reservationRequestDto.getHourly();
            history.setStartTime(hourly.getDate());
            history.setEndTime(hourly.getDate().plusHours(hourly.getDuration().length));
        }
        else if (reservationRequestDto.getMonthly() != null) {
            ReservationInfo monthly = reservationRequestDto.getMonthly();
            history.setStartTime(monthly.getDate());
            history.setEndTime(monthly.getDate().plusMonths(monthly.getDuration()[0]));
        }
        return history;
    }

}