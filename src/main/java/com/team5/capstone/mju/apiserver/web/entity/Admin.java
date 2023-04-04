package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id", nullable = false, columnDefinition = "int")
    private Long adminId;

    @Column(name = "password")
    private String password;

}
