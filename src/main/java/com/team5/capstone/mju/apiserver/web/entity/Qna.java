package com.team5.capstone.mju.apiserver.web.entity;

import com.team5.capstone.mju.apiserver.web.dto.QnaRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "qna")
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id", nullable = false, columnDefinition = "int")
    private Long qnaId;

    @Column(name = "user_id", columnDefinition = "int")
    private Integer userId;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Lob
    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

    @Column(name = "date_asked")
    private LocalDateTime dateAsked;

    public void updateAllInfoSelf(QnaRequestDto requestDto) {
        this.setUserId(requestDto.getUserId());
        this.setTitle(requestDto.getTitle());
        this.setContent(requestDto.getContent());
        this.setAnswer(requestDto.getAnswer());
        this.setDateAsked(requestDto.getDateAsked());
    }
}