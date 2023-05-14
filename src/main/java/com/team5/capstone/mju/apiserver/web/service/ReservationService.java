package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ReservationResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import com.team5.capstone.mju.apiserver.web.repository.ReservationRepository;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class ReservationService {

    // Repository 객체
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ParkingLotRepository parkingLotRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, ParkingLotRepository parkingLotRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    @Transactional(readOnly = true)
    public ReservationResponseDto getReservationInfo(Long id) {
        Reservation found = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("예약내역을 찾을 수 없습니다."));

        return ReservationResponseDto.of(found);
    }

    // 예약생성
    @Transactional
    public ReservationResponseDto createReservation(ReservationRequestDto requestDto) {
        parkingLotRepository.findById(Long.valueOf(requestDto.getParkingLotId()))
                .orElseThrow(() -> new EntityNotFoundException("주차장이 존재하지 않습니다."));
        userRepository.findById(Long.valueOf(requestDto.getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다"));
        // Reservation 엔티티를 데이터베이스에 저장
        Reservation savedReservation = reservationRepository.save(requestDto.toEntity());
        return ReservationResponseDto.of(savedReservation);
    }

    // 예약내역 업데이트
    @Transactional
    public ReservationResponseDto updateReservation(Long id, ReservationRequestDto requestDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("예약내역을 찾을 수 없습니다."));

        // 예약 상세 정보를 업데이트합니다.
        reservation.updateAllInfoSelf(requestDto);
        return ReservationResponseDto.of(reservation);
    }

    // 예약정보 삭제
    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
