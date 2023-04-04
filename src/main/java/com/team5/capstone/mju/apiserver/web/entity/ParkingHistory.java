package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "parking_history")
public class ParkingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", nullable = false, columnDefinition = "int")
    private Long historyId;

    @Column(name = "user_id", columnDefinition = "int")
    private Integer userId;

    @Column(name = "parking_lot_id", columnDefinition = "int")
    private Integer parkingLotId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "date_used")
    private LocalDateTime dateUsed;

}