package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.Grade;
import com.team5.capstone.mju.apiserver.web.entity.History;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeResponseDto {
    private int gradeId;
    private int parkingLotId;
    private int userId;
    private Float rating;
    private String comment;
    private LocalDateTime timestamp;

    public Grade ToEntity(){

        Grade grade = new Grade();
        grade.setGradeId((long) gradeId);
        grade.setParkingLotId(parkingLotId);
        grade.setUserId(userId);
        grade.setRating(rating);
        grade.setComment(comment);
        grade.setTimestamp(timestamp);


        return grade;
    }

    public static GradeResponseDto of(Grade grade) {
        return GradeResponseDto.builder()
                .gradeId(Math.toIntExact(grade.getGradeId()))
                .parkingLotId(grade.getParkingLotId())
                .userId(grade.getUserId())
                .rating(grade.getRating())
                .comment(grade.getComment())
                .timestamp(grade.getTimestamp())
                .build();
    }
}
