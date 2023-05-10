package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.ParkingLotOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotOwnerRepository extends JpaRepository<ParkingLotOwner, Long> {
}