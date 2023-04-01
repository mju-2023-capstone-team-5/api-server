package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id", nullable = false, columnDefinition = "int")
    private Long gradeId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "rating")
    private Float rating;

    @Lob
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

}