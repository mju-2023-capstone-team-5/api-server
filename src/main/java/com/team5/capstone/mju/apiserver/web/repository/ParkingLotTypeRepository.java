package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.ParkingLotType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotTypeRepository extends JpaRepository<ParkingLotType, Long> {
}