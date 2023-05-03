package com.team5.capstone.mju.apiserver.web.dto;

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
    private String answer;
    private LocalDateTime dateAsked;
    public Qna toEntity(){
        Qna qna = new Qna();
        qna.setUserId(userId);
        qna.setTitle(title);
        qna.setContent(content);
        qna.setAnswer(answer);
        qna.setDateAsked(dateAsked);
        return qna;
    }
}