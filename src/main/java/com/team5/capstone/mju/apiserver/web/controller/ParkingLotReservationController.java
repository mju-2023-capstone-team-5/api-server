package com.team5.capstone.mju.apiserver.web.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ReservationResponseDto;
import com.team5.capstone.mju.apiserver.web.service.HistoryService;
import com.team5.capstone.mju.apiserver.web.service.NotificationService;
import com.team5.capstone.mju.apiserver.web.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1") // API 요청 URL의 앞에 오는 Prefix 설정
@Tag(name = "예약 Controller", description = "예약 관련 API 요청 Controller")
public class ParkingLotReservationController {
    private final ReservationService reservationService;
    private final NotificationService notificationService;
    private final HistoryService historyService;

    @Autowired
    public ParkingLotReservationController(ReservationService reservationService, NotificationService notificationService, HistoryService historyService) {
        this.reservationService = reservationService;
        this.notificationService = notificationService;
        this.historyService = historyService;
    }

    @Operation(summary = "예약 정보 반환 API", description = "예약 아이디를 받아 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예약 정보 조회에 성공"),
                    @ApiResponse(responseCode = "404", description = "예약 정보가 존재하지 않아 조회에 실패")
            }
    )
    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponseDto> getReservation(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reservationService.getReservationInfo(id));
    }

    @Operation(summary = "예약 정보 생성 API", description = "예약 정보를 받아 새로운 예약을 생성하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "새로운 예약 생성에 성공"),
                    @ApiResponse(responseCode = "404", description = "주차장이나 사용자 정보가 존재하지 않아 예약 생성에 실패")
            }
    )
    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto requestDto) throws FirebaseMessagingException {
        log.info(requestDto.toString());
        ReservationResponseDto responseDto = reservationService.createReservation(requestDto);
        historyService.createHistory(requestDto, Long.valueOf(responseDto.getReservationId()));
        notificationService.sendReservationSuccessPush(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "예약 정보 업데이트 API", description = "예약 정보를 받아 예약을 업데이트 하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예약 정보 업데이트에 성공"),
                    @ApiResponse(responseCode = "404", description = "예약 정보가 존재하지 않아 예약 조회에 실패")
            }
    )
    @PatchMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponseDto> updateReservation(@PathVariable Long id, @RequestBody ReservationRequestDto requestDto) {
        log.info(requestDto.toString());
        ReservationResponseDto responseDto = reservationService.updateReservation(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "예약 정보 삭제 API", description = "예약 아이디를 받아 예약을 삭제하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예약 삭제에 성공"),
                    @ApiResponse(responseCode = "404", description = "예약 정보가 존재하지 않아 삭제에 실패")
            }
    )
    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}