package com.team5.capstone.mju.apiserver.web.entity;

import com.team5.capstone.mju.apiserver.web.dto.ParkingLotDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestOldDto;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_lot_id", nullable = false, columnDefinition = "int")
    private Long parkingLotId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @Column(name = "free_information")
    private String freeInformation;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "total_space", columnDefinition = "int")
    private Integer totalSpace;

    @Column(name = "remaining_space")
    private Integer remainingSpace;

    @Column(name = "owner_id")
    private Integer ownerId;

    @Lob
    @Column(name = "car_type", columnDefinition = "text")
    private String carType;

    @Lob
    @Column(name = "available_day", columnDefinition = "text")
    private String availableDay;

    public void updateAllInfoSelf(ParkingLotRequestOldDto requestDto) {

        this.setName(requestDto.getName());
        this.setAddress(requestDto.getAddress());
        this.setLatitude(requestDto.getLatitude());
        this.setLongitude(requestDto.getLongitude());
        this.setTotalSpace(requestDto.getTotalSpace());
        this.setRemainingSpace(requestDto.getRemainingSpace());
        this.setStatus(requestDto.getStatus());
        this.setFreeInformation(requestDto.getFreeInformation());
    }

    public void updateAllInfoSelf(ParkingLot parkingLot) {
        this.setName(parkingLot.getName());
        this.setAddress(parkingLot.getAddress());
        this.setLatitude(parkingLot.getLatitude());
        this.setLongitude(parkingLot.getLongitude());
        this.setTotalSpace(parkingLot.getTotalSpace());
        this.setRemainingSpace(parkingLot.getRemainingSpace());
        this.setFreeInformation(parkingLot.getFreeInformation());
        this.setCarType(parkingLot.getCarType());
        this.setAvailableDay(parkingLot.getAvailableDay());
        this.setOwnerId(parkingLot.getOwnerId());
    }

    public void updateStatusToParkingAvailableSelf() {
        if (this.status.equals(ParkingLotStatus.WAIT.getStatus())) this.status = ParkingLotStatus.PARKING_AVAILABLE.getStatus();
    }

    public void useSpace() {
        this.remainingSpace--;
    }

    public void returnSpace() {
        this.remainingSpace++;
    }
}