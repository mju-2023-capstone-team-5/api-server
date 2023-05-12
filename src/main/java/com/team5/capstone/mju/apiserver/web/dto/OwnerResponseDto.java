package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLotOwner;
import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.vo.OwnerUserDefaultInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OwnerResponseDto {
    private Integer userId;
    @JsonProperty(value = "userInfo")
    private OwnerUserDefaultInfo userInfo;
    private Integer parkingLotId;
    @JsonProperty(value = "inquiryPhone")
    private String inquiryPhoneNumber;

    public static OwnerResponseDto of(ParkingLotOwner owner, User user) {
        OwnerUserDefaultInfo userDefaultInfo = OwnerUserDefaultInfo.builder()
                .name(user.getName())
                .userPhone(user.getPhone())
                .email(user.getEmail())
                .build();

        return OwnerResponseDto.builder()
                .userId(owner.getOwnerId())
                .parkingLotId(owner.getParkingLotId())
                .inquiryPhoneNumber(owner.getInquiryPhoneNumber())
                .userInfo(userDefaultInfo)
                .build();
    }
}
