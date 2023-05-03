package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "parking_avaliable_day")
public class ParkingAvaliableDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_avaliable_day_id", nullable = false)
    private Long parkingAvailableDayId;

    @Column(name = "parking_lot_id")
    private Integer parkingLotId;

    @Lob
    @Column(name = "available_day")
    private String availableDay;

}