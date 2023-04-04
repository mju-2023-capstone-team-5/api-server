package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}