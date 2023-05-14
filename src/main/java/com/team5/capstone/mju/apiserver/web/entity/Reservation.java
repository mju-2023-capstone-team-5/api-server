package com.team5.capstone.mju.apiserver.web.entity;

import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false, columnDefinition = "int")
    private Long reservationId;

    @Column(name = "user_id", columnDefinition = "int")
    private Integer userId;

    @Column(name = "parking_lot_id")
    private Integer parkingLotId;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "price")
    private Integer price;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "date_type")
    private String dateType;


    public void updateAllInfoSelf(ReservationRequestDto requestDto) {
        this.setUserId(requestDto.getUserId());
        this.setParkingLotId(requestDto.getParkingLotId());
//        this.setStartTime(requestDto.getStartTime());
//        this.setEndTime(requestDto.getEndTime());
//        this.setDateReserved(requestDto.getDateReserved());
//        this.setStatus(requestDto.getStatus());
    }
}