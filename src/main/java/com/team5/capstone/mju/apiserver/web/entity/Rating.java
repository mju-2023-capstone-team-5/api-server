package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @Column(name = "parking_lot_id", nullable = false, columnDefinition = "int")
    private Integer parkingLotId;

    @Column(name = "rating_sum")
    private float ratingSum;

    @Column(name = "rating_num")
    private Integer ratingNum;

    @Column(name = "rating_avg")
    private float ratingAvg;

    public Rating(Integer parkingLotId, Float rating, int i, Float rating1) {

    }

    public Rating() {

    }
}
