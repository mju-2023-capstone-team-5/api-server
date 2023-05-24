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
    private Integer ratingSum;

    @Column(name = "rating_num")
    private Integer ratingNum;

    @Column(name = "rating_avg")
    private float ratingAvg;

    @Column(name = "comment_summary")
    private String commentSummary;

}
