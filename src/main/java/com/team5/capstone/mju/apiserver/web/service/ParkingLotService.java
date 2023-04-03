package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    @Autowired
    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    public ParkingLot getParkingLotInfo(Long id) {
        Optional<ParkingLot> result = parkingLotRepository.findById(id);
        return result.orElseThrow(() -> new IllegalStateException("잘못된 아이디"));
    }
}
