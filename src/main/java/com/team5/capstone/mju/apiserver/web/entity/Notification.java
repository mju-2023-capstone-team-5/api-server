package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false, columnDefinition = "int")
    private Long notificationId;

    @Column(name = "user_id")
    private Integer userId;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "date_sent")
    private LocalDateTime dateSent;

}