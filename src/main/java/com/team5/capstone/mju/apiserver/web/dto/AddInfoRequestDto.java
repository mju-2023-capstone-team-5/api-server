package com.team5.capstone.mju.apiserver.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddInfoRequestDto {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String carNumber;
}
