package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "parking_price")
public class ParkingPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_price_id", nullable = false, columnDefinition = "int")
    private Long parkingPriceId;

    @Column(name = "parking_lot_id", nullable = false)
    private Integer parkingLotId;

    @Column(name = "minimum", nullable = false)
    private Integer minimum;

    @Column(name = "surcharge")
    private Integer surcharge;

    @Column(name = "date_type", columnDefinition = "varchar(16)")
    private String dateType;

    public void updateAllSelf(ParkingPrice price) {
        this.parkingLotId = price.parkingLotId;
        this.minimum = price.minimum;;
        this.surcharge = price.surcharge;
    }
}