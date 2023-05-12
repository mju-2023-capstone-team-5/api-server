package com.team5.capstone.mju.apiserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OwnerResponseDto {
    private String userId;
    private Integer parkingLotId;
    private String inquiryPhoneNumber;
}
