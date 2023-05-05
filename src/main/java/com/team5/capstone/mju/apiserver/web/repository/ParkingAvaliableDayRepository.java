package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.ParkingAvailableDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingAvaliableDayRepository extends JpaRepository<ParkingAvailableDay, Long> {
    Optional<ParkingAvailableDay> findByParkingLotId(Integer parkingLotId);
}