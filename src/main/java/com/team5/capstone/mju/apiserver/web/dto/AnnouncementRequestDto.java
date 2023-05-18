package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.Announcement;
import com.team5.capstone.mju.apiserver.web.entity.History;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementRequestDto {
    private String title;
    private String content;
    private LocalDateTime timestamp;
    public Announcement ToEntity(){

        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setContent(content);
        announcement.setTimestamp(timestamp);
        return announcement;
    }
}
