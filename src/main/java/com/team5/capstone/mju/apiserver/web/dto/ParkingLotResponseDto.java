package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ParkingLotResponseDto {

    private String name;
    private String address;
    private String freeInformation;
    private String phoneNumber;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int totalSpace;
    private int remainingSpace;
    private int ownerId;

    // ParkingLotDto에서는 jsonIgnore 속성으로 요청/응답에서 보이지 않지만
    // 응답 객체로 반환할 때 of 함수에서 대입한 id 값을 @JsonProperty로 공개하여 응답 데이터에서 공개
    @JsonProperty(value = "id")
    private Integer id;

    // ParkingLotDto에서는 jsonIgnore 속성으로 요청/응답에서 보이지 않지만
    // 응답 객체로 반환할 때 of 함수에서 대입한 id 값을 @JsonProperty로 공개하여 응답 데이터에서 공개
    @JsonProperty(value = "reviewSummary")
    private String reviewSummary;

    @JsonProperty(value = "imageUrl")
    private String imageUrl;

    @JsonProperty(value = "ratingAvg")
    private float ratingAvg;

    @JsonProperty(value = "ratingNum")
    private Integer ratingNum;

    @JsonProperty(value = "type")
    private String[] type;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "availableDay")
    private String[] availableDay;

    @JsonProperty(value = "availableTime")
    private ParkingLotTime[] time;

    @JsonProperty(value = "monthly", required = false)
    private ParkingLotPrice monthPrice;

    @JsonProperty(value = "hourly", required = false)
    private ParkingLotPrice hourPrice;

    public static ParkingLotResponseDto of(ParkingLotDto parkingLotDto) {

        return ParkingLotResponseDto.builder()
                .id(parkingLotDto.getId()) // 응답 데이터로 추가
                .name(parkingLotDto.getName())
                .address(parkingLotDto.getAddress())
                .freeInformation(parkingLotDto.getFreeInformation())
                .latitude(parkingLotDto.getLatitude())
                .longitude(parkingLotDto.getLongitude())
                .totalSpace(parkingLotDto.getTotalSpace())
                .imageUrl(parkingLotDto.getImageUrl())
                .remainingSpace(parkingLotDto.getRemainingSpace())
                .ownerId(parkingLotDto.getOwnerId())
                .phoneNumber(parkingLotDto.getPhoneNumber())
                .availableDay(parkingLotDto.getAvailableDay())
                .type(parkingLotDto.getType())
                .monthPrice(parkingLotDto.getMonthPrice())
                .hourPrice(parkingLotDto.getHourPrice())
                .time(parkingLotDto.getTime())
                .ratingAvg(parkingLotDto.getRatingAvg())
                .ratingNum(parkingLotDto.getRatingNum())
                .imageUrl(parkingLotDto.getImageUrl()) // 응답 데이터로 추가
                .reviewSummary(parkingLotDto.getReviewSummary())
                .status(parkingLotDto.getStatus())
                .build();
    }

}
