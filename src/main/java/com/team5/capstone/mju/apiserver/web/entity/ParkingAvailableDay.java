package com.team5.capstone.mju.apiserver.web.entity;

import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "parking_available_day")
public class ParkingAvailableDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_available_day_id", nullable = false, columnDefinition = "int")
    private Long parkingAvailableDayId;

    @Column(name = "parking_lot_id")
    private Integer parkingLotId;

    @Lob
    @Column(name = "available_day", columnDefinition = "TEXT")
    private String availableDay;

    public void updateAvailableDay(ParkingLotRequestDto parkingLotRequestDto) {
        this.availableDay = parkingLotRequestDto.getAvailableDay();
    }

}