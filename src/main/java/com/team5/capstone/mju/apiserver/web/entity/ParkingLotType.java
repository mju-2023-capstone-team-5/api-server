package com.team5.capstone.mju.apiserver.web.entity;

import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "parking_lot_type")
public class ParkingLotType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_lot_type_id", nullable = false, columnDefinition = "int")
    private Long parkingLotTypeId;

    @Column(name = "parking_lot_id")
    private Integer parkingLotId;

    @Lob
    @Column(name = "type", columnDefinition = "TEXT")
    private String type;

    public void updateType(ParkingLotRequestDto parkingLotRequestDto) {
        this.type = parkingLotRequestDto.getType();
    }

}