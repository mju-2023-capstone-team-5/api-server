package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, columnDefinition = "int")
    private Long Userid;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "social_login_type")
    private String socialLoginType;

    @Column(name = "social_login_token")
    private String socialLoginToken;

    @Column(name = "car_number")
    private String carNumber;

    @Column(name = "date_joined")
    private LocalDateTime dateJoined;


}