package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
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
    @JsonIgnore
    private String status;
    private LocalDateTime openTime;
    private String freeInformation;

    public ParkingLot toEntity() {

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLot.setLatitude(latitude);
        parkingLot.setLongitude(longitude);
        parkingLot.setTotalSpace(Math.toIntExact(totalSpace));
        parkingLot.setRemainingSpace(Math.toIntExact(remainingSpace));
        parkingLot.setOpenTime(openTime);
//        parkingLot.setStatus(status);
        parkingLot.setFreeInformation(freeInformation);

        return parkingLot;
    }
}
