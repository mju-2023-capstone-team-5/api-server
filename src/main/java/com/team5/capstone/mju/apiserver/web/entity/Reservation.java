package com.team5.capstone.mju.apiserver.web.entity;

import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.UserPointReceiptResponseDto;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotPriceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false, columnDefinition = "int")
    private Long reservationId;

    @Column(name = "user_id", columnDefinition = "int")
    private Integer userId;

    @Column(name = "parking_lot_id")
    private Integer parkingLotId;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "price")
    private Integer price;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "date_type")
    private String dateType;

    @Lob
    @Column(name = "pay_receipt_ids", columnDefinition = "TEXT")
    private String payReceiptIds;


    public void updateAllInfoSelf(ReservationRequestDto requestDto) throws EntityNotFoundException {
        this.setUserId(requestDto.getUserId());
        this.setParkingLotId(requestDto.getParkingLotId());
        if (requestDto.getHourly() == null && requestDto.getMonthly() == null) throw new EntityNotFoundException("예약 관련 시간 정보가 존재하지 않습니다");
        else if (requestDto.getMonthly() != null) {
            this.setDateType(ParkingLotPriceType.MONTH.getType());
            this.setDate(requestDto.getMonthly().getDate());
            this.setDuration(requestDto.getMonthly().getDuration());
        }
        else if (requestDto.getHourly() != null) {
            this.setDateType(ParkingLotPriceType.HOUR.getType());
            this.setDate(requestDto.getHourly().getDate());
            this.setDuration(requestDto.getHourly().getDuration());
        }
        this.setPrice(requestDto.getPrice());
    }

    public void writeReceipts(UserPointReceiptResponseDto userReceipt, UserPointReceiptResponseDto ownerReceipt) {
        this.payReceiptIds = userReceipt.getReceiptId() + "," + ownerReceipt.getReceiptId();
    }
}