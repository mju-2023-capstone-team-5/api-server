package com.team5.capstone.mju.apiserver.web.entity;

import com.team5.capstone.mju.apiserver.web.dto.RegisterRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, columnDefinition = "int")
    private Long Userid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "kakao_app_uuid")
    private String kakaoAppUuid; // 앱별 연결된 사용자에게 부여 된 id

    @Column(name = "social_login_token")
    private String socialLoginToken;

    @Column(name = "car_number")
    private String carNumber;

    @Column(name = "date_joined")
    private LocalDateTime dateJoined;

    public void addExtraInfoSelf(RegisterRequestDto registerRequestDto) {
        this.name = registerRequestDto.getName();
        this.address = registerRequestDto.getAddress();
        this.carNumber = registerRequestDto.getCarNumber();
        this.phone = registerRequestDto.getPhone();
    }
}