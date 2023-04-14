package com.team5.capstone.mju.apiserver.web.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "kakao_app_id", columnDefinition = "bigint")
    private Long kakaoAppId;

    @Column(name = "social_login_refresh_token")
    private String socialLoginRefreshToken;

    @Column(name = "car_number")
    private String carNumber;

    @Column(name = "date_joined")
    private LocalDateTime dateJoined;


}