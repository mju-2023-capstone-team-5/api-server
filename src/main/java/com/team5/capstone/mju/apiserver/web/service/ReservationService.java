package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ReservationResponseDto;
import com.team5.capstone.mju.apiserver.web.dto.UserPointReceiptResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.entity.UserPayReceipt;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotPriceType;
import com.team5.capstone.mju.apiserver.web.enums.UserPayReceiptType;
import com.team5.capstone.mju.apiserver.web.exceptions.*;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import com.team5.capstone.mju.apiserver.web.repository.ReservationRepository;
import com.team5.capstone.mju.apiserver.web.repository.UserPayReceiptRepository;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class ReservationService {

    // Repository 객체
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final UserPayReceiptRepository userPayReceiptRepository;
    private final ParkingLotRepository parkingLotRepository;

    private final UserService userService;

    @Autowired // 생성자를 통한 의존성 주입
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, UserPayReceiptRepository userPayReceiptRepository, ParkingLotRepository parkingLotRepository, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.userPayReceiptRepository = userPayReceiptRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public ReservationResponseDto getReservationInfo(Long id) {
        Reservation found = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        return ReservationResponseDto.of(found);
    }

    // 예약생성
    @Transactional
    public ReservationResponseDto createReservation(ReservationRequestDto requestDto) {
        ParkingLot foundParkingLot = parkingLotRepository.findById(Long.valueOf(requestDto.getParkingLotId()))
                .orElseThrow(() -> new ParkingLotNotFoundException(requestDto.getParkingLotId()));
        User foundUser = userRepository.findById(Long.valueOf(requestDto.getUserId()))
                .orElseThrow(() -> new UserNotFoundException(requestDto.getUserId()));

        if (foundParkingLot.getRemainingSpace() == 0)
            throw new ParkingLotFullException(foundParkingLot.getParkingLotId());
        else foundParkingLot.useSpace();

        UserPointReceiptResponseDto usedDto = userService.usePoint(foundUser.getUserid(), Long.valueOf(requestDto.getPrice()));
        UserPointReceiptResponseDto earnedDto = userService.earnPointToOwner(Long.valueOf(foundParkingLot.getOwnerId()), Long.valueOf(requestDto.getPrice()));

        Reservation reservation = requestDto.toEntity();
        reservation.writeReceipts(usedDto, earnedDto);

        // Reservation 엔티티를 데이터베이스에 저장
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponseDto.of(savedReservation);
    }

    // 예약내역 업데이트
    @Transactional
    public ReservationResponseDto updateReservation(Long id, ReservationRequestDto requestDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        // 예약 상세 정보를 업데이트합니다.
        reservation.updateAllInfoSelf(requestDto);
        return ReservationResponseDto.of(reservation);
    }

    // 예약정보 삭제
    @Transactional
    public void cancelReservation(Long id) {
        Reservation found = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        String[] ids = found.getPayReceiptIds().split(",");
        Long userReceiptId = Long.valueOf(ids[0]);
        Long ownerReceiptId = Long.valueOf(ids[1]);

        UserPayReceipt userPaidReceipt = userPayReceiptRepository.findById(userReceiptId)
                .orElseThrow(() -> new PayReceiptNotFoundException("포인트"));
        UserPayReceipt ownerPaidReceipt = userPayReceiptRepository.findById(ownerReceiptId)
                .orElseThrow(() -> new PayReceiptNotFoundException("포인트"));

        // 사용자가 사용했던 포인트 복구
        userService.cancelUsePoint(Long.valueOf(userPaidReceipt.getUserId()), userPaidReceipt.getAmount());

        // 판매자가 얻은 포인트 보구
        userService.cancelEarnPoint(Long.valueOf(ownerPaidReceipt.getUserId()), ownerPaidReceipt.getAmount());

        // 주차장 남은 공간 복구
        ParkingLot foundParkingLot = parkingLotRepository.findById(Long.valueOf(found.getParkingLotId()))
                .get();
        foundParkingLot.returnSpace();

        reservationRepository.delete(found);
    }

    @Scheduled(cron = "* * * * * *")
    @Transactional
    public void changeStatusWhenReservationEnded() {
        List<Reservation> all = reservationRepository.findAll();
        all.forEach(reservation -> {
            LocalDateTime endTime = null;
            if (reservation.getDateType().equals(ParkingLotPriceType.HOUR.getType())) {
                endTime = reservation.getDate().plusHours(Arrays.stream(reservation.getDuration().split(",")).map(d -> Integer.parseInt(d)).mapToInt(d -> d).max().getAsInt());
            }
            else {
                endTime = reservation.getDate().plusMonths(Long.parseLong(reservation.getDuration()));
            }
            if (endTime.isBefore(LocalDateTime.now())) {
                ParkingLot foundParkingLot = parkingLotRepository.findById(Long.valueOf(reservation.getParkingLotId()))
                        .get();
                foundParkingLot.returnSpace();
                reservationRepository.delete(reservation);
            }
        });
    }
}
