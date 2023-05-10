package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team5.capstone.mju.apiserver.web.entity.ParkingAvailableTime;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLotOwner;
import com.team5.capstone.mju.apiserver.web.entity.ParkingPrice;
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
        price.setDateType("월간결제");
        price.setMinimum(monthPrice.getMinimum());
        price.setSurcharge(monthPrice.getSurcharge());

        return Optional.of(price);
    }

    public Optional<ParkingPrice> parseHourlyToParkingLotPrice() {
        if (hourPrice == null) {
            return Optional.empty();
        }

        ParkingPrice price = new ParkingPrice();
        price.setDateType("시간결제");
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
}
