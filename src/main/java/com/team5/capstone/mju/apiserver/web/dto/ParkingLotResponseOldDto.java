package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotResponseOldDto {
    private int parkingLotId;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int totalSpace;
    private int remainingSpace;
    private String status;
    private LocalDateTime openTime;
    private String freeInformation;
    private String imageUrl;

    public ParkingLot ToEntity() {

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setParkingLotId((long) parkingLotId);
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLot.setLatitude(latitude);
        parkingLot.setLongitude(longitude);
        parkingLot.setTotalSpace(Math.toIntExact(totalSpace));
        parkingLot.setRemainingSpace(Math.toIntExact(remainingSpace));
//        parkingLot.setOpenTime(openTime);
        parkingLot.setStatus(status);
        parkingLot.setFreeInformation(freeInformation);

        return parkingLot;
    }

    public static ParkingLotResponseOldDto of(ParkingLot parkingLot) {
        return ParkingLotResponseOldDto.builder()
                .parkingLotId(Math.toIntExact(parkingLot.getParkingLotId()))
                .name(parkingLot.getName())
                .address(parkingLot.getAddress())
                .latitude(parkingLot.getLatitude())
                .longitude(parkingLot.getLongitude())
                .totalSpace(parkingLot.getTotalSpace())
                .remainingSpace(parkingLot.getRemainingSpace())
//                .openTime(parkingLot.getOpenTime())
                .status(parkingLot.getStatus())
                .freeInformation(parkingLot.getFreeInformation())
                .imageUrl(parkingLot.getImageUrl())
                .build();
    }
}
