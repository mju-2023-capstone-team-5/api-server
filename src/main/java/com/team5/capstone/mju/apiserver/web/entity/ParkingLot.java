package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_lot_id", nullable = false, columnDefinition = "int")
    private Long parkingLotId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @Column(name = "free_information")
    private String freeInformation;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "total_space", columnDefinition = "int")
    private Integer totalSpace;

    @Column(name = "remaining_space")
    private Integer remainingSpace;

    @Column(name = "open_time")
    private LocalDateTime openTime;

}