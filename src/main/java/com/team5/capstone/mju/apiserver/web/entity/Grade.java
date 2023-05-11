package com.team5.capstone.mju.apiserver.web.entity;

import com.team5.capstone.mju.apiserver.web.dto.GradeRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id", nullable = false, columnDefinition = "int")
    private Long gradeId;

    @Column(name = "parking_lot_id")
    private Integer parkingLotId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "rating")
    private Float rating;

    @Lob
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public void updateAllInfoSelf(GradeRequestDto requestDto) {
        this.setParkingLotId(requestDto.getParkingLotId());
        this.setUserId(requestDto.getUserId());
        this.setRating(requestDto.getRating());
        this.setComment(requestDto.getComment());
        this.setTimestamp(requestDto.getTimestamp());
    }
}