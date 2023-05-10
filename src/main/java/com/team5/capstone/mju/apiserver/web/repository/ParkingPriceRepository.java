package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.ParkingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingPriceRepository extends JpaRepository<ParkingPrice, Long> {
}