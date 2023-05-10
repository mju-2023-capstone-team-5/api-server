package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.vo.ParkingLotPrice;
import com.team5.capstone.mju.apiserver.web.vo.ParkingLotTime;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotDto {

    private String name;
    private String address;
    private String freeInformation;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int totalSpace;
    private int remainingSpace;
    private int ownerId;

    @JsonProperty(value = "type")
    private String[] type;

    @JsonProperty(value = "availableDay")
    private String availableDay;

    @JsonProperty(value = "availableTime")
    private ParkingLotTime[] time;

    @JsonProperty(value = "monthly", required = false)
    private ParkingLotPrice monthPrice;

    @JsonProperty(value = "hourly", required = false)
    private ParkingLotPrice hourPrice;

    public ParkingLot toEntity() {

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLot.setLatitude(latitude);
        parkingLot.setLongitude(longitude);
        parkingLot.setTotalSpace(Math.toIntExact(totalSpace));
        parkingLot.setRemainingSpace(Math.toIntExact(remainingSpace));
//        parkingLot.setOpenTime(openTime);
//        parkingLot.setStatus(status);
        parkingLot.setFreeInformation(freeInformation);

        return parkingLot;
    }
}
