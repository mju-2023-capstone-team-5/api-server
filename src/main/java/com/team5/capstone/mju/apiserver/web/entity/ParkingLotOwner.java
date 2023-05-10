package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "parking_lot_owner")
public class ParkingLotOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_lot_owner_id", nullable = false, columnDefinition = "int")
    private Long parkingLotOwnerId;

    @Column(name = "parking_lot_id", nullable = false)
    private Integer parkingLotId;

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @Column(name = "inquiry_phone_number", nullable = false, columnDefinition = "varchar(16)")
    private String inquiryPhoneNumber;
}