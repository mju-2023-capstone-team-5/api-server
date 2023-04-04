package com.team5.capstone.mju.apiserver.web.entity;

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

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "date_reserved")
    private LocalDateTime dateReserved;

    @Column(name = "status")
    private String status;

}