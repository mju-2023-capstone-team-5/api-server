package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.ParkingLotType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotTypeRepository extends JpaRepository<ParkingLotType, Long> {
    Optional<ParkingLotType> findByParkingLotId(Integer parkingLotId);
}