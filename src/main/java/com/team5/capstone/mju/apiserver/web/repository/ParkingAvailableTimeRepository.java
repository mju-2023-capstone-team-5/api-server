package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.ParkingAvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingAvailableTimeRepository extends JpaRepository<ParkingAvailableTime, Long> {
    List<ParkingAvailableTime> findAllByParkingLotId(Integer parkingLotId);
}