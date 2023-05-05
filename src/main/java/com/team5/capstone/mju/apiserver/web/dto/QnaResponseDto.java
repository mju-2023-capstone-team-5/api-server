package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.Qna;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaResponseDto {

    private  int qnaId;
    private int userId;
    private String title;
    private String content;
    private String answer;
    private LocalDateTime dateAsked;

    public Qna ToEntity(){

        Qna qna = new Qna();
        qna.setQnaId((long) qnaId);
        qna.setUserId(userId);
        qna.setTitle(title);
        qna.setContent(content);
        qna.setAnswer(answer);
        qna.setDateAsked(dateAsked);
        return qna;
    }
    public static QnaResponseDto of(Qna qna) {
        return QnaResponseDto.builder()
                .qnaId(Math.toIntExact(qna.getQnaId()))
                .userId(qna.getUserId())
                .title(qna.getTitle())
                .content(qna.getContent())
                .answer(qna.getAnswer())
                .dateAsked(qna.getDateAsked())
                .build();
    }

}
