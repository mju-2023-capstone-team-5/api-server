package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_point")
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_id", nullable = false, columnDefinition = "int")
    private Long userPointId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "points")
    private Long points;

    public void use(Long amount) {
        this.points -= amount;
    }

    public void earn(Long amount) {
        this.points += amount;
    }

}