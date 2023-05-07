package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team5.capstone.mju.apiserver.web.entity.Qna;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaRequestDto {

    private int userId;
    private String title;
    private String content;

    @JsonIgnore
    private String answer;

    @JsonIgnore
    private LocalDateTime dateAsked;
    public Qna toEntity(){
        Qna qna = new Qna();
        qna.setUserId(userId);
        qna.setTitle(title);
        qna.setContent(content);
        qna.setAnswer(null);
        qna.setDateAsked(LocalDateTime.now());
        return qna;
    }
}