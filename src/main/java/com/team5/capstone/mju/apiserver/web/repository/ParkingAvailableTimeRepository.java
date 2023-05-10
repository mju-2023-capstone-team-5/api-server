package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.ParkingAvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingAvailableTimeRepository extends JpaRepository<ParkingAvailableTime, Long> {
}