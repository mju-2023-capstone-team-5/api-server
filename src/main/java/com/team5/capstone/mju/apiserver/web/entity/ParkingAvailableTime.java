package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "parking_available_time")
public class ParkingAvailableTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_available_time_id", nullable = false, columnDefinition = "int")
    private Long parkingAvailableTimeId;

    @Column(name = "parking_lot_id", nullable = false)
    private Integer parkingLotId;

    @Column(name = "start_time")
    private Integer startTime;

    @Column(name = "start_minute", nullable = false)
    private Integer startMinute;

    @Column(name = "end_time", nullable = false)
    private Integer endTime;

    @Column(name = "end_minute", nullable = false)
    private Integer endMinute;

}