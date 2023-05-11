package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.ParkingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingPriceRepository extends JpaRepository<ParkingPrice, Long> {
    ParkingPrice findByParkingLotIdAndDateType(Integer parkingLotId, String dateType);
    List<ParkingPrice> findAllByParkingLotId(Integer parkingLotId);
}