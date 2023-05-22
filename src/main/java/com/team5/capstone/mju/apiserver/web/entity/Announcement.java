package com.team5.capstone.mju.apiserver.web.entity;

import com.team5.capstone.mju.apiserver.web.dto.AnnouncementRequestDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id", nullable = false, columnDefinition = "int")
    private Long announcementId;

    @Lob
    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public void updateAllInfoSelf(AnnouncementRequestDto requestDto) {
        this.setTitle(requestDto.getTitle());
        this.setContent(requestDto.getContent());
        this.setTimestamp(requestDto.getTimestamp());
    }
}