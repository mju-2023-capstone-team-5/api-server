package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ReservationResponseDto;
import com.team5.capstone.mju.apiserver.web.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1") // API 요청 URL의 앞에 오는 Prefix 설정
public class ParkingLotReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ParkingLotReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponseDto> getReservation(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reservationService.getReservationInfo(id));
    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto requestDto) {
        log.info(requestDto.toString());
        ReservationResponseDto responseDto = reservationService.createReservation(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponseDto> updateReservation(@PathVariable Long id, @RequestBody ReservationRequestDto requestDto) {
        log.info(requestDto.toString());
        ReservationResponseDto responseDto = reservationService.updateReservation(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}