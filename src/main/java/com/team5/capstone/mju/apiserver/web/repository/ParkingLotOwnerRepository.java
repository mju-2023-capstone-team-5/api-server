package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.ParkingLotOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotOwnerRepository extends JpaRepository<ParkingLotOwner, Long> {
    Optional<ParkingLotOwner> findByParkingLotId(Integer parkingLotId);
}