package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.team5.capstone.mju.apiserver.web.entity.*;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotPriceType;
import com.team5.capstone.mju.apiserver.web.vo.ParkingLotPrice;
import com.team5.capstone.mju.apiserver.web.vo.ParkingLotTime;
import lombok.*;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private String phoneNumber;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int totalSpace;
    private int remainingSpace;
    private int ownerId;

    @JsonIgnore
    private String imageUrl;

    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private float ratingAvg;

    @JsonProperty(value = "type")
    private String[] type;

    @JsonProperty(value = "availableDay")
    private String[] availableDay;

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
        parkingLot.setFreeInformation(freeInformation);

        return parkingLot;
    }

    public ParkingLot parseToParkingLot() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLot.setLatitude(latitude);
        parkingLot.setLongitude(longitude);
        parkingLot.setTotalSpace(totalSpace);
        parkingLot.setRemainingSpace(remainingSpace);
        parkingLot.setFreeInformation(freeInformation);
        parkingLot.setCarType(String.join(",", type));
        parkingLot.setAvailableDay(String.join(",", availableDay));

        return parkingLot;
    }

    public ParkingLotOwner parseToParkingLotOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        owner.setOwnerId(ownerId);
        owner.setInquiryPhoneNumber(phoneNumber);
        return owner;
    }

    public Optional<ParkingPrice> parseMonthlyToParkingLotPrice() {
        if (monthPrice == null) {
            return Optional.empty();
        }

        ParkingPrice price = new ParkingPrice();
        price.setDateType(ParkingLotPriceType.MONTH.getType());
        price.setMinimum(monthPrice.getMinimum());
        price.setSurcharge(monthPrice.getSurcharge());

        return Optional.of(price);
    }

    public Optional<ParkingPrice> parseHourlyToParkingLotPrice() {
        if (hourPrice == null) {
            return Optional.empty();
        }

        ParkingPrice price = new ParkingPrice();
        price.setDateType(ParkingLotPriceType.HOUR.getType());
        price.setMinimum(hourPrice.getMinimum());
        price.setSurcharge(hourPrice.getSurcharge());

        return Optional.of(price);
    }

    public List<ParkingAvailableTime> parseToParkingAvailableTimeList() {
        List<ParkingAvailableTime> parkingAvailableTimeList = new ArrayList<>();
        Arrays.stream(time)
                .forEach(t -> {
                    ParkingAvailableTime availableTime = new ParkingAvailableTime();
                    availableTime.setStartTime(t.getStartTime());
                    availableTime.setEndTime(t.getEndTime());
                    availableTime.setStartMinute(t.getStartMinute());
                    availableTime.setEndMinute(t.getEndMinute());

                    parkingAvailableTimeList.add(availableTime);
                });
        return parkingAvailableTimeList;
    }

    public static ParkingLotDto of(ParkingLot parkingLot, ParkingLotOwner owner, Optional<Rating> rating, List<ParkingAvailableTime> availableList, List<ParkingPrice> priceList) {

        Optional<ParkingPrice> month = priceList.stream().filter(price -> price.getDateType().equals(ParkingLotPriceType.MONTH.getType()))
                .findFirst();

        ParkingLotPrice monthPrice = null;
        if (month.isPresent()) {
            monthPrice = new ParkingLotPrice();
            monthPrice.setMinimum(month.get().getMinimum());
            monthPrice.setSurcharge(month.get().getSurcharge());
        }


        Float ratingAvg = 0.0f;
        if(rating.isPresent()) {
            ratingAvg = rating.get().getRatingAvg();
        }



        Optional<ParkingPrice> hour = priceList.stream().filter(price -> price.getDateType().equals(ParkingLotPriceType.HOUR.getType()))
                .findFirst();

        ParkingLotPrice hourPrice = null;
        if (hour.isPresent()) {
            hourPrice = new ParkingLotPrice();
            hourPrice.setMinimum(hour.get().getMinimum());
            hourPrice.setSurcharge(hour.get().getSurcharge());
        }

        List<ParkingLotTime> times = new ArrayList<>();
        availableList.forEach(parkingAvailableTime -> {
            ParkingLotTime time1 = new ParkingLotTime();
            time1.setStartTime(parkingAvailableTime.getStartTime());
            time1.setEndTime(parkingAvailableTime.getEndTime());
            time1.setStartMinute(parkingAvailableTime.getStartMinute());
            time1.setEndMinute(parkingAvailableTime.getEndMinute());
            times.add(time1);
        });

        return ParkingLotDto.builder()
                .id(Math.toIntExact(parkingLot.getParkingLotId()))
                .name(parkingLot.getName())
                .address(parkingLot.getAddress())
                .freeInformation(parkingLot.getFreeInformation())
                .latitude(parkingLot.getLatitude())
                .longitude(parkingLot.getLongitude())
                .totalSpace(parkingLot.getTotalSpace())
                .imageUrl(parkingLot.getImageUrl())
                .remainingSpace(parkingLot.getRemainingSpace())
                .ownerId(owner.getOwnerId())
                .phoneNumber(owner.getInquiryPhoneNumber())
                .availableDay(parkingLot.getAvailableDay().split(","))
                .type(parkingLot.getCarType().split(","))
                .monthPrice(monthPrice)
                .hourPrice(hourPrice)
                .ratingAvg(ratingAvg)
                .time(times.toArray(new ParkingLotTime[0]))
                .build();
    }
}
