package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.History;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryResponseDto {
    private int historyId;
    private int userId;
    private int parkingLotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime dateUsed;

    public History ToEntity(){

        History history = new History();
        history.setHistoryId((long) historyId);
        history.setUserId(userId);
        history.setParkingLotId(parkingLotId);
        history.setStartTime(startTime);
        history.setEndTime(endTime);
//        history.setDateUsed(dateUsed);


        return history;
    }

}
