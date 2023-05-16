package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.entity.UserPayReceipt;
import com.team5.capstone.mju.apiserver.web.entity.UserPoint;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserPointReceiptResponseDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String carNumber;
    private LocalDateTime dateJoined;
    private Long points;
    private Integer receiptId;

    public static UserPointReceiptResponseDto of(User user, UserPoint userPoint, UserPayReceipt receipt) {
        return UserPointReceiptResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .carNumber(user.getCarNumber())
                .dateJoined(user.getDateJoined())
                .points(userPoint.getPoints())
                .receiptId(Math.toIntExact(receipt.getUserPayReceiptId()))
                .build();
    }
}
