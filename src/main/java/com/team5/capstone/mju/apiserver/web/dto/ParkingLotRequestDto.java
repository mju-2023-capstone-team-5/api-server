package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team5.capstone.mju.apiserver.web.entity.ParkingAvailableDay;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLotType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotRequestDto {

    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int totalSpace;
    private int remainingSpace;
    private LocalDateTime openTime;
    private String freeInformation;
    @JsonIgnore
    private String status;

    private String type; // API의 요청 데이터 중 주차 가능 차종 데이터
    private String availableDay; // API의 요청 데이터 중 주차 가능 요일 데이터

    public ParkingLot toParkingLot() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLot.setLatitude(latitude);
        parkingLot.setLongitude(longitude);
        parkingLot.setTotalSpace(Math.toIntExact(totalSpace));
        parkingLot.setRemainingSpace(Math.toIntExact(remainingSpace));
        parkingLot.setOpenTime(openTime);
        parkingLot.setStatus(status);
        parkingLot.setFreeInformation(freeInformation);

        return parkingLot;
    }

    public ParkingAvailableDay toParkingAvailableDay(Long parkingLotId) {
        ParkingAvailableDay availableDay = new ParkingAvailableDay();
        availableDay.setParkingLotId(Math.toIntExact(parkingLotId));
        availableDay.setAvailableDay(this.availableDay);
        return availableDay;
    }

    public ParkingLotType toParkingLotType(Long parkingLotId) {
        ParkingLotType type = new ParkingLotType();
        type.setParkingLotId(Math.toIntExact(parkingLotId));
        type.setType(this.type);
        return type;
    }
}
