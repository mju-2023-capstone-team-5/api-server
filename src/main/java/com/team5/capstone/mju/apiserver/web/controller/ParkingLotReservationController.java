package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.service.ParkingLotReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingLotReservationController {
    private final ParkingLotReservationService reservationService;

    @Autowired
    public ParkingLotReservationController(ParkingLotReservationService reservationService) {
        this.reservationService = reservationService;
    }
}
