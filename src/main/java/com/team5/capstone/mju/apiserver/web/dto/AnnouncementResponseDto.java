package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.Announcement;
import com.team5.capstone.mju.apiserver.web.entity.Grade;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementResponseDto {
    private int announcementId;
    private String title;
    private String content;
    private LocalDateTime timestamp;

    public Announcement ToEntity(){

        Announcement announcement = new Announcement();
        announcement.setAnnouncementId((long) announcementId);
        announcement.setTitle(title);
        announcement.setContent(content);
        return announcement;
    }

    public static AnnouncementResponseDto of(Announcement announcement) {
        return AnnouncementResponseDto.builder()
                .announcementId(Math.toIntExact(announcement.getAnnouncementId()))
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .timestamp(announcement.getTimestamp())
                .build();
    }

}
