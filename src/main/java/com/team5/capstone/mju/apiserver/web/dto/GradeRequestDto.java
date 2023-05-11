package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.Grade;
import com.team5.capstone.mju.apiserver.web.entity.History;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeRequestDto {
    private int userId;
    private int parkingLotId;
    private Float rating;
    private String comment;
    private LocalDateTime timestamp;
    public Grade ToEntity(){

        Grade grade = new Grade();
        grade.setUserId(userId);
        grade.setParkingLotId(parkingLotId);
        grade.setRating(rating);
        grade.setComment(comment);
        grade.setTimestamp(timestamp);
        return grade;
    }
}
